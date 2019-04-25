package UTILLS.Tools.expr.func.inner;

import java.util.Date;
import java.util.List;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.func.IFunction;

public class FuncMax implements IFunction {

    public static final FuncMax instance = new FuncMax();

    private FuncMax() {
    }

    @Override
    public String getName() {
        return "max";
    }

    @Override
    public Object invoke(List<Object> args) throws FuncException {
        if (args.size() < 1) {
            throw new FuncException(Consts.FN_ARG_NO_ERR);
        }

        Object ret = null;
        int expectedType = -1;
        for (Object arg : args) {
            int currentType = -1;
            if (arg == null) {
                continue;
            } else if (arg instanceof Number) {
                currentType = 0;
            } else if (arg instanceof Date) {
                currentType = 1;
            } else if (arg instanceof CharSequence) {
                currentType = 2;
            } else {
                throw new FuncException(Consts.FN_MAX_MIN_ERR);
            }

            if (expectedType == -1) {
                expectedType = currentType;
                ret = arg;
                continue;
            }

            if (currentType != expectedType) {
                throw new FuncException(Consts.FN_MAX_MIN_ERR);
            }

            Integer icmp = ExprUtil.cmp(ret, arg);
            if (icmp != null && icmp < 0) {
                ret = arg;
            }
        }
        return ret;
    }

}
