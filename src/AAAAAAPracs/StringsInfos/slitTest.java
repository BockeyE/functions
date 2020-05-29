package AAAAAAPracs.StringsInfos;

/**
 * @author bockey
 */
public class slitTest {
    public static void main(String[] args) {

        /**
         * java.lang.string.split
         * split 方法
         * 将一个字符串分割为子字符串，然后将结果作为字符串数组返回。
         * stringObj.split([separator，[limit]])
         * 参数
         * stringObj
         * 必选项。要被分解的 String 对象或文字。该对象不会被 split 方法修改。
         * separator
         * 可选项。字符串或正则表达式对象，它标识了分隔字符串时使用的是一个还是多个字符。如果忽略该选项，返回包含整个字符串的单一元素数组。
         * limit
         * 可选项。该值用来限制返回数组中的元素个数。
         *
         * 说明
         * split 方法的结果是一个字符串数组，在 stingObj 中每个出现 separator 的位置都要进行分解
         */
        String[] split = "attachHash".split("\\|");
    }
}
