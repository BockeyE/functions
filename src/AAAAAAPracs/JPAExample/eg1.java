package AAAAAAPracs.JPAExample;

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
}
