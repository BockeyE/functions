package AAAAAAPracs.activitisRep;

/**
 * @author bockey
 */
public class rejected {
    /**
     * 驳回到上个流程
     * @return
     */


    public String reject(String processId, String taskId, String rejectStr, UserVo userVo){
        String type = getUserType(userVo);
        DesignForm designForm = designFormDao.findDesignFormByProcessId(processId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if (null == processInstance) {
            throw new RuntimeException("该流程已完成!无法回退");
        }
        String firstCheckKey = getFirstCheckKey(processId);
        Task currentTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        if("err".equals(firstCheckKey)){
            return "err";
        }
        if(firstCheckKey.equals(currentTask.getTaskDefinitionKey())){
            Map map = new HashMap();
            map.put("processId",processId);
            map.put("check",2);
            map.put("opinion",rejectStr);
            try {
                String ret =  completeShejiTask(map,userVo);
                if ("ok".equals(ret) || "over".equals(ret)){
                    return "ok";
                }
                else if("noPermission".equals(ret)){
                    return ("没有权限");
                }
                else {
                    return ("审核失败");
                }
            }
            catch (Exception e){
                return "exception";
            }
        }
        if(currentTask.getTaskDefinitionKey().contains("supervisor")){
            if(taskId == null || "".equals(taskId)){
                return "taskId为空";
            }
        }
        else {
            List<UserTask> userTaskList = getDesignUserTaskList();
            String previousKey = "";
            for (int i=0; i<userTaskList.size(); i++){
                UserTask userTask = userTaskList.get(i);
                if(currentTask.getTaskDefinitionKey().equals(userTask.getId())){
                    previousKey = userTaskList.get(i-1).getId();
                }
            }
            if("ownerManager".equals(currentTask.getTaskDefinitionKey())){
                previousKey = "constructionCost";
            }
            System.out.println("previousKey = "+previousKey);
            List<HistoricTaskInstance> historyTasks = historyService.createHistoricTaskInstanceQuery().
                    processInstanceId(processId).taskDefinitionKey(previousKey).
                    orderByTaskCreateTime().desc().list();
            if(historyTasks.size() > 0){
                taskId = historyTasks.get(0).getId();
            }
            else {
                return "err";
            }
        }
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (null == task) {
            throw new RuntimeException("无效任务ID[ " + taskId + " ]");
        }
        Task cutask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        // 获取流程定义对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        Process process = bpmnModel.getProcesses().get(0);

        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();

        FlowNode sourceNode = (FlowNode) process.getFlowElement(task.getTaskDefinitionKey());
        taskList.forEach(obj -> {
            FlowNode currentNode = (FlowNode) process.getFlowElement(obj.getTaskDefinitionKey());
            // 获取原本流程连线
            List<SequenceFlow> outComingSequenceFlows = currentNode.getOutgoingFlows();

            // 配置反转流程连线
            SequenceFlow sequenceFlow = new SequenceFlow();
            sequenceFlow.setTargetFlowElement(sourceNode);
            sequenceFlow.setSourceFlowElement(currentNode);
            sequenceFlow.setId("callback-flow");

            List<SequenceFlow> newOutComingSequenceFlows = new ArrayList<>();
            newOutComingSequenceFlows.add(sequenceFlow);
            currentNode.setOutgoingFlows(newOutComingSequenceFlows);

            // 配置任务审批人
            Map<String, Object> variables = new HashMap<>(1);
            //variables.put(WorkFlowConfiguration.DEFAULT_USER_TASK_ASSIGNEE, UserEntityService.getCurrentUserEntity().getUserId());
            variables.put("assignee", userVo.getPhone());
            // 完成任务
            taskService.setAssignee(obj.getId(), userVo.getPhone());
            taskService.complete(obj.getId(), variables);
            // 复原流程
            currentNode.setOutgoingFlows(outComingSequenceFlows);
        });

        //MsgToTaskId msgToTaskId = new MsgToTaskId();
        MsgToTaskId msgToTaskId = msgToTaskIdDao.findMsgToTaskIdByTaskId(cutask.getId());
        if (msgToTaskId == null) {
            msgToTaskId = new MsgToTaskId();
        }
        msgToTaskId.setTaskId(cutask.getId());
        msgToTaskId.setPhone(userVo.getPhone());
        msgToTaskId.setMsg(rejectStr);
        msgToTaskId.setUserName(userVo.getUsername());
        msgToTaskId.setRealName(userVo.getRealName());
        msgToTaskId.setTaskName(cutask.getName());
        msgToTaskId.setCheckMsg("no");
        msgToTaskId.setChainHash(fakeChainService.initNewOne().getChainHash());
        msgToTaskIdDao.save(msgToTaskId);

        processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        //System.out.println("processInstance "+(processInstance!=null));
        if(processInstance != null){
            Task taskNext = taskService.createTaskQuery().processInstanceId(processId).singleResult();
            String taskId2 = taskNext.getId();
            //System.out.println("task2"+taskId2);
            String nextAssignee = getAssignee(taskNext.getTaskDefinitionKey(),designForm.getProjectId());
            taskService.setAssignee(taskId2,getAssignee(taskNext.getTaskDefinitionKey(),designForm.getProjectId()));
            sendWs(Long.parseLong(designForm.getProjectId()),nextAssignee,designForm);
        }

        //发送ws
        //sendMessg();
        // 更新流程状态
        //updateAuditStatus(processInstance.getProcessInstanceId());
        return "ok";
    }

}
