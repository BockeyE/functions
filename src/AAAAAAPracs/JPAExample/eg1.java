package AAAAAAPracs.JPAExample;

import java.util.Date;
import java.util.List;

/**
 * @author bockey
 */
public class eg1 {
//    private List<OnSiteSign> searchSignPool(String key, String projectId) {
//        return onSiteSignDao.findAll((Specification<OnSiteSign>) (root, criteriaQuery, cb) -> {
//            Predicate p2 = cb.like(root.get("buildDepartmentName").as(String.class), "%" + key + "%");
//            Predicate p4 = cb.like(root.get("projectName").as(String.class), "%" + key + "%");
//            Predicate p5 = cb.like(root.get("signCause").as(String.class), "%" + key + "%");
//            Predicate p1 = cb.like(root.get("signCost").as(String.class), "%" + key + "%");
//            Predicate p6 = cb.like(root.get("archivedId").as(String.class), "%" + key + "%");
//            Predicate p3 = cb.equal(root.get("projectId").as(Long.class), Long.parseLong(projectId));
//            criteriaQuery.where(cb.and(p3, cb.or(p2, p1, p4, p5, p6)));
//            criteriaQuery.orderBy(cb.desc(root.get("id").as(Long.class)));
//            return criteriaQuery.getRestriction();
//        });
//    }


//    Page<SpvProject> page = projectDao.findAll(new Specification<SpvProject>() {
//        @Override
//        public Predicate toPredicate(Root<SpvProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
//            Predicate p2 = cb.like(root.get("ownerProjDepartment").as(String.class), "%" + key + "%");
//            Predicate p4 = cb.like(root.get("designDepartment").as(String.class), "%" + key + "%");
//            Predicate p5 = cb.like(root.get("constructBuildDepartment").as(String.class), "%" + key + "%");
//            Predicate p1 = cb.like(root.get("supervisorDepartment").as(String.class), "%" + key + "%");
//            Predicate p6 = cb.like(root.get("constructManageDepartment").as(String.class), "%" + key + "%");
//            Predicate p3 = cb.like(root.get("spvProjectName").as(String.class), "%" + key + "%");
//            Predicate p7 = cb.like(root.get("spvProjectNumber").as(String.class), "%" + key + "%");
//            CriteriaBuilder.In<Long> in = cb.in(root.get("id"));
//            for (Long id : ids) {
//                in.value(id);
//            }
//            criteriaQuery.where(cb.and(in,cb.or(p2, p1, p4, p5, p6, p3, p7)));
//            criteriaQuery.orderBy(cb.desc(root.get("id").as(Long.class)));
//            return criteriaQuery.getRestriction();
//        }
//    }, pageable);

//    public List<OnSiteSign> querySignsForArchive(String projName, String startDate, String endDate, List<String> procesIds) {
//        该方法，意在查询出所有时间区间内的，状态是完成的，属于本项目，且processid不在已归档的list中
//        return signDao.findAll((Specification<OnSiteSign>) (root, criteriaQuery, cb) -> {
//            Predicate p3 = cb.equal(root.get("projectName").as(String.class), projName);
//            Predicate p1 = cb.greaterThanOrEqualTo(root.get("dateStr").as(String.class), startDate);
//            CriteriaBuilder.In<String> in = cb.in(root.get("processId"));
//            for (String id : procesIds) {
//                in.value(id);
//            }
//            Predicate p2 = cb.lessThanOrEqualTo(root.get("dateStr").as(String.class), endDate);
//            criteriaQuery.where(cb.and(cb.not(in), p3, p1, p2));
//            criteriaQuery.orderBy(cb.desc(root.get("id").as(Long.class)));
//            return criteriaQuery.getRestriction();
//        });
//    }

//    public List<OnSiteSign> querySignsForArchive(String projName, Date startDate, Date endDate, List<String> procesIds) {
//
//        System.out.println("cb test");
//        该方法，意在查询出所有时间区间内的，状态是完成的，属于本项目，且processid不在已归档的list中
//        return signDao.findAll((Specification<OnSiteSign>) (root, criteriaQuery, cb) -> {
//            Predicate p2 = cb.lessThanOrEqualTo(root.get("addDate").as(Date.class), endDate);
//            Predicate p3 = cb.equal(root.get("projectName").as(String.class), projName);
//            Predicate p2 = cb.greaterThanOrEqualTo(root.get("dateStr").as(String.class), startDate.toString());
//            Predicate p1 = root.get("processId").in(procesIds);
//            if (procesIds.size() == 0) {
//                criteriaQuery.where(cb.and(p3, p2));
//            } else {
//                criteriaQuery.where(cb.and(p3, p2, p1));
//            }
//            criteriaQuery.orderBy(cb.asc(root.get("id").as(Long.class)));
//            return criteriaQuery.getRestriction();
//        });
//    }


}
