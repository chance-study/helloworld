package org.chance

import java.io.FileNotFoundException

/**
  * Created by Administrator on 2016/9/26.
  */
class Exception {

  /**
    * Scala 异常处理
    * Scala 的异常处理和其它语言比如 Java 类似。
    * Scala 的方法可以通过抛出异常的方法的方式来终止相关代码的运行，不必通过返回值。
    *
    */

  // 抛出异常
  throw new IllegalArgumentException


  import java.io.FileReader
  import java.io.FileNotFoundException
  import java.io.IOException

  // 捕获异常
  try{
    val f=new FileReader("input.txt")
  }catch{
    case ex:FileNotFoundException =>{
      println("missing file exception")
    }
    case ex:IOException =>{

    }

    // finally 语句
  } finally {
    println("finally")
  }

  /**
    * Scala 提取器(Extractor)
    * 提取器是从传递给它的对象中提取出构造该对象的参数。
    * Scala 标准库包含了一些预定义的提取器，我们会大致的了解一下它们。
    * Scala 提取器是一个带有unapply方法的对象。unapply方法算是apply方法的反向操作：unapply接受一个对象，然后从对象中提取值，提取的值通常是用来构造该对象的值。
    */

  //  注入方法 (可选)
  def apply(user:String,domain:String) = {
    user +"@" + domain
  }

  // 提取方法（必选）
  def unapply(str:String):Option[(String,String)] = {
    val parts = str split "@"
    if(parts.length == 2){
      Some(parts(0),parts(1))
    }else{
      None
    }
  }

  apply("zara","gmail.com")
  unapply("zara@gmail.com")
  unapply("zara ali")

  // 提取器使用模式匹配
//  在我们实例化一个类的时，可以带上0个或者多个的参数，编译器在实例化的时会调用 apply 方法。我们可以在类和对象中都定义 apply 方法。
//  就像我们之前提到过的，unapply 用于提取我们指定查找的值，它与 apply 的操作相反。 当我们在提取器对象中使用 match 语句是，unapply 将自动执行，如下所示：



  object Test{

    def main(args: Array[String]): Unit = {
      val x = Test(5)
      println(x)

      x match
      {
        case Test(num) => println(x + "is" + num +"2")
        case _ => println("no")
      }


    }

    def apply(x:Int) = x*2
    def unapply(z:Int):Option[Int] =
      if(z%2==0) Some(z/2) else None

  }

}
