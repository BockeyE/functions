package UTILLS.Tools.expr.parse;

import java.util.Comparator;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprException;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.struct.Linker;
import UTILLS.Tools.expr.struct.OperNode;
import UTILLS.Tools.expr.struct.Operator;
import UTILLS.Tools.expr.struct.Stack;
import UTILLS.Tools.expr.struct.Linker.Entry;

public class Parser {

    // 表达式字符串
    public final String expr;
    // 表达式字符数组
    public final char[] exprChars;

    // 根节点
    public final OperNode root;

    private BracketData[] brackets;

    private Linker<ParserNode> opNodes = new Linker<ParserNode>();

    private static class BracketData {
        /** -1:"(" 0:not"(" and ")" 1:")" */
        public byte flag = 0;
        /** If flag=-1,right save ")" position, else -1 */
        public int right = -1;
        /** 下标位置 */
        public int index = -1;
        /** 左括号前面部分 */
        public String prefix = "";
    }

    private static final Comparator<ParserNode> nodeLevelSorter = new Comparator<ParserNode>() {
        @Override
        public int compare(ParserNode o1, ParserNode o2) {
            int ret = o1.level - o2.level;
            if (ret == 0) {
                return o2.lo - o1.lo;
            }
            return ret;
        }
    };

    public Parser(String expression) throws ExprException {
        if (null == expression || expression.trim().length() == 0) {
            throw new ExprException(Consts.BLANK_ERR, 0, 0);
        }
        this.expr = expression;
        this.exprChars = expression.toCharArray();
        extractOperators();
        root = buildNode(0, expr.length() - 1);
    }

    private void saveOperator(Operator op, int lo, int hi, int levelFactor) {
        ParserNode node = new ParserNode();
        node.saveOperator(op, lo, hi);
        node.level = levelFactor + op.level;
        opNodes.addSort(node, nodeLevelSorter);
    }

    /**
     * 抽取操作符
     */
    private void extractOperators() throws ExprException {
        brackets = new BracketData[expr.length()];
        Stack<BracketData> stack = new Stack<BracketData>();
        boolean canPrevFollowRev = true;// 前面的字符后面是否可以取负操作
        int factor = 0;

        StringBuilder sbIdent = new StringBuilder();
        for (int i = 0, len = expr.length(); i < len; i++) {
            boolean canCurrFollowRev = true;// 当前字符后面是否可以取负操作
            char c = ExprUtil.upper(exprChars[i]);
            BracketData bracket = new BracketData();
            bracket.index = i;
            switch (c) {
            case '\'':
                canCurrFollowRev = false;
                i = jumpQuoter(i, len);
                break;
            case '(':
                factor += 100;
                bracket.prefix = sbIdent.toString();
                bracket.flag = -1;
                stack.push(bracket);
                break;
            case ')':
                factor -= 100;
                canCurrFollowRev = false;
                bracket.flag = 1;
                BracketData lBracket = stack.pop();
                if (lBracket == null) {
                    throw new ExprException(Consts.BRACKET_ERR, i, i);
                }
                lBracket.right = bracket.index;
                int prefixLen = lBracket.prefix.length();
                if (prefixLen > 0) {
                    saveOperator(Operator.Fun, lBracket.index - prefixLen, lBracket.index - 1, factor);
                }
                break;
            case '+':
            case '＋':
                if (!canPrevFollowRev) {
                    saveOperator(Operator.Add, i, i, factor);
                } else {
                    saveOperator(Operator.Plus, i, i, factor);
                }
                break;
            case '-':
            case '－':
                if (!canPrevFollowRev) {
                    saveOperator(Operator.Sub, i, i, factor);
                } else {
                    saveOperator(Operator.Rev, i, i, factor);
                }
                break;
            case '/':
            case '÷':
                saveOperator(Operator.Div, i, i, factor);
                break;
            case '%':
                saveOperator(Operator.Mod, i, i, factor);
                break;
            case '*':
            case '×':
                saveOperator(Operator.Mul, i, i, factor);
                break;
            case '^':
                saveOperator(Operator.Pwr, i, i, factor);
                break;
            case '&':
                if (charAt(i + 1) == '&') {
                    saveOperator(Operator.And, i, ++i, factor);
                } else {
                    saveOperator(Operator.And, i, i, factor);
                }
                break;
            case '|':
                if (charAt(i + 1) == '|') {
                    saveOperator(Operator.Or, i, ++i, factor);
                } else {
                    saveOperator(Operator.Or, i, i, factor);
                }
                break;
            case '>':
                if (charAt(i + 1) == '=') {
                    saveOperator(Operator.BigE, i, ++i, factor);
                } else {
                    saveOperator(Operator.Big, i, i, factor);
                }
                break;
            case '<':
                int c1 = charAt(i + 1);
                if (c1 == '>') {
                    saveOperator(Operator.Ueq, i, ++i, factor);
                } else if (c1 == '=') {
                    saveOperator(Operator.LitE, i, ++i, factor);
                } else {
                    saveOperator(Operator.Lit, i, i, factor);
                }
                break;
            case '=':
                if (charAt(i + 1) == '=') {
                    saveOperator(Operator.Equ, i, ++i, factor);
                } else {
                    saveOperator(Operator.Equ, i, i, factor);
                }
                break;
            case '!':
                if (charAt(i + 1) == '=') {
                    saveOperator(Operator.Ueq, i, ++i, factor);
                } else {
                    saveOperator(Operator.Not, i, i, factor);
                }
                break;
            case ',':
                canCurrFollowRev = true;
                break;
            case 'I':
                if (ExprUtil.upper(charAt(i + 1)) == 'N' && !ExprUtil.isAlpha(charAt(i - 1))
                        && !ExprUtil.isAlpha(charAt(i + 2))) {
                    saveOperator(Operator.In, i, ++i, factor);
                } else {
                    canCurrFollowRev = c < ' ' && canPrevFollowRev;
                }
                break;
            default:
                canCurrFollowRev = c < ' ' && canPrevFollowRev;
                break;
            }

            canPrevFollowRev = canCurrFollowRev;
            brackets[i] = bracket;

            if (canCurrFollowRev || c == ')') {
                sbIdent.setLength(0);
            } else if (c > ' ') {
                sbIdent.append(c);
            }
        }

        if (!stack.isEmpty()) {
            BracketData bracket = stack.pop();
            throw new ExprException(Consts.BRACKET_ERR, bracket.index, bracket.index);
        }

        for (int i = brackets.length - 1; i > -1; i--) {
            if (brackets[i] == null) {
                brackets[i] = new BracketData();
            }
        }
    }

