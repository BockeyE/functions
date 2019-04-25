package UTILLS.Tools.expr.func.inner;

import java.util.List;

import UTILLS.Tools.expr.FuncException;
import UTILLS.Tools.expr.func.IFunction;

public class FuncContainStr implements IFunction {

    public static final FuncContainStr instance = new FuncContainStr();

    private FuncContainStr() {
    }

    @Override
    public Object invoke(List<Object> args) throws FuncException {
        if (null == args || args.size() < 2) {
            return false;
        }
        CharSequence s = args.get(0) + "";
        CharSequence sub = args.get(1) + "";
        int len = 0;
        int subLen = 0;
        if (s == null || sub == null || (len = s.length()) < (subLen = sub.length())) {
            return false;
        }
        for (int i = 0, count = len - subLen + 1; i < count; i++) {
            boolean isEq = true;
            for (int k = 0, t = i; k < subLen; k++, t++) {
                if (lower(s.charAt(t)) != lower(sub.charAt(k))) {
                    isEq = false;
                    break;
                }
            }
            if (isEq) {
                return true;
            }
        }
        return false;
    }

    public char lower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c - ('A' - 'a'));
        } else {
            return c;
        }
    }

    @Override
    public String getName() {
        return "containStr";
    }
}
