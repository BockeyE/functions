package UTILLS.Tools.expr.struct;

import java.util.ArrayList;
import java.util.List;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprException;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.func.IFunction;
import UTILLS.Tools.expr.func.inner.SysFuncManager;
import UTILLS.Tools.expr.struct.Linker.Entry;

/**
 * 枚举类型 操作符 Val:最高优先级 值类型
 */
public enum Operator {

    Or(1) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Boolean b1 = null;
            Boolean b2 = null;
            if ((b1 = ExprUtil.toBool(node.children.first().value)) == null
                    || (b2 = ExprUtil.toBool(node.children.second().value)) == null) {
                throw new ExprException(Consts.LOGIC_ERR);
            }

            node.value = b1 || b2;
        }
    },
    And(2) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Boolean b1 = null;
            Boolean b2 = null;
            if ((b1 = ExprUtil.toBool(node.children.first().value)) == null
                    || (b2 = ExprUtil.toBool(node.children.second().value)) == null) {
                throw new ExprException(Consts.LOGIC_ERR);
            }

            node.value = b1 || b2;
        }
    },
    In(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Object o = node.children.first().value;
            for (Entry<OperNode> e = node.children.getHead().getNext(); e != null; e = e.getNext()) {
                Integer iCmp = ExprUtil.cmp(o, e.elem.value);
                if (iCmp != null && iCmp.intValue() == 0) {
                    node.value = true;
                }
            }
            node.value = false;
        }
    },
    Lit(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp < 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    LitE(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp <= 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    Big(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp > 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    BigE(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp >= 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    Equ(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp == 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    Ueq(3) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Integer iCmp = ExprUtil.cmp(node.children.first().value, node.children.second().value);
            if (iCmp != null) {
                node.value = iCmp != 0;
            } else {
                throw new ExprException(Consts.CMP_ERR);
            }
        }
    },
    Add(4) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Object o1 = node.children.first().value;
            Object o2 = node.children.second().value;
            Double num1 = null;
            Double num2 = null;
            String str1 = null;
            String str2 = null;
            if ((num1 = ExprUtil.toNum(o1)) != null && (num2 = ExprUtil.toNum(o2)) != null) {
                node.value = num1 + num2;
            } else if ((str1 = ExprUtil.toString(o1)) != null && (str2 = ExprUtil.toString(o2)) != null) {
                node.value = str1 + str2;
            } else {
                throw new ExprException(Consts.ADD_ERR);
            }
        }
    },
    Sub(4) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num1 = ExprUtil.toNum(node.children.first().value);
            Double num2 = ExprUtil.toNum(node.children.second().value);
            if (num1 != null && num2 != null) {
                node.value = num1 - num2;
            } else {
                throw new ExprException(Consts.SUB_ERR);
            }
        }
    },
    Mul(5) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num1 = ExprUtil.toNum(node.children.first().value);
            Double num2 = ExprUtil.toNum(node.children.second().value);
            if (num1 != null && num2 != null) {
                node.value = num1 * num2;
            } else {
                throw new ExprException(Consts.MUL_ERR);
            }
        }
    },
    Div(5) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num1 = ExprUtil.toNum(node.children.first().value);
            Double num2 = ExprUtil.toNum(node.children.second().value);
            if (num1 != null && num2 != null) {
                if (Math.abs(num2) < 0.00000001) {
                    throw new ExprException(Consts.DIV_0_ERR);
                } else {
                    node.value = num1 / num2;
                }
            } else {
                throw new ExprException(Consts.DIV_ERR);
            }
        }
    },
    Mod(5) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num1 = ExprUtil.toNum(node.children.first().value);
            Double num2 = ExprUtil.toNum(node.children.second().value);
            if (num1 != null && num2 != null) {
                if (num2.longValue() == 0l) {
                    throw new ExprException(Consts.DIV_0_ERR);
                } else {
                    node.value = num1.longValue() % num2.longValue();
                }
            } else {
                throw new ExprException(Consts.MUL_ERR);
            }
        }
    },
    Not(6) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Boolean b = ExprUtil.toBool(node.children.first().value);
            if (b != null) {
                node.value = !b;
            } else {
                throw new ExprException(Consts.NOT_ERR);
            }
        }
    },
    Rev(6) {// 负号

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num = ExprUtil.toNum(node.children.first().value);
            if (num != null) {
                node.value = -num;
            } else {
                throw new ExprException(Consts.REV_ERR);
            }
        }
    },
    Plus(6) {// 正号

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num = ExprUtil.toNum(node.children.first().value);
            if (num != null) {
                node.value = num;
            } else {
                throw new ExprException(Consts.PLUS_ERR);
            }
        }
    },
    Pwr(7) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            Double num1 = ExprUtil.toNum(node.children.first().value);
            Double num2 = ExprUtil.toNum(node.children.second().value);
            if (num1 != null && num2 != null) {
                node.value = Math.pow(num1, num2);
            } else {
                throw new ExprException(Consts.PWR_ERR);
            }
        }
    },
    Fun(8) {

        @Override
        public void calc(Context ctx, OperNode node) throws ExprException {
            IFunction func = ctx.funcs.get(node.getUtext());
            if (func == null) {
                func = SysFuncManager.getFunction(node.getUtext());
            }
            if (func != null) {
                List<Object> args = new ArrayList<Object>();
                for (Entry<OperNode> e = node.children.getHead(); e != null; e = e.getNext()) {
                    args.add(e.elem.value);
                }
                node.value = func.invoke(args);
            } else {
                throw new ExprException(Consts.FN_UNKOWN);
            }
        }
    };

    public final int level;

    private Operator(int level) {
        this.level = level;
    }

    public abstract void calc(Context ctx, OperNode node) throws ExprException;

}
