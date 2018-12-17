package lamda;

import java.util.function.Function;

/**
 * @author bockey
 */
public class FourInternalInterfaces {

    public static void main(String[] args) {

        Function<Integer, String> f = (y) -> ("66" + y);
        String apply = f.apply(34);
        System.out.println(apply);
    }
}
