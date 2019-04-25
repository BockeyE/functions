package UTILLS.Tools.expr.func;

import java.util.List;

import UTILLS.Tools.expr.FuncException;

/**
 * 函数计算接口
 * 
 * @author PanJun
 * 
 */
public interface IFunction {

    /**
     * 函数名称
     * 
     * @return
     */
    public String getName();

    /**
     * 计算函数结果
     * 
     * @param args
     * @return
     */
    public Object invoke(List<Object> args) throws FuncException;

}
