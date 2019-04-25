package UTILLS.Tools;

/**
 * 可抛出异常的函数框架
 * 
 * @author PanJun
 *
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface FunctionX<T, R> {

    R apply(T t) throws Exception;

}
