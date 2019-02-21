package protem;

/**
 * @author bockey
 */
public class Project {
    String projId;
    String projNumber;
    String projName;
    String coreID;
    String coreName;
    String prodName;
    String finPlanName;//融资计划名称
    String releaseDate;
    String dueDate;
    String scale;
    int projStatus;//PENDING_RELEASE:待发行  FINISH_RELEASE:已发行
    int ZDWsearchStatus;//不需要 0; 需要 未上传 -1; 需要已上传 1;
    int ZDWregistStatus;//不需要 0; 需要 未上传 -1; 需要已上传 1;
    int invoiceStatus;//不需要 0; 需要 未上传 -1; 需要已上传 1;
    int contractStatus;//不需要 0; 需要 未上传 -1; 需要已上传 1;
    String projectFields;
    int projAssetValidRequire;
}
