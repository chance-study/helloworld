package org.chance.grammar

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by gengchao on 15/12/26.
 */
class Operators {
    public static void main(String[] args) {
        f6()
        f8()
        f10()
    }

    /**
     * +,-,*,/,%,**
     */
    public static void ArithmeticOperators(){
        assert  1  + 2 == 3
        assert  4  - 3 == 1
        assert  3  * 5 == 15
        assert  3  / 2 == 1.5
        assert 10  % 3 == 1
        assert  2 ** 3 == 8
    }

    /**
     * The + and - operators are also available as unary operators:
     * ++ , --
     */
    public static void UnaryOperators() {
        assert +3 == 3
        assert -4 == 0 - 4

        assert -(-1) == 1


        def a = 2
        def b = a++ * 3

        assert a == 3 && b == 6

        def c = 3
        def d = c-- * 2

        assert c == 2 && d == 6

        def e = 1
        def f = ++e + 3

        assert e == 2 && f == 5

        def g = 4
        def h = --g + 1

        assert g == 3 && h == 4
    }

    /**
     * += -= *= /= %= **=
     */
    public static void assignmentArithmeticOperators(){
        def a = 4
        a += 3

        assert a == 7

        def b = 5
        b -= 3

        assert b == 2

        def c = 5
        c *= 3

        assert c == 15

        def d = 10
        d /= 2

        assert d == 5

        def e = 10
        e %= 3

        assert e == 1

        def f = 3
        f **= 2

        assert f == 9
    }

    /**
     * Relational operators
     * == != < <= > >=
     */
    public static void f1(){
        assert 1 + 2 == 3
        assert 3 != 4

        assert -2 < 3
        assert 2 <= 2
        assert 3 <= 4

        assert 5 > 1
        assert 5 >= -2
    }

    /**
     * Logical operators
     * && || ! 短路 & |
     * precedence : not > && > ||
     */
    public static void f2(){
        assert !false
        assert true && true
        assert true || false

        def called
        called = false
        true || (called=true)
        assert !called

        called = false
        false || c(called=true)
        assert called

        called = false
        false && (called=true)
        assert !called

        called = false
        true && (called=true)
        assert called
    }

    /**
     * Bitwise operators
     * &: bitwise "and"
     * |: bitwise "or"
     * ^: bitwise "xor" (exclusive "or")
     * ~: bitwise negation
     */
    public static void f3(){
        int a = 0b00101010
        assert a==42
        int b = 0b00001000
        assert b==8
        assert (a & a) == a
        assert (a & b) == b
        assert (a | a) == a
        assert (a | b) == a

        int mask = 0b11111111
        assert ((a ^ a) & mask) == 0b00000000
        assert ((a ^ b) & mask) == 0b00100010
        assert ((~a) & mask)    == 0b11010101
    }

    /**
     * Conditional operators
     */
    public static void f4(){
        assert (!true)    == false
        assert (!'foo')   == false
        assert (!'')      == true
    }

    /**
     * Ternary operator
     */
    public static void f5(){
        def string
        def result = (string!=null && string.length()>0) ? 'Found' : 'Not found'
        //Groovy truth
        result string?'Found':'Not Found'

        def user,displayName
        displayName = user.name ? user.name : 'Anonymous'
        displayName = user.name ?: 'Anonymous'

    }

    /**
     * Object operators
     */
    public static void f6(){
//        def person = org.chance.grammar.Operators.Person.find { it.id == 123 }
//        def name = person?.name
//        assert name == null

        def user=new org.chance.grammar.Operators.User('Bob')
        assert user.name=='Name: Bob'

        //use of .@ forces usage of the field instead of the getter
        assert user.@name=='Bob'

        /**
         * The method pointer operator (.&) call be used to
         * store a reference to a method in a variable, in order to call it later:
         */
        def str = 'example of method reference'
        def fun = str.&toUpperCase
        def upper = fun()
        assert upper == str.toUpperCase()

        /**
         *Method pointer operator
         */
        def action = this.&describe

        def list=[
                new Person(name:'Bob',age:42),
                new Person(name:'Julia',age:35)]
        transform(list,action)

        assert transform(list,action)==['Bob is 42','Julia is 35']

        def reference=this.&doSomething
        assert reference('foo')=='FOO'
        assert reference(123)==246


    }

