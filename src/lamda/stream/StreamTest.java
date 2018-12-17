package lamda.stream;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author bockey
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Employee> employs = new ArrayList<>();

        employs.add(new Employee("张三", "男", 25));
        employs.add(new Employee("李四", "女", 24));
        employs.add(new Employee("王五", "女", 23));
        employs.add(new Employee("赵六", "男", 22));
        employs.add(new Employee("孙七", "女", 21));
        employs.add(new Employee("周八", "男", 20));
        employs.add(new Employee("吴九", "女", 19));
        employs.add(new Employee("郑十", "男", 18));

        Consumer<Employee> printAction = System.out::println;

        System.out.println("print all ems");
        employs.stream().forEach(printAction);


        System.out.println(" show as age");
        employs.stream().sorted((e1, e2) -> (e1.getAge() - e2.getAge())).forEach(printAction);

        System.out.println("print oldest women");
        Employee max1 = employs.stream().filter(e -> "女".equals(e.getGender())).max(
                (e1, e2) -> (e1.getAge() - e2.getAge())
        ).get();
        printAction.accept(max1);
        System.out.println("other showing");
        employs.stream().filter(
                (e) -> (e.getAge() > 20 && e.getGender().equals("男"))
        ).forEach(printAction);

        Collector<CharSequence, ?, String> collector = Collectors.joining(",");
        String s = employs.stream().map(Employee::getName).collect(collector);
        System.out.println(s);
    }
}
