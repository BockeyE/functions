package UTILLS.Tools.expr;

import java.math.BigDecimal;
import java.util.Map;

import UTILLS.Tools.expr.func.IFunction;
import UTILLS.Tools.expr.parse.Parser;
import UTILLS.Tools.expr.struct.Context;
import UTILLS.Tools.expr.struct.OperNode;

/**
 * 表达式引擎
 */
public class ExprEngine {

    private static int maxDecimal = 16;

    // 表达式字符串
    private String expr = "";

    private final Context ctx;

    // 根节点
    private OperNode root;

    public ExprEngine(String expr) throws ExprException {
        this.expr = expr;
        ctx = new Context(expr);
        this.root = new Parser(expr).root;
    }

    public ExprEngine addFunction(IFunction func) {
        ctx.funcs.put(func.getName().toUpperCase(), func);
        return this;
    }

    public ExprEngine setParam(String key, Object val) {
        ctx.params.put(key.toUpperCase(), val);
        return this;
    }

    public ExprEngine setParam(Map<String, Object> paramMap) {
        if (paramMap != null) {
            for (String k : paramMap.keySet()) {
                setParam(k, paramMap.get(k));
            }
        }
        return this;
    }

    public IParamParser getParser() {
        return ctx.parser;
    }

    public void setParser(IParamParser parser) {
        ctx.parser = parser;
    }

    /**
     * 计算字符表达式
     * 
     * @return String
     * @throws ExprException
     */
    public String asString() throws ExprException {
        root.calculate(ctx);
        if (root.getValue() == null) {
            return null;
        } else {
            return root.getValue().toString();
        }
    }

    /**
     * 计算布尔表达式
     * 
     * @return boolean
     * @throws ExprException
     */
    public boolean asBoolean() throws ExprException {
        root.calculate(ctx);
        if (root.getValue() instanceof Boolean) {
            return (Boolean) root.getValue();
        } else {
            return false;
        }
    }

    /**
     * 计算数值结果
     * 
     * 
     * @return
     * @throws ExprException
     */
    public BigDecimal asNumber() throws ExprException {
        root.calculate(ctx);
        // if type is number,return value
        Double x = ExprUtil.toNum(root.getValue());
        if (x != null) {
            return doubleToBigDecimal(x);
        } else {
            return null;
        }
    }

    private static BigDecimal doubleToBigDecimal(double val) {
        BigDecimal bdFloat = BigDecimal.valueOf(val);

        bdFloat = bdFloat.setScale(maxDecimal, BigDecimal.ROUND_HALF_UP);
        return BigDecimal.valueOf(bdFloat.doubleValue());
    }

    public String getExpr() {
        return expr;
    }

}
