package UTILLS.Tools.expr.func.inner;

import java.util.List;

import UTILLS.Tools.expr.Consts;
import UTILLS.Tools.expr.ExprUtil;
import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.func.IFunction;

public class FuncAvg implements IFunction {

    public static final FuncAvg instance = new FuncAvg();

    private FuncAvg() {
    }

    @Override
    public String getName() {
        return "avg";
    }

    @Override
    public Object invoke(List<Object> args) throws FuncException {
        if (args.size() < 1) {
            throw new FuncException(Consts.FN_ARG_NO_ERR);
        }

        double sum = 0;
        for (Object arg : args) {
            Double d = null;
            if ((d = ExprUtil.toNum(arg)) == null) {
                throw new FuncException(Consts.FN_ARG_NUM_ERR);
            }
            sum += d;
        }
        return sum / args.size();
    }
}
