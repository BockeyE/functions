package protem;

/**
 * @author bockey
 */
public class Constants {
    public static assetAttach AttachType = new assetAttach();
    public static assetStatus AssetStatus = new assetStatus();
    public static ZDWstatus ZDWstatus = new ZDWstatus();

    static class assetAttach {
        public static final String BASICCONTRACT = "basicContract";
        public static final String BASICINVOICE = "basicInvoice";
        public static final String CONTRACTFILE = "contractFile";
        public static final String OTHERFILE = "otherFile";
    }

    static class assetStatus {
        //        资产状态，
        public static final int INITSUBMIT = 1;
        public static final int FILTERPASS = 2;
        public static final int FILTERFAIL = -2;
        public static final int CONFIRMPASS = 3;
        public static final int CONFIRMFAIL = -3;
        public static final int HASAPPLIED = 4;
        public static final int APPROVAL = 5;
        public static final int DISAPPROVAL = -5;
        public static final int PUBLISHED = 6;
    }

    static class ZDWstatus {
        public static final int NONEED = 0;
        public static final int NEEDNOTDOWN = -1;
        public static final int COMPLETED = 1;

    }
}
