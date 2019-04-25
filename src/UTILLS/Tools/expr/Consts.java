package UTILLS.Tools.expr;

/**
 * 【函数表达式解析】异常编码
 */
public class Consts {

    /** 表达式不能为空 */
    public static final String BLANK_ERR = "wfr.expr.blank_err";
    /** 表达式在位置：解析出错 */
    public static final String PARSE_ERR = "wfr.expr.parse_err";
    /** 加法运算只能对数字和字符串有效 */
    public static final String ADD_ERR = "wfr.expr.add_err";
    /** 减法运算只能对数字有效 */
    public static final String SUB_ERR = "wfr.expr.sub_err";
    /** 除法运算只能对数字有效 */
    public static final String DIV_ERR = "wfr.expr.div_err";
    /** 除零错误 */
    public static final String DIV_0_ERR = "wfr.expr.div_0_err";
    /** 求模运算只能对数字有效 */
    public static final String MOD_ERR = "wfr.expr.mod_err";
    /** 乘方运算只能对数字有效 */
    public static final String MUL_ERR = "wfr.expr.mul_err";
    /** 乘法运算只能对数字有效 */
    public static final String PWR_ERR = "wfr.expr.pwr_err";
    /** 负号运算只能对数字有效 */
    public static final String REV_ERR = "wfr.expr.rev_err";
    /** 正号运算只能对数字有效 */
    public static final String PLUS_ERR = "wfr.expr.plus_err";
    /*** 非运算只能对是否(布尔)有效 */
    public static final String NOT_ERR = "wfr.expr.not_err";
    /** 比较运算(">",">=","<=","=","==","!=","<>")要求数据类型一致 */
    public static final String CMP_ERR = "wfr.expr.cmp_err";
    /** 逻辑运算(||,&&)只能对布尔类型有效! */
    public static final String LOGIC_ERR = "wfr.expr.logic_err";
    /** 参数'%s'无法识别 */
    public static final String PARAM_ERR = "wfr.expr.param_err";
    /** 括号不匹配! */
    public static final String BRACKET_ERR = "wfr.expr.bracket_err";

    /** 无法识别的函数 */
    public static final String FN_UNKOWN = "wfr.expr.fn.unkown";
    /** 函数至少需要一个参数 */
    public static final String FN_ARG_NO_ERR = "wfr.expr.fn.arg_no_err";
    /** 函数参数类型必须为数值、字符串、日期 ，并且所有参数类型要相同 */
    public static final String FN_MAX_MIN_ERR = "wfr.expr.fn.arg_num_err";
    /** 函数参数类型必须为数值、字符串、日期 */
    public static final String FN_ARG_NUM_ERR = "wfr.expr.fn.arg_num_err";
    /** 函数需要一个数值类型的参数 */
    public static final String FN_ARG_1NUM_ERR = "wfr.expr.fn.arg_1num_err";
    /** 函数需要两个数值类型的参数 */
    public static final String FN_ARG_2NUM_ERR = "wfr.expr.fn.arg_2num_err";
    /** iif函数参数要求三个参数，并且第一个为布尔! */
    public static final String FN_IIF_ERR = "wfr.expr.fn.iif_err";
}
