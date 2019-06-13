package org.chance

/**
  * Created by Administrator on 2016/9/26.
  */
class Match {

  /**
    * Scala 模式匹配
    * Scala 提供了强大的模式匹配机制，应用也非常广泛。
    * 一个模式匹配包含了一系列备选项，每个都开始于关键字 case。每个备选项都包含了一个模式及一到多个表达式。箭头符号 => 隔开了模式和表达式。
    */

  def matchTest(x:Int):String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }

  println(matchTest(3))

  //match 对应 Java 里的 switch，但是写在选择器表达式之后。即： 选择器 match {备选项}
  //match 表达式通过以代码编写的先后次序尝试每个模式来完成计算，只要发现有一个匹配的case，剩下的case不会继续匹配。
  //接下来我们来看一个不同数据类型的模式匹配：

  def match1(x:Any):Any = x match {
    case 1 => "one"
    case "two" => 2
    case y:Int => "scala.Int"
    case _ => "many"
  }

  match1("two")
  match1("test")
  match1(1)
  match1(6)


  // 使用样例类
  // 使用了case关键字的类定义就是就是样例类(case classes)，样例类是种特殊的类，经过优化以用于模式匹配。
  case class Person(name:String,age:Int)

  val alice = new Person("Alice",25)
  val bob = new Person("bob",32)
  val charlie = new Person("charlie",33)

  for (person <- List(alice,bob,charlie)){
    person match {
      case Person("Alice",25) => println("Hi Alice!")
      case Person("Bob", 32) => println("Hi Bob!")
      case Person(name, age) =>
        println("Age: " + age + " year, name: " + name + "?")
    }
  }


  //在声明样例类时，下面的过程自动发生了：
//  构造器的每个参数都成为val，除非显式被声明为var，但是并不推荐这么做；
//  在伴生对象中提供了apply方法，所以可以不使用new关键字就可构建对象；
//  提供unapply方法使模式匹配可以工作；
//  生成toString、equals、hashCode和copy方法，除非显示给出这些方法的定义。


  /**
    * Scala 正则表达式
    *
    */

  var pattern = "Scala".r
  var str = "Scala is Scalable and cool"
  println(pattern findFirstIn str)

  // 实例中使用 String 类的 r() 方法构造了一个Regex对象。
  // 然后使用 findFirstIn 方法找到首个匹配项。
  // 如果需要查看所有的匹配项可以使用 findAllIn 方法。
  // 你可以使用 mkString( ) 方法来连接正则表达式匹配结果的字符串，并可以使用管道(|)来设置不同的模式：

  import scala.util.matching.Regex

  val pattern1 = new Regex("(S|s)cala")
  val str1 ="Scala is scalable and cool"
  println((pattern1 findAllIn str).mkString(","))


  // 使用 replaceFirstIn( ) 方法来替换第一个匹配项，使用 replaceAllIn( ) 方法替换所有匹配项，
  pattern = "(S|s)cala".r
  pattern replaceFirstIn(str,"Java")

  pattern = new Regex("abl[ae]\\d+")
  str = "ablaw is able1 and cool"
  (pattern findAllIn str).mkString(",")



}
