package org.chance

import java.util.Date

/**
  * Created by Administrator on 2016/9/23.
  */
class Function {

  /**
    * Scala 函数
    * Scala 有函数和方法，二者在语义上的区别很小。
    * Scala 方法是类的一部分，而函数是一个对象可以赋值给一个变量。换句话来说在类中定义的函数即是方法。
    *
    * def f1([参数列表]) : [return type]
    * 如果你不写等于号和方法主体，那么方法会被隐式声明为"抽象(abstract)"，包含它的类型于是也是一个抽象类型。
    * def f2([参数列表]) : [return type] = {body;return [expr]}
    *
    * 函数调用 f1(参数列表)
    * [instance].fname(参数列表)
    *
    */

  object add {
    def addInt(a: Int, b: Int): Int = {
      var sum: Int = 0
      sum = a + b
      return sum
    }
  }

  object Hello {
    def printMe(): Unit = {
      println("Hello, Scala!")
    }
  }

  object Test2 {

    def addInt(a: Int, b: Int): Int = {
      var sum: Int = 0
      sum = a + b
      return sum
    }

    def main(args: Array[String]): Unit = {
      println(addInt(5, 7))
    }
  }

  /**
    * 传值调用（call-by-value）：先计算参数表达式的值，再应用到函数内部;
    * 传名调用（call-by-name）：将未计算的参数表达式直接应用到函数内部;  => 符号来设置传名调用
    */
  object Test{
    def main(args: Array[String]): Unit = {

      //函数传名调用(Call-by-Name)
      delayed(time())

      // 指定函数参数名
      printInt(b=5,a=7)

      //Scala 函数 - 可变参数
      printStrings("Runoob", "Scala", "Python");

      //Scala 递归函数
      print(factorial(5))

      //Scala 函数 - 默认参数值
      print(addInt())




    }
    def time() = {
      println("获取时间,单位为纳秒")
      System.nanoTime()
    }

    def delayed(t: => Long) = {
      println("xxxx")
      println("xx"+t)
      t
    }

    def printInt(a:Int, b:Int) = {
        println(a)
        println(b)
    }

    // 变参函数
    def printStrings(args:String*) = {
      var i:Int = 0;
      for(arg<-args){
        println(arg)
        i+=i;
      }
    }

    //递归函数
    def factorial(n:BigInt):BigInt = {
      if(n<=1) 1
      else n*factorial(n-1)
    }

    //函数默认值
    def addInt(a:Int=5, b:Int=7):Int = {
      var sum:Int = 0;
      sum = a+b
      return sum
    }

    // Scala 高阶函数
    // 高阶函数（Higher-Order Function）就是操作其他函数的函数。
    //Scala 中允许使用高阶函数, 高阶函数可以使用其他函数作为参数，或者使用函数作为输出结果。
    def apply(f:Int=>String, v:Int) = f(v)
    def layout[A](x:A) = "[" + x.toString + "]"
    println(apply(layout,10))

    //Scala 函数嵌套
    def factorial(i:Int) :Int = {
      def fact(i:Int, accumulator: Int):Int = {
        if(i<=1) accumulator
        else fact(i-1,i*accumulator)
      }
      fact(i,1)
    }
    println(factorial(0))
    println(factorial(1))
    println(factorial(2))
    println(factorial(3))


    // Scala 匿名函数
    var inc = (x:Int) => x+1
    def add2 = new Function1[Int,Int]{
      def apply(x:Int):Int = x+1;
    }
    var x = inc(7)-1
    var mul = (x:Int,y:Int) => x*y
    print(mul(3,4))

    var userDir = ()=>{System.getProperty("user.dir")}


    // Scala 偏应用函数
    def log(date:Date,message:String) = {
      println(date + "----" +message)
    }

    val date = new Date
    log(date,"m1")
    val logWithDateBound = log(date, _:String)
    logWithDateBound("m2")
    logWithDateBound("m3")


    // Scala 函数柯里化(Currying)
    // 柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
    // 新的函数返回一个以原有第二个参数为参数的函数。
    def add(x:Int,y:Int):Int = x+y
    add(1,2)
    def addd(x:Int)(y:Int) = x+y
    addd(1)(2)

    def add3(x:Int) = ( (y:Int) => x+y )

  }

  // Scala 闭包
  // 闭包是一个函数，返回值依赖于声明在函数外部的一个或多个变量。
  // 闭包通常来讲可以简单的认为是可以访问一个函数里面局部变量的另外一个函数。
  var factor = 3
  val multiplier1 = (i:Int) => i*10
  // 这样定义的函数变量 multiplier 成为一个"闭包"，因为它引用到函数外面定义的变量，
  // 定义这个函数的过程是将这个自由变量捕获而构成一个封闭的函数。
  val multiplier2 = (i:Int) => i*factor


}