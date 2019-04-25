package UTILLS.Tools.expr.func.inner;

import java.util.List;


import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.func.IFunction;

public class FuncAbs implements IFunction {

    public static final FuncAbs instance = new FuncAbs();

    private FuncAbs() {
    }

    @Override
    public String getName() {
        return "abs";
    }

    @Override
    public Object invoke(List<Object> args) throws FuncException {
        Double x = null;
        if (args.size() != 1 || (x = ExprUtil.toNum(args.get(0))) == null) {
            throw new FuncException(Consts.FN_ARG_1NUM_ERR);
        }

        return Math.abs(x);
    }
}
