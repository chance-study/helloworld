package org.chance.grammar

/**
 * Created by gengchao on 15/12/27.
 * Object orientation
 * This chapter covers the object orientation of the Groovy programming language.
 */

/**
 * Types
 integral types: byte (8 bit), short (16 bit), int (32 bit) and long (64 bit)
 floating-point types: float (32 bit) and double (64 bit)
 boolean type (exactly true or false)
 char type (16 bit, usable as a numeric type, representing an UTF-16 code)

 Primitive type	Wrapper class
 boolean Boolean
 char Character
 short Short
 int Integer
 long Long
 float Float
 double Double

 */

class Foo{
    static int i
}

assert Foo.class.getDeclaredField('i').type == int.class
assert Foo.i != int.class && Foo.i.class == Integer.class

/**
 * Class
 */
/**
 * Normal class
 */
class Person {

    String name
    Integer age

    def increaseAge(Integer years) {
        this.age += years
    }
}
def p=new Person()

/**
 * Inner class
 */
class Outer {
    private String privateStr

    def callInnerMethod() {
        new Inner().methodA()
    }

    class Inner {
        def methodA() {
            println "${privateStr}."
        }
    }
}

class Outer2 {
    private String privateStr = 'some string'

    def startThread() {
        new Thread(new Inner2()).start()
    }

    class Inner2 implements Runnable {
        void run() {
            println "${privateStr}."
        }
    }
}

//Anonymous inner class
class Outer3 {
    private String privateStr = 'some string'

    def startThread() {
        new Thread(new Runnable() {
            void run() {
                println "${privateStr}."
            }
        }).start()
    }
}

/**
 * Abstract class
 */

abstract class Abstract {
    String name

    abstract def abstractMethod()

    def concreteMethod() {
        println 'concrete'
    }
}


/**
 * Interface
 */
interface Greeter {
    void greet(String name)
}

class SystemGreeter implements Greeter {
    void greet(String name) {
        println "Hello $name"
    }
}

def greeter = new SystemGreeter()
assert greeter instanceof Greeter

interface ExtendedGreeter extends Greeter {
    void sayBye(String name)
}

class DefaultGreeter {
    void greet(String name) { println "Hello" }
}
greeter = new DefaultGreeter()
assert !(greeter instanceof Greeter)

greeter = new DefaultGreeter()
coerced = greeter as Greeter
assert coerced instanceof Greeter


/**
 *Constructors
 * */
class PersonConstructor {
    String name
    Integer age

    PersonConstructor(){}

    PersonConstructor(name, age) {
        this.name = name
        this.age = age
    }
}

def person1 = new PersonConstructor('Marie', 1)
def person2 = ['Marie', 2] as PersonConstructor
PersonConstructor person3 = ['Marie', 3]
def person4 = new PersonConstructor()
def person5 = new PersonConstructor(name: 'Marie')
def person6 = new PersonConstructor(age: 1)
def person7 = new PersonConstructor(name: 'Marie', age: 2)

/**
 * Methods
 */
//Method definition
def someMethod() { 'method called' }
String anotherMethod() { 'another method called' }
def thirdMethod(param1) { "$param1 passed" }
static String fourthMethod(String param1) { "$param1 passed" }

//Default arguments
def foo(String par1, Integer par2 = 1) { [name: par1, age: par2] }
assert foo('Marie').age == 1

//Varargs
def foo(Object... args) { args.length }
assert foo() == 0
assert foo(1) == 1
assert foo(1, 2) == 2

def foo1(Object[] args) { args.length }
assert foo1() == 0
assert foo1(1) == 1
assert foo1(1, 2) == 2

def foo2(Object... args) { args }
assert foo2(null) == null

def foo3(Object... args) { args }
Integer[] ints = [1, 2]
assert foo3(ints) == [1, 2]

def foo4(Object... args) { 1 }
def foo4(Object x) { 2 }
assert foo4() == 1
assert foo4(1) == 2
assert foo4(1, 2) == 1

/**
 * Fields and properties
 *
 */

//Fields
class Data {
    private int id = IDGenerator.next()
    protected String description
    public static final boolean DEBUG = false

    private Map<String,String> mapping

}

//Properties
class Person1 {
    final String name   //read-only
    final int age
    Person1(){}
    Person1(String name, int age) {
        this.name = name
        this.age = age
    }
}


def pp=new Person1()
assert pp.properties.keySet().containsAll(['name','age'])

/**
 * Annotation  @interface
 */
//Annotation definition
@interface SomeAnnotation {

}
@interface SomeAnnotation1 {
    String value()
}
@interface SomeAnnotation2 {
    String value() default 'something'
}
@interface SomeAnnotation3 {
    int step()
}
@interface SomeAnnotation4 {
    Class appliesTo()
}
@interface SomeAnnotation5 {}

@interface SomeAnnotations6 {
    SomeAnnotation[] value()
}
enum DayOfWeek { mon, tue, wed, thu, fri, sat, sun }
@interface Scheduled {
    DayOfWeek dayOfWeek()
}

//Annotation placement
@SomeAnnotation
class SomeClass {}

@SomeAnnotation String var

import java.lang.annotation.ElementType
import java.lang.annotation.Target

@Target([ElementType.METHOD, ElementType.TYPE])
@interface SomeAnnotation7 {}

//Annotation member values
@interface Page {
    String value()
    int statusCode() default 200
}

@Page(value='/home' , statusCode=404)
void home() {
    // ...
}

println 'end'