package UTILLS.Tools.expr.struct;

import java.util.HashMap;
import java.util.Map;

import UTILLS.Tools.expr.IParamParser;
import UTILLS.Tools.expr.func.IFunction;

/**
 * 表达式计算上下文,记录支持的函数和参数
 * 
 * @author PanJun
 * 
 */
public class Context {

    // 支持的函数
    public final Map<String, IFunction> funcs = new HashMap<String, IFunction>();

    // 键值对参数
    public final Map<String, Object> params = new HashMap<String, Object>();

    public IParamParser parser;

    // 表达式
    public final String expr;

    public Context(String expr) {
        this.expr = expr;
    }

}