    /**
     * Regular expression operators
     *
     */
    public static void f7(){

        /**
         * Pattern operator
         * The pattern operator (~) provides a simple way
         * to create a java.util.regex.Pattern instance:
         */
        def p = ~/foo/
        assert p instanceof Pattern
        def pattern =/foo/
        p = ~'foo'
        p = ~"foo"
        p = ~$/dollar/slashy $ string/$
        p = ~"${pattern}"

        /**
         * Find operator
         * Alternatively to building a pattern, you can directly use the
         * find operator =~ to build a java.util.regex.Matcher instance:
         */
        def text = "some text to math"
        def m=text =~ /match/
        assert m instanceof Matcher
        if(!m){
            throw new RuntimeException("Oops, text not found!")
        }

        /**
         * Match operator (==~)
         * ==~
         */
        m = text ==~/match/
        assert m instanceof Boolean
        if(m){
            throw new RuntimeException("Should not reach that point!")
        }


    }

    /**
     * Other operators ()
     */
    public static void f8(){
        /**
         * Spread operator (*.)
         * *. is used to invoke an action on all items of an aggregate object.
         *
         */
        def cars=[
                new Car(make:'Peugeot',model:'508'),
                null,
                new Car(make:'Renault',model:'Clio')
        ]
        def makes=cars*.make
//        assert makes==['Peugot','Renault']
        assert cars*.make==['Peugeot',null,'Renault']
        assert null*.make==null

        def composite=new CompositeObject()
        assert composite*.id==[1,2]
        assert composite*.name==['Foo','Bar']

        /**
         * Spreading method arguments (*)
         *
         */
        def args=[4,5,6]
        assert function(*args)==26
        args=[4]
        assert function(*args,5,6)==26

        /**
         * Spread list elements
         * (*list)
         */
        def items = [4,5]
        def list = [1,2,3,*items,6]
        assert list == [1,2,3,4,5,6]

        /**
         * Spread map elements
         * (*:map)
         */
        def m1 = [c:3, d:4]
        def map = [a:1, b:2, *:m1]
        assert map == [a:1, b:2, c:3, d:4]

        /**
         * Range operator(..)
         * ..
         * groovy.lang.Range implements LIst
         */
        def range = 0..5
        assert (0..5).collect() == [0, 1, 2, 3, 4, 5]
        assert (0..<5).collect() == [0, 1, 2, 3, 4]
        println ( (0..5).class)
        assert (0..5) instanceof java.util.List
        assert (0..5).size() == 6
        assert ('a'..'d').collect() == ['a','b','c','d']

        /**
         * Spaceship operator(<=>)
         * <=>
         */
        assert (1 <=> 1) == 0
        assert (1 <=> 2) == -1
        assert (2 <=> 1) == 1
        assert ('a' <=> 'z') == -1

        /**
         * Subscript operator []
         * getAt/putAt method
         */
        def list1 = [0,1,2,3,4]
        assert list1[2] == 2
        list1[2] = 4
        assert list1[0..2] == [0,1,4]
        list1[0..2] = [6,6,6]
        assert list1 == [6,6,6,3,4]

        def user=new User(id:1,name1:"Alex")
        assert user[0] == 1
        assert user[1] == 'Alex'
        user[1] = 'Bob'
        assert user.name1 == 'Bob'

        /**
         * Membership operator (in)
         * in
         */
        def list2 = ['Grace','Rob','Emmy']
        assert ('Emmy' in list2)
        //equivalent to calling list.contains('Emmy') or list.isCase('Emmy')

        /**
         * Identity operator ==
         * == equals  is
         */
        def list3 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
        def list4 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
        assert list3 == list4
        assert !list3.is(list4)

        /**
         * Coercion operator
         *The coercion operator (as) is a variant of casting.
         * asType
         */
        Integer x = 123
        String s = (String) x
        s = x as String
        def u=new User(name1:'Xavier')
        def p=u as Identifiable
        assert p instanceof Identifiable
        assert !(p instanceof User)

        /**
         * Diamond operator (<>)
         *
         */

        java.util.List<String> strings = new LinkedList<>()

        /**
         * Call operator ( () )
         * ()  .call
         */
        def mc = new MyCallable()
        assert mc.call(2)==4
        assert mc(2)==4


    }

