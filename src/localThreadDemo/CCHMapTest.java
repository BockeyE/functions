package localThreadDemo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class CCHMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, LongAdder> cmap = new ConcurrentHashMap<>();
        LongAdder adder = cmap.computeIfAbsent("", (key) -> new LongAdder());
        adder.increment();
    }
}
