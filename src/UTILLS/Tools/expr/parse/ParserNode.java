package UTILLS.Tools.expr.parse;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprException;
import UTILLS.Tools.expr.struct.OperNode;
import UTILLS.Tools.expr.struct.Operator;

final class ParserNode extends OperNode {

    /** 开始索引 */
    public int lo;
    /** 结束索引 */
    public int hi;
    /** 计算级别 */
    public int level;

    /**
     * 添加子节点
     * 
     * @param list
     * @return TreeNode
     */
    void addChild(OperNode node) {
        children.add(node);
    }

    /**
     * 对text文本值进行分析
     * 
     * @param node
     * @param low
     * @param high
     * @throws ExprException
     */
    void analyzeValue() throws ExprException {
        if (utext.length() > 0) {
            if (utext.equals("TRUE")) {
                value = true;
            } else if (utext.equals("FALSE")) {
                value = false;
            } else if (utext.equals("PI") || utext.equals("∏") || utext.equals("π")) {
                value = Math.PI;
            } else if (text.charAt(0) == '\'' && text.charAt(text.length() - 1) == '\'') {
                value = extractStr(text);
            } else {
                try {
                    value = Double.parseDouble(utext);
                } catch (Exception nothing) {
                    isParam = true;
                }
            }
        } else {
            throw new ExprException(Consts.PARSE_ERR, lo, hi);
        }
    }

    /**
     * 去掉字符串首尾单引号,转义'' ('' -> ')
     * 
     * @param s
     * @return String
     */
    static String extractStr(String s) {
        StringBuilder ret = new StringBuilder(s.length());
        for (int j = 2; j < s.length(); j++) {
            char c = s.charAt(j - 1);
            ret.append(c);
            char cNext = s.charAt(j);
            if (c == '\'' && cNext == '\'') {
                j++;
            }
        }
        return ret.toString();
    }

    ParserNode saveOperator(Operator operator, int start, int end) {
        this.operator = operator;
        this.lo = start;
        this.hi = end;
        return this;
    }

    void setText(String s) {
        text = s;
        utext = s == null ? null : s.toUpperCase();
    }

    @Override
    public String getPosition() {
        return "(" + lo + "," + hi + ")";
    }

}
