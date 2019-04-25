package UTILLS.Tools.expr;

/**
 * 表达式函数参数异常
 */
public class FuncException extends ExprException {

    private static final long serialVersionUID = 2374532080463337841L;

    /** 函数名称 */
    public String funcName;

    public FuncException(String errorCode) {
        super(errorCode);
    }

    public FuncException(String errorCode, String funcName, String positon) {
        super(errorCode, positon);
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        return "error:" + errorCode + ",function:" + funcName + ", position:" + position;
    }
}