    /**
     * Operator overloading
     * Here is a complete list of the operators
     * and their corresponding methods:
     * Operator	Method	Operator	Method
     * + a.plus(b)
     * a[b] a.getAt(b)
     * - a.minus(b)
     * a[b] = c a.putAt(b, c)
     * a.multiply(b)
     * a in b b.isCase(a)
     * / a.div(b)
     * << a.leftShift(b)
     * % a.mod(b)
     * >> a.rightShift(b)
     * ** a.power(b)
     * >>> a.rightShiftUnsigned(b)
     * | a.or(b)
     * ++ a.next()
     * & a.and(b)
     * -- a.previous()
     * ^ a.xor(b)
     * +a a.positive()
     * as a.asType(b)
     * -a a.negative()
     * a() a.call()
     * ~a a.bitwiseNegative()
     */
    public static void f10(){
        def b1=new Bucket(4)
        def b2=new Bucket(11)
        assert (b1+b2).size==15
//        assert (b1 + 11).size == 15


    }

    static int function(int x,int y,int z){
        x*y+z
    }

    static def transform(java.util.List elements,Closure action){
        def result=[]
        elements.each{
            result << action(it)
        }
        result

    }

    static def doSomething(String str){
        str.toUpperCase()
    }

    static def doSomething(Integer x){
        2*x
    }

    static String describe(org.chance.grammar.Operators.Person p){
        "$p.name is $p.age"
    }

    static class MyCallable{
        int call(int x){
            2*x
        }
    }

    static class Person{
        int id
        def name
        def sex
        def age
    }

    static class Identifiable{
        String name1
    }

    static class User{
        Long id
        String name1

        User(){}
        private final String name
        User(String name) { this.name = name}
        String getName() { "Name: $name" }

        def getAt(int i){
            switch(i){
                case 0:return id
                case 1:return name1
            }
            throw new IllegalArgumentException("No such element $i")
        }

        void putAt(int i,def value){
            switch (i){
                case 0:id=value;return
                case 1:name1=value;return
            }
            throw new IllegalArgumentException("No such element $i")
        }

        def asType(Class target){
            if(target==Identifiable){
                return new Identifiable(name1:name1)
            }
            throw new ClassCastException("User cannot be coerced into $target")
        }

    }

    static class Car{
        def make
        String model
    }

    static class Component{
        Long id
        String name
    }
    static class CompositeObject implements Iterable<Component>{
        def components=[
                new Component(id:1,name:'Foo'),
                new Component(id:2,name:'Bar')
        ]

        @Override
        Iterator<Component> iterator() {
            components.iterator()
        }
    }

    static class Bucket{
        int size
        Bucket(int size){this.size=size}

        Bucket plus(Bucket other){
            return new Bucket(this.size + other.size)

        }

        Bucket plus(int capacity) {
            return new Bucket(this.size + capacity)
        }
    }
}

/**
 *Operator precedence
 * 1 new   () object creation, explicit parentheses
 ()   {}   [] method call, closure, literal list/map
 .   .&   .@ member access, method closure, field/attribute access
 ?.   *   *.   *: safe dereferencing, spread, spread-dot, spread-map
 ~   !   (type) bitwise negate/pattern, not, typecast
 []   ++   -- list/map/array index, post inc/decrement
 2
 ** power
 3
 ++   --   +   - pre inc/decrement, unary plus, unary minus
 4
 *   /   % multiply, div, modulo
 5
 +   - addition, subtraction
 6
 <<   >>   >>>   ..   ..< left/right (unsigned) shift, inclusive/exclusive range
 7
 <   <=   >   >=   in   instanceof   as less/greater than/or equal, in, instanceof, type coercion
 8
 ==   !=   <=> equals, not equals, compare to
 =~   ==~ regex find, regex match
 9
 & binary/bitwise and
 10
 ^ binary/bitwise xor
 11
 | binary/bitwise or
 12
 && logical and
 13
 || logical or
 14
 ? : ternary conditional
 ?: elvis operator
 15
 =   **=   *=   /=   %=   +=   -=
 <<=   >>=   >>>=   &=   ^=   |= various assignments
 *
 *
 * */


