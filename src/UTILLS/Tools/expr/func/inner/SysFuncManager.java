package UTILLS.Tools.expr.func.inner;

import java.util.HashMap;
import java.util.Map;

import UTILLS.Tools.expr.func.IFunction;

/**
 * 系统内置函数管理器
 * 
 * @author Administrator
 * 
 */
final public class SysFuncManager {

    // 内置的函数
    private static Map<String, IFunction> funcMap = new HashMap<String, IFunction>();

    static {
        addFunction(FuncAbs.instance);
        addFunction(FuncAvg.instance);
        addFunction(FuncCeil.instance);
        addFunction(FuncCos.instance);
        addFunction(FuncFloor.instance);
        addFunction(FuncMax.instance);
        addFunction(FuncMin.instance);
        addFunction(FuncRound.instance);
        addFunction(FuncSin.instance);
        addFunction(FuncSqrt.instance);
        addFunction(FuncSum.instance);
        addFunction(FuncTan.instance);
        addFunction(FuncIif.instance);
        addFunction(FuncContainStr.instance);
    }

    private static void addFunction(IFunction func) {
        funcMap.put(func.getName().toUpperCase(), func);
    }

    public static IFunction getFunction(String funcUpperName) {
        return funcMap.get(funcUpperName);
    }
}
