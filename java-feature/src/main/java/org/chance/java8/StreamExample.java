package org.chance.java8;

import java.io.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by gengchao on 16/6/12.
 */
public class StreamExample {

    private static class NewStreamClient{
        public static void main(String[] args) {
            //Individual values
            Stream<String> stream = Stream.of("a","b","c");

            //Arrays
            String[] strArray = new String[]{"a"+"b"+"c"};
            stream = Stream.of(strArray);
            stream = Arrays.stream(strArray);


            //Collections
            List<String> list = Arrays.asList(strArray);
            stream = list.stream();

            //IntStream  >  Stream<Integer>
            //LongStream > Stream<Long>
            //DoubleStream > Stream<Double>
            IntStream intStream = IntStream.of(new int[]{1,2,3,4});
            intStream=IntStream.range(1,3);
            intStream.forEach(System.out::println);
            intStream=IntStream.rangeClosed(1,3);
            intStream.forEach(System.out::println);

            //流转换为其它数据结构
            //Array
            String[] strArray1=stream.toArray(String[]::new);
            //Collection
            List<String> list1=stream.collect(Collectors.toList());
            List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
            Set set1 = stream.collect(Collectors.toSet());
            Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));

            //Stream
            String str = stream.collect(Collectors.joining()).toString();

        }
    }

    private static class OpStream{
        public static void main(String[] args) throws IOException {
            //Intermediate
            //map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered

            Arrays.asList("sdfP","Dssd")
                    .stream()
                    .map(String::toUpperCase)
                    .forEach(System.out::println);  // SDFP DSSD

            Arrays.asList(1,2,3)
                    .stream()
                    .map(n->n*n)
                    .forEach(System.out::println);  // 1 4 9

            Stream<List<Integer>> inputStream = Stream.of(
                    Arrays.asList(1),
                    Arrays.asList(2,3),
                    Arrays.asList(4,5,6)
            );
//            inputStream.map(n->n.size())
//                    .forEach(System.out::println);
            inputStream.flatMap(
                    (childList) -> childList.stream())
                    .forEach(System.out::println);

            Integer[] intArr =  Stream.of(1,2,3,4,5,6)
                    .filter(n-> n%2==0)
                    .toArray(Integer[]::new);
            System.out.println("intArr = " + intArr.toString());

            System.out.println("-----------");

            Stream.of("noe","two","three","four")
                    .filter(e->e.length()>3)
                    .peek(System.out::println)
                    .map(String::toUpperCase)
                    .peek(System.out::println)
                    .collect(Collectors.toList())
                    .toString();


            new StreamExample().testLimitAndSkip();

            //有一种情况是 limit/skip 无法达到 short-circuiting 目的的
            List<Person> persons = new ArrayList();
            for(int i=1;i<=5;i++){
                Person p = new Person(i,"name"+i);
                persons.add(p);
            }
            List<Person> personList2=
                    persons.stream().sorted((p1, p2) ->
                            p1.getName().compareTo(p2.getName()))
                    .limit(2)
                    .collect(Collectors.toList());
            System.out.println("personList2 = " + personList2);

            List<Person> personList3 = persons.stream().limit(2).sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
            System.out.println(personList3);

            System.out.println(new File("java8-new-feature/src/main/resources/test.log").getAbsolutePath());
            BufferedReader br = new BufferedReader(new FileReader("java8-new-feature/src/main/resources/test.log"));
            int longest =br.lines()
                    .mapToInt(String::length)
                    .max()
                    .getAsInt();

            System.out.println("longest = " + longest);

            List<String> words = br.lines()
                    .flatMap(line->Stream.of(line.split(" ")))
                    .filter(word->word.length()>0)
                    .map(String::toLowerCase)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println("words = " + words);
            br.close();
            //Terminal
            //forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator

            String concat = Stream.of("A","B","C","D")
                    .reduce("",String::concat);
            System.out.println("concat = " + concat);
            double minValue = Stream.of(-1.5,1.0,-3.0,-2.0)
                    .reduce(Double.MAX_VALUE,Double::min);
            System.out.println("minValue = " + minValue);

            //有初始值,返回具体的对象
            int sumValue = Stream.of(1,2,3,4)
                    .reduce(0,Integer::sum);
            System.out.println("sumValue = " + sumValue);
            //没有初始值返回Optional
            sumValue = Stream.of(1,2,3,4)
                    .reduce(Integer::sum).get();
            concat=Stream.of("a","B","c","D","e","F")
                    .filter(x -> x.compareTo("Z") > 0)
                    .reduce("", String::concat);
            System.out.println("sumValue = " + sumValue);
            System.out.println("concat = " + concat);

            //Short-circuiting
            //anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit

            String strA = "  abcd", strB = null;
            print(strA);
            print("");
            print(strB);
            System.out.println(getLength(strA));
            System.out.println(getLength(""));
            System.out.println(getLength(strB));

            List<Person> persons3 = new ArrayList();
            persons3.add(new Person(1,"name"+1,10));
            persons3.add(new Person(2,"name"+2,21));
            persons3.add(new Person(3,"name"+3,34));
            persons3.add(new Person(4,"name"+4,6));
            persons3.add(new Person(5, "name" + 5, 55));
            boolean isAllAdult = persons3.stream()
                    .allMatch(p->p.getAge()>18);
            System.out.println("isAllAdult = " + isAllAdult);
            boolean isThereAnyChild=persons3.stream()
                    .anyMatch(p->p.getAge()<12);
            System.out.println("isThereAnyChild = " + isThereAnyChild);

            Stream.iterate(0, n->n+3)
                    .limit(10)
                    .forEach(x-> System.out.println("x = " + x));

        }
    }

    private static class AdvancedFeatureStream{
        public static void main(String[] args) {
            //进阶：自己生成流
            Random seed = new Random();
            Supplier<Integer> random = seed::nextInt;
            Stream.generate(random).limit(10).forEach(System.out::println);

            IntStream.generate(()->(int)(System.nanoTime()%100))
                    .limit(10)
                    .forEach(System.out::println);

            Stream.generate(new PersonSupplier())
                    .limit(10)
                    .forEach(p-> System.out.println(p.getName()+". " + p.getAge()));


            //用 Collectors 来进行 reduction 操作
            Map<Integer, List<Person>> personGroups =
                    Stream.generate(new PersonSupplier())
                    .limit(100)
                    .collect(Collectors.groupingBy(Person::getAge));
            Iterator it = personGroups.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Integer,List<Person>> person = (Map.Entry<Integer, List<Person>>) it.next();
                System.out.println("Age"+person.getKey()+" = "+person.getValue().size());

            }

            Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier())
                    .limit(100)
                    .collect(Collectors.partitioningBy(p->p.getAge()<10));
            System.out.println("children number = " + children.get(true).size());
            System.out.println("adult number = " + children.get(false).size());




        }

        private static class PersonSupplier implements Supplier<Person> {
            private int index =0;
            private Random random = new Random();


            @Override
            public Person get() {
                return new Person(index++,"StormTestUser"+index, random.nextInt(100));
            }
        }
    }

    public static void print(String text){
        //java8
        Optional.ofNullable(text).ifPresent(System.out::println);

        // pre-java8
        if(text != null) System.out.println(text);
    }

    public static int getLength(String text){
        //java 8
        return Optional.ofNullable(text)
                .map(String::length)
                .orElse(-1);

        //pre-java8
//        return text != null ? text.length(): -1 ;

    }

    public void testLimitAndSkip(){
        List<Person> persons = new ArrayList<>();
        for(int i=1;i<=10000;i++){
            Person p = new Person(i,"name"+i);
            persons.add(p);
        }

        List<String> personList2 = persons.stream()
                .map(Person::getName)
                .limit(10)
                .skip(3)
                .collect(Collectors.toList());
        System.out.println("personList2 = " + personList2);
    }

    private static class Person{
        public int no;
        private String name;
        private int age;

        public Person(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public Person(int no, String name,int age){
            this(no,name);
            this.age = age;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }



}
