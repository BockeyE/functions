package UTILLS.Tools.expr.func.inner;

import java.util.List;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.func.IFunction;

public class FuncFloor implements IFunction {

    public static final FuncFloor instance = new FuncFloor();

    private FuncFloor() {
    }

    @Override
    public String getName() {
        return "floor";
    }

    @Override
    public Object invoke(List<Object> args) throws FuncException {
        Double x = null;
        if (args.size() != 1 || (x = ExprUtil.toNum(args.get(0))) == null) {
            throw new FuncException(Consts.FN_ARG_1NUM_ERR);
        }

        return Math.floor(x);
    }
}
