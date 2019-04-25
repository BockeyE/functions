package UTILLS.Tools.expr;

/**
 * ExprException表达式异常
 */
public class ExprException extends RuntimeException {

    private static final long serialVersionUID = -1777165206728204494L;

    /** 错误代号 */
    public final String errorCode;
    /** 位置信息 */
    public final String position;

    @Override
    public String toString() {
        return "error:" + errorCode + ", position:" + position;
    }

    public ExprException(String errorCode, String positon) {
        this.errorCode = errorCode;
        this.position = positon;
    }

    public ExprException(String errorCode, int lo, int hi) {
        this.errorCode = errorCode;
        this.position = "(" + lo + "," + hi + ")";
    }

    public ExprException(String errorCode) {
        this.errorCode = errorCode;
        this.position = "";
    }

}