    private void buildFuncArgNode(int lo, int hi, ParserNode func) throws ExprException {
        int savedLo = lo, savedHi = hi;
        for (; exprChars[lo] <= ' ' && lo <= hi; lo++) {
            ;
        }
        for (; exprChars[hi] <= ' ' && lo <= hi; hi--) {
            ;
        }
        if (lo > hi || brackets[lo].flag != -1 || brackets[lo].right != hi) {
            throw new ExprException(Consts.PARSE_ERR, savedLo, savedHi);
        }
        lo++;
        hi--;

        for (int i = lo, bracket = 0, prev = lo; i <= hi; i++) {
            switch (exprChars[i]) {
            case '\'':
                i = jumpQuoter(i, hi);
                break;
            case '(':
                bracket++;
                break;
            case ')':
                bracket--;
                break;
            case ',':
                if (bracket == 0) {
                    func.addChild(buildNode(prev, i - 1));
                    prev = i + 1;
                }
                break;
            default:

            }
            if (i == hi) {
                func.addChild(buildNode(prev, i));
            }
        }
    }

    /**
     * 创建树节点
     * 
     * @param lo
     * @param hi
     * @return
     * @throws ExprException
     */
    private ParserNode buildNode(int lo, int hi) throws ExprException {
        int iLo = lo, iHi = hi;
        while (true) {
            while (lo <= hi && exprChars[lo] <= ' ') {
                lo++;
            }
            while (lo <= hi && exprChars[hi] <= ' ') {
                hi--;
            }

            if (lo < hi && brackets[lo].flag == -1 && brackets[lo].right == hi) {
                lo++;
                hi--;
            } else {
                break;
            }
        }

        if (lo > hi) {
            throw new ExprException(Consts.PARSE_ERR, iHi, iLo);
        }

        ParserNode result = findOperator(lo, hi);
        if (result != null) {
            result.setText(trimSubstr(result.lo, result.hi + 1));
            Operator oper = result.getOperator();
            switch (oper) {
            case Fun:
                buildFuncArgNode(result.hi + 1, hi, result);
                break;
            case In:
                // eg: "1 in (1,2,3)", deal "1"
                result.addChild(buildNode(lo, result.lo - 1));
                buildFuncArgNode(result.hi + 1, hi, result);
                break;
            case Plus:
            case Rev:
            case Not:
                result.addChild(buildNode(result.hi + 1, hi));
                break;
            default:
                result.addChild(buildNode(lo, result.lo - 1));
                result.addChild(buildNode(result.hi + 1, hi));
            }
        } else {
            result = new ParserNode();
            result.setText(trimSubstr(lo, hi + 1));
            result.analyzeValue();
        }

        return result;
    }

    private String trimSubstr(int lo, int hi) {
        while (lo <= hi && (exprChars[lo] <= ' ')) {
            lo++;
        }
        while (lo <= hi - 1 && (exprChars[hi - 1] <= ' ')) {
            hi--;
        }

        return expr.substring(lo, hi);
    }

    private char charAt(int i) {
        if (i > -1 && i < exprChars.length) {
            return exprChars[i];
        }
        return 0;
    }

    /**
     * 先后找单引号,返回与之匹配的最后一个单引号的索引位置(EX:'a+b'+'c+d')
     * 
     * @param i
     * @param hi
     * @return
     */
    private int jumpQuoter(int i, int hi) {
        for (int k = i + 1; k <= hi; k++) {
            if (exprChars[k] == '\'') {
                if (charAt(k + 1) == '\'') {
                    k++;
                } else {
                    return k;
                }
            }
        }
        return i;
    }

    /**
     * 找操作符信息
     * 
     * @param lo
     * @param hi
     * @param result
     */
    private ParserNode findOperator(int lo, int hi) {
        for (Entry<ParserNode> e = opNodes.getHead(); e != null; e = e.getNext()) {
            ParserNode node = e.elem;
            if (node.lo >= lo && node.hi <= hi) {
                e.remove();
                return node;
            }
        }
        return null;
    }

}
