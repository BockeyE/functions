package UTILLS.Tools.expr.struct;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprException;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.struct.Linker.Entry;

/**
 * 操作符节点，对调用客户端而言为永久类
 * 
 * @author PanJun
 * 
 */
abstract public class OperNode {

    // 原始字符值||函数名,默认""
    protected String text = "";

    // 转换成大写的原始字符
    protected String utext = "";

    // 数值
    protected Object value;

    // 操作符
    protected Operator operator;

    // 标识当前节点是否为参数类型
    protected boolean isParam = false;

    // 子节点
    protected final Linker<OperNode> children = new Linker<OperNode>();

    @Override
    public String toString() {
        if (operator == null) {
            return text;
        } else {
            return operator + "(" + text + ")";
        }
    }

    private Object parseParamValue(Context ctx) {
        Object ret = ctx.params.get(utext);
        int pos = utext.lastIndexOf(".");
        if (ret != null || pos == -1) {
            return ret;
        }

        // 先找父对象
        String prefix = utext;
        Object parent = null;
        while (pos > -1) {
            prefix = prefix.substring(0, pos);
            parent = ctx.params.get(prefix);
            if (parent != null) {
                break;
            }
            pos = prefix.lastIndexOf('.');
        }
        if (parent == null) {
            return null;
        }

        ret = parent;
        String suffix = utext.substring(prefix.length() + 1);
        while (suffix.length() > 0) {
            int i = suffix.indexOf('.');
            String prop = null;
            if (i > -1) {
                prop = suffix.substring(0, i);
                suffix = suffix.substring(i + 1);
            } else {
                prop = suffix;
                suffix = "";
            }

            ret = ExprUtil.getBeanPropValue(ret, prop);
            if (ret == null) {
                break;
            }
        }
        return ret;
    }

    /**
     * 执行计算
     * 
     * @param node
     * @throws ExprException
     */
    public void calculate(Context ctx) throws ExprException {
        for (Entry<OperNode> e = children.getHead(); e != null; e = e.getNext()) {
            e.elem.calculate(ctx);
        }
        if (isParam) {
            Object prmVal = parseParamValue(ctx);
            value = null;
            if (prmVal != null) {
                value = prmVal;
            } else if (ctx.parser != null) {
                value = ctx.parser.parseValue(text);
            }

            if (value == null) {
                throw new ExprException(Consts.PARAM_ERR, " \"" + text + "\" " + getPosition());
            }
        } else if (operator == Operator.Fun || children.getHead() != null) {
            try {
                operator.calc(ctx, this);
            } catch (FuncException e) {
                throw new FuncException(e.errorCode, e.funcName, getPosition());
            } catch (ExprException e) {
                throw new ExprException(e.errorCode, getPosition());
            }
        }
    }

    public String getText() {
        return text;
    }

    public String getUtext() {
        return utext;
    }

    public Operator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    /** 取得位置描述 */
    public abstract String getPosition();

}
