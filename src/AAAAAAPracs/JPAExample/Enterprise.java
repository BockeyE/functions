//package AAAAAAPracs.JPAExample;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Index;
//import javax.persistence.UniqueConstraint;
//
//@Data
//@javax.persistence.Table(indexes = {@Index(columnList = "enterpriseName")}
//        , uniqueConstraints = {@UniqueConstraint(columnNames = "enterpriseName")})
//@javax.persistence.Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Enterprise {
//    @Id
//    @GeneratedValue
//    private Long id;
//    private String enterpriseName;
//    private String code;
//    private Integer type = 0;
//
//    public Enterprise(String enterpriseName, String code, Integer type) {
//        this.enterpriseName = enterpriseName;
//        this.code = code;
//        this.type = type;
//    }
//
//    public static final Integer OwnerProjDepartment = 0;//0,业主项目部
//    public static final Integer ConstructManageDepartment = 1;//1,建设管理单位
//    public static final Integer SupervisorDepartment = 2;//2,监理单位
//    public static final Integer DesignDepartment = 3;//3,设计单位
//    public static final Integer ConstructBuildDepartment = 4;//,施工单位
//}
