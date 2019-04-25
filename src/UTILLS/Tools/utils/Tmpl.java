package UTILLS.Tools.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的模版引擎<br>
 * ${bill.submitName}于${time:submitTime}提交的单据${bill.billNum},待你审批
 * 
 * @author PanJun
 * 
 */
public class Tmpl {

    private String template;

    private Map<String, Object> variables = new HashMap<String, Object>();

    private String timeFormat = "yyyy-MM-dd HH:mm:ss";

    private String dateFormat = "yyyy-MM-dd";

    public Tmpl() {
    }

    public Tmpl(String template) {
        this.template = template;
    }

    /**
     * 添加变量
     * 
     * @param name
     * @param value
     */
    public Tmpl addVariable(String name, Object value) {
        name = name == null ? "" : name.trim().toLowerCase();
        variables.put(name, value);
        return this;
    }

    /**
     * 设置时间格式
     * 
     * @param timeFormat
     * @return
     */
    public Tmpl setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        return this;
    }

    /**
     * 设置日期格式
     * 
     * @param dateFormat
     * @return
     */
    public Tmpl setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public Tmpl setTemplate(String raw) {
        this.template = raw;
        return this;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 执行，返回结果
     * 
     * @return
     */
    public String execute() {
        if (STR.isBlank(template)) {
            return "";
        }

        template = handleLoop(template);

        StringBuilder sbRet = new StringBuilder();
        while (template.length() > 0) {
            int i = template.indexOf("${");
            int j = i > -1 ? template.indexOf("}") : -1;
            if (j < 0) {
                sbRet.append(template);
                template = "";
                break;
            }

            sbRet.append(template.substring(0, i));
            Object value = parse(template.substring(i + 2, j));
            sbRet.append(value);
            template = template.substring(j + 1);
        }
        return sbRet.toString();
    }

    private String handleLoop(String tmpl) {
        Map<String, Object> savedVar = new HashMap<String, Object>(variables);
        StringBuilder sbRet = new StringBuilder();
        StringBuilder sbTmp = new StringBuilder();
        while (tmpl.length() > 0) {
            int i = tmpl.indexOf("${[[");
            int j = i > -1 ? tmpl.indexOf("]]", i + 1) : -1;
            if (j < 0) {
                sbRet.append(tmpl);
                tmpl = "";
                break;
            } else {
                String tag = tmpl.substring(i + 4, j);
                int r = tmpl.indexOf("}", j + 1);
                String var = r < 0 ? "" : tmpl.substring(j + 2, r);
                if (STR.isBlank(tag) || STR.isBlank(var)) {
                    sbRet.append("${").append(tmpl.substring(0, j + 2));
                    tmpl = tmpl.substring(j + 2);
                    continue;
                }
                int lo = tmpl.lastIndexOf("<" + tag, i);
                int hi = lo > -1 ? tmpl.indexOf("</" + tag + ">", i) : -1;
                if (hi > 0) {
                    String loopSeg = tmpl.substring(lo, hi + ("</" + tag + ">").length());

                    sbRet.append(tmpl.substring(0, lo));
                    tmpl = tmpl.substring(hi + ("</" + tag + ">").length());

                    sbTmp.setLength(0);
                    while (loopSeg.length() > 0) {
                        int x = loopSeg.indexOf("[[");
                        if (x < 0) {
                            sbTmp.append(loopSeg);
                            loopSeg = "";
                        } else {
                            sbTmp.append(loopSeg.substring(0, x));
                            x = loopSeg.indexOf("]]");
                            if (x < 0) {
                                sbTmp.append(loopSeg);
                                loopSeg = "";
                            } else {
                                loopSeg = loopSeg.substring(x + 2);
                            }
                        }
                    }

                    r = var.indexOf(".");
                    if (r > 0) {
                        var = var.substring(0, r);
                    }
                    var = var.toLowerCase();
                    Object value = savedVar.get(var);
                    if (value instanceof List) {
                        @SuppressWarnings("rawtypes")
                        List list = (List) value;
                        int index = 1;
                        for (Object o : list) {
                            variables.put(var, o);
                            variables.put("#index#", index);
                            template = sbTmp.toString();
                            sbRet.append(execute());
                            index++;
                        }
                    }
                } else {
                    sbRet.append(tmpl.substring(0, j + 2));
                    tmpl = tmpl.substring(j + 2);
                }
            }
        }

        variables.clear();
        variables.putAll(savedVar);
        return sbRet.toString();
    }

    private Object parse(String var) {
        var = var.toLowerCase();
        boolean isTime = var.startsWith("time:");
        boolean isDate = var.startsWith("date:");
        if (isTime || isDate) {
            var = var.substring(5);
        }

        List<String> varList = STR.split(var, ".");
        if (varList.isEmpty()) {
            return "";
        }

        Object o = variables.get(varList.get(0));
        if (o == null) {
            return "";
        }

        Object ret = o;
        for (int i = 1; i < varList.size(); i++) {
            String attr = varList.get(i);
            ret = parseAttribute(ret, attr);
            if (ret == null) {
                return "";
            }
        }

        if (isTime) {
            if (ret instanceof Date) {
                ret = new SimpleDateFormat(timeFormat).format((Date) ret);
            } else {
                throw new IllegalArgumentException(var + ":" + ret.getClass().getName() + "不是日期类型");
            }
        }

        if (isDate && ret instanceof Date) {
            if (ret instanceof Date) {
                ret = new SimpleDateFormat(dateFormat).format((Date) ret);
            } else {
                throw new IllegalArgumentException(ret.getClass().getName() + "不是日期类型");
            }
        }
        return ret;
    }

    @SuppressWarnings("all")
    private Object parseAttribute(Object o, String attr) {
        if (o instanceof Map) {
            Map map = (Map) o;
            for (Object k : map.keySet()) {
                if (attr.equalsIgnoreCase(k + "")) {
                    return map.get(k);
                }
            }
            throw new IllegalArgumentException("Map中不包含属性：" + attr);
        }

        attr = "get" + attr;
        Method getter = null;
        for (Method m : o.getClass().getMethods()) {
            if (m.getName().equalsIgnoreCase(attr) && m.getParameterTypes().length == 0) {
                getter = m;
                break;
            }
        }

        if (getter == null) {
            throw new IllegalArgumentException(o.getClass().getName() + "不包含属性：" + attr);
        }

        try {
            getter.setAccessible(true);
            return getter.invoke(o);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 测试单据对象
     */
    static class Bill {

        String billNum = "B0001";

        String submitName = "张三";

        public String getSubmitName() {
            return submitName;
        }

        public void setSubmitName(String submitName) {
            this.submitName = submitName;
        }

        public String getBillNum() {
            return billNum;
        }

        public void setBillNum(String billNum) {
            this.billNum = billNum;
        }
    }

    public static void main(String[] args) throws Exception {
        String cont = "888888\n<tr>\n<td>${[[tr]]bill.billNum}</td><td>${[[tr]]bill.submitName}</td>\n000000";
        List<Bill> vars = new ArrayList<>();
        vars.add(new Bill());
        vars.add(new Bill());
        vars.add(new Bill());
        vars.add(new Bill());
        long b = System.currentTimeMillis();

        String s = "";
        for (int i = 0; i < 1000; i++) {
            Tmpl et = new Tmpl(cont);
            et.addVariable("bill", vars);
            s = et.execute();
        }
        System.out.println((System.currentTimeMillis() - b) + "ms\n" + s);
    }

}
