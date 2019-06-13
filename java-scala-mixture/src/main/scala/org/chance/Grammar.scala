package org.chance

/**
  * Created by GengChao on 2016/9/23.
  */
class Grammar {

  /**
  基本语法:def main(args: Array[String]) - Scala程序从main()方法开始处理，这是每一个Scala程序的强制程序入口部分。
   */

  /**
  标识符:字符数字和符号
  "$"开头的标识符为保留的 Scala 编译器产生的标志符使用
  符号标志符包含一个或多个符号:+ ++ ::: < ?> :->
  字面量标志符为使``定义的字符串，比如 `x` `yield`
   */

  /**
  定义包: package com.runnoob
  引用: import java.awt.Color
       import java.awt._
       import java.awt.{Color,Font} 选择器selector
       //重命名成员
       import java.util.{HashMap => JavaHashMap}
      //隐藏成员
      import java.util.{HashMap => _, _} // 引入了util包的所有成员，但是HashMap被隐藏了
   */

  /**
    * 数据类型：Byte，Short，Int，Long，Float，Double，Char，String，Boolean
    * Unit 表示无值，和其他语言中void等同。用作不返回任何结果的方法的结果类型。Unit只有一个实例值，写成()。
    * Null null 或空引用
    * Nothing Nothing类型在Scala的类层级的最低端；它是任何其他类型的子类型。
    * Any Any是所有其他类的超类
    * AnyRef AnyRef类是Scala里所有引用类(reference class)的基类
    *
    */

  /**
    * 字面量：
    * 整型字面量用于 Int 类型，如果表示 Long，可以在数字后面添加 L 或者小写 l 作为后缀。
    * 如果浮点数后面有f或者F后缀时，表示这是一个Float类型，否则就是一个Double类型的
    * 布尔型字面量有 true 和 false
    * 符号字面量：'<标识符>  'x 是 scala.Symbol("x") 的简写
    * 字符字面量 ‘a’
    * 字符串字面量 " Hello, \n World! "
    * 多行字符串的表示方法 """ ... """
    * Null scala.Null
    * Nothing scala.Nothing
    * 转义 \b \r \n \f \r \" \' \\
    */

  /**
    * Scala 变量
    * var 变量 var myVar:String = "Foo"
    * val 常量 val myVal:String = "Too"
    * 元组 (v1:Int, v2: String) = Pair(40,"Foo")
    */

  var v3, v1:String = "v"
  val v2=2
  var (v4:Int, v5: String) = Pair(40,"Foo")
  val (v6, v7) = Pair(40,"Foo")

  /**
    * Scala 访问修饰符 public,private,protected
    * Scala 中的 private 限定符，比Java更严格，在嵌套类情况下，外层类甚至不能访问被嵌套类的私有成员
    *
    * 作用域保护 private[x] protected[x] - x指代某个所属的包、类或单例对象。
    * 如果写成private[x],读作"这个成员除了对[…]中的类或[…]中的包中的类及它们的伴生对像可见外，对其它所有类都是private。
    *
    *
    */
  class Outer{
    class Inner{
      private def f(){println("f")}
      protected def f1(){println("f1")}
      def f2(){println("f2")}
      class InnerMost{
        f()
      }
    }

    class Sub extends Inner{
      f1()
    }

    class Other{
//      (new Inner).f() //scala:error,java:can
    }

//    new Inner().f()  //scala:error,java:can

    new Inner().f2()  //public都可以访问
  }


  /**
    * Scala 运算符
    * 算术运算符 + - * / %
    * 关系运算符 == != > < >= <=
    * 逻辑运算符 && || !
    * 位运算符 ~ & | &#94;(^^)  << >> >>>
    * 赋值运算符 = += -= *= /= %= <<= >>= &= ^^(1)= |=
    * 运算符优先级
    */

  /**
    * 条件语句
    * if(){}
    * if(){}else{}
    * if(){}else if(){}else if(){}else{}
    * 循环语句
    * while(condition){statment(s);}
    * do{statment(s);}while(condition);
    * for(var x <- Range [; var y <- Range]){statments(s);} Range i to j i until j <-用于为变量x赋值。
    * 范围 ： to [i,j] until [i,j)
    * for(var x <- List){statments(s);}
    * for(var x <- List if contd1;if cond2...){statment(s);}
    * break; continue;
    */

  //
  var a =0;
  var b =0;
  for (a<- 1 to 3; b<- 1 to 3){
  }

  class Test1{
    def main(args: Array[String]): Unit = {
      var a = 0;
      val numList = List(1,2,3,4,5,6,7,8,9,10);
      // for 循环
      var retVal = for{ a<-numList if a!=3; if a<8 }yield a
      // 输出返回值
      for (a<-retVal){
        println(a);
      }
    }
  }


  //Scala 字符串
  //在 Scala 中，字符串的类型实际上是 Java String，它本身没有 String 类。
  //在 Scala 中，String 是一个不可变的对象，所以该对象不可被修改。
  //这就意味着你如果修改字符串就会产生一个新的字符串对象。
  val greeting:String = "Hello,World!"
  val buf = new StringBuilder
  buf +='a'
  buf ++="bacdef"


  // Scala 数组
  var z:Array[String] = new Array[String](3)
  var z1 = new Array[String](3)
  z(0) = "Runnoob"
  z(1) = "Baidu"
  z(4/2) = "Google"
  var z2 = Array("a","b","c")

  var myList = Array(1.9,2.9,3.4,3.5)
  for(x <- myList)
    println(x)

  var total = 0.0
  for(i<- 0 to myList.length-1){
    total+=myList(i);
  }

  var max = myList(0);
  for(i<- 1 to myList.length -1 ){
    if(myList(i) > max) max = myList(i)
  }
  println(max)

  // 多维数组
  import Array._
  var myMatrix = ofDim[Int](3,3)
  for(i<-0 to 2){
    for(j<-0 to 2){
      myMatrix(i)(j) = j
    }
  }

  for(i<-0 to 2){
    for(j<-0 to 2){
      print(" "+myMatrix(i)(j));
    }
    println();
  }

  var my1 = Array(1.9,2.9,3.4,3.5)
  var my2 = Array(8.9,7.9,0.4,1.5)
  var my3 = concat(my1,my2)

  for(x<-my3){
    println(x)
  }

  var my4 = range(10,20,2)
  var my5 = range(10,20)

  for(x<-my4){
    print(" "+x)
  }
  for(x<-my5){
    print(" " + x)
  }


  /**
    * Scala Collection
    * Scala提供了一套很好的集合实现，提供了一些集合类型的抽象。
    * Scala 集合分为可变的和不可变的集合。
    * 可变集合可以在适当的地方被更新或扩展。这意味着你可以修改，添加，移除一个集合的元素。
    * 而不可变集合类，相比之下，永远不会改变。不过，你仍然可以模拟添加，移除或更新操作。但是这些操作将在每一种情况下都返回一个新的集合，同时使原来的集合不发生改变。
    *
    * List 列表
    * Set 集合
    * Map 映射
    * 元组
    * Option  可选
    * Iterator 迭代器
    *
    */

  val v261 =List(1,2,3,4)
  var v262 = Set(1,2,3,4)
  val v263 = Map("one"->1,"two"->2,"three"->3)
  val v264 = (10,"Runnob")
  val v265:Option[Int] = Some(5)

  /**
    * List
    * Scala 列表类似于数组，它们所有元素的类型都相同，
    * 但是它们也有所不同：列表是不可变的，值一旦被定义了就不能改变，其次列表 具有递归的结构（也就是链接表结构）而数组不是。。
    * 列表的元素类型 T 可以写成 List[T]。例如，以下列出了多种类型的列表：
    */
  var site11: List[String] = List("xx", "x1", "x2")
  var nums :List[Int] = List(1,2,3,4)
  var empty:List[Nothing] = List()
  var dim: List[List[Int]] =
    List(
      List(1,0,0),
      List(0,1,0),
      List(0,0,1)
    )

  /**
    * 构造列表的两个基本单位是 Nil 和 ::
    * Nil 也可以表示为一个空列表。
    */

  site = "Runoob" :: ("Google"::("Baidu" :: Nil))
  nums = 1::(2::(3::(4::Nil)))
  empty = Nil
  dim = (1::(0::(0::Nil))) ::
    (0::(1::(0::Nil))) ::
    (0::(0::(1::Nil))) :: Nil

  /**
    * 列表基本操作
    */

    // head 返回列表第一个元素
    // tail  返回一个列表，包含除了第一元素之外的其他元素
    // imEmpty 在列表为空时返回true

  /**
    * 你可以使用 ::: 运算符或 List.:::() 方法
    * 或 List.concat() 方法来连接两个或多个列表。实例如下:
    *
    */

  val site1 = "Runoob" :: ("Google" :: ("Baidu"::Nil))
  val site2 = "Facebook" :: ("Taobao" :: Nil)

  var fruit = site1 ::: site2
  print(fruit)
  // Set.:::()
  fruit = site1.:::(site2)

  // 使用 concat 方法
  fruit = List.concat(site1,site2)
  print(fruit)

  // List.fill()
  var site = List.fill(3)("Runoob") // 重复 Runoob 3次
  print(site)
  val num = List.fill(10)(2) //重复元素 2, 10 次

  // List.tabulate()
  val seq = List.tabulate(6)(n => n*n)
  print(seq)

  val mul = List.tabulate(4,5)( _ * _ )
  println(mul)


  // List.reverse
  val site261 = "Runoob"::("Google"::("Baidu"::Nil))
  print(site)
  print(site.reverse)


  /**
    * Set
    * Scala Set(集合)是没有重复的对象集合，所有的元素都是唯一的。
    * Scala 集合分为可变的和不可变的集合。
    * 默认情况下，Scala 使用的是不可变集合，如果你想使用可变集合，需要引用 scala.collection.mutable.Set 包。
    * 默认引用 scala.collection.immutable.Set，不可变集合实例如下：
    */

  val set = Set(1,2,3)
  print(set.getClass.getName)
  print(set.exists(_ % 2 == 0))
  println(set.drop(1))

  import scala.collection.mutable.Set
  val mutableSet = Set(1,2,3)
  println(mutableSet.getClass.getName)
  mutableSet.add(4)
  mutableSet.remove(1)
  mutableSet += 5
  mutableSet -= 2
  println(mutableSet)

  val another = mutableSet.toSet
  println(another.getClass.getName)


  //集合基本操作
  //head
  //tail
  //isEmpty
  val setSite = Set("R","G","B")
  val numsSet:Set[Int] = Set()
  setSite.head
  setSite.tail
  setSite.isEmpty
  numsSet.isEmpty

  // 你可以使用 ++ 运算符或 Set.++() 方法来连接两个集合。
  // 如果元素有重复的就会移除重复的元素。实例如下：
  val set1 = Set("R","G","B")
  val set2 = Set("F","T")
  var set3 = set1 ++ set2
  set3 = set1.++(site2)
  println(set3)

  // Set.min / Set.max
  val set4 = Set(5,6,9,29,30,45)
  set4.min
  set4.max

  // 交集 Set.& Set.intersect
  val num1 = Set(5,6,9,20,30,45)
  val num2 = Set(50,60,9,20,35,55)

  // 交集
  println( "num1.&(num2) : " + num1.&(num2) )
  println( "num1.intersect(num2) : " + num1.intersect(num2) )

  /**
    * Scala Map(映射)
    * Map(映射)是一种可迭代的键值对（key/value）结构。
    * 所有的值都可以通过键来获取
    * Map 中的键都是唯一的。
    * Map 也叫哈希表（Hash tables）。
    * Map 有两种类型，可变与不可变，区别在于可变对象可以修改它，而不可变对象不可以。
    * 默认情况下 Scala 使用不可变 Map。如果你需要使用可变集合，你需要显式的引入 import scala.collection.mutable.Map 类
    * 在 Scala 中 你可以同时使用可变与不可变 Map，不可变的直接使用 Map，
    * 可变的使用 mutable.Map。以下实例演示了不可变 Map 的应用：
    */

  var A:Map[Char,Int] = Map()
  var colors = Map("red"-> "#FFOOOO", "azure"-> "#F0FFFF")
  A += ('I' -> 1)
  A += ('J' -> 5)
  A += ('K' -> 10)
  A += ('L' -> 100)

  // Map 基本操作
  // keys
  // values
  // isEmpty
  colors = Map("red" -> "#FF0000",
    "azure" -> "#F0FFFF",
    "peru" -> "#CD853F")
  val numsMap: Map[Int, Int] = Map()
  println( "colors 中的键为 : " + colors.keys )
  println( "colors 中的值为 : " + colors.values )
  println( "检测 colors 是否为空 : " + colors.isEmpty )
  println( "检测 nums 是否为空 : " + numsMap.isEmpty )

  // Map 合并
  // ++ 运算符或 Map.++() 方法来连接两个 Map，Map 合并时会移除重复的 key。
  val colors1 = Map("red" -> "#FF0000",
    "azure" -> "#F0FFFF",
    "peru" -> "#CD853F")
  val colors2 = Map("blue" -> "#0033FF",
    "yellow" -> "#FFFF00",
    "red" -> "#FF0000")

  //  ++ 作为运算符
  colors = colors1 ++ colors2
  println( "colors1 ++ colors2 : " + colors )

  //  ++ 作为方法
  colors = colors1.++(colors2)
  println( "colors1.++(colors2)) : " + colors )

  // 输出 Map 的 keys 和 values
  val sites = Map("runoob" -> "http://www.runoob.com",
    "baidu" -> "http://www.baidu.com",
    "taobao" -> "http://www.taobao.com")

  sites.keys.foreach{ i =>
    print( "Key = " + i )
    println(" Value = " + sites(i) )}

  // 使用 Map.contains 方法来查看 Map 中是否存在指定的 Key。
  if( sites.contains( "runoob" )){
    println("runoob 键存在，对应的值为 :"  + sites("runoob"))
  }else{
    println("runoob 键不存在")
  }

  /**
    * Scala 元组
    * 与列表一样，元组也是不可变的，但与列表不同的是元组可以包含不同类型的元素。
    * 元组的值是通过将单个的值包含在圆括号中构成的。例如：
    */
  val t = (1,3.14,"Fred")
  val t1 = new Tuple3(1, 3.14, "Fred")
  var t2 = (4,3,2,1)
  val sum = t2._1 + t2._2 + t2._3 + t2._4
  println( "元素之和为: "  + sum )
  t2.productIterator.foreach{i=>println("Value=" + i )}

  // Tuple.toString() 方法将元组的所有元素组合成一个字符串，
  t2.toString()

  // 使用 Tuple.swap 方法来交换元组的元素。

  val t3 = new Tuple2("www.google.com", "www.runoob.com")
  t3.swap

  /**
    * Scala Option(选项)
    * Scala Option(选项)类型用来表示一个值是可选的（有值或无值)。
    * Option[T] 是一个类型为 T 的可选值的容器：
    * 如果值存在， Option[T] 就是一个 Some[T] ，如果不存在， Option[T] 就是对象 None 。
    */
  val myMap:Map[String,String] = Map("key1"->"value")
  val value1:Option[String] = myMap.get("key1")
  val value2:Option[String] = myMap.get("key2")
  println(value1)  // Some("value1")
  println(value2)  // None

  // 通过模式匹配来输出匹配值
  val sites1 = Map("runoob" -> "www.runoob.com", "google" -> "www.google.com")
  println("show(sites.get( \"runoob\")) : " +
    show(sites1.get( "runoob")) )
  println("show(sites.get( \"baidu\")) : " +
    show(sites1.get( "baidu")) )

  def show(x:Option[String]) = x match {
    case Some(s) => s
    case None => "?"
  }

  //使用 getOrElse() 方法来获取元组中存在的元素或者使用其默认的值
  val a1:Option[Int] = Some(5)
  val b1:Option[Int] = None
  println(a1.getOrElse(0))
  println(b1.getOrElse(10))

  // isEmpty() 方法
  println(a1.isEmpty)
  println(b1.isEmpty)





  /**
    * Scala Iterator（迭代器）
    * 迭代器 it 的两个基本操作是 next 和 hasNext。
    * 调用 it.next() 会返回迭代器的下一个元素，并且更新迭代器的状态。
    * 调用 it.hasNext() 用于检测集合中是否还有元素。
    * API
    * it.max
    * it.min
    * it.size
    * it.length
    *
    */

  val it = Iterator("Baidu","Google","Runoob","Taobao")
  while(it.hasNext)
    println(it.next())

  //查找最大与最小元素 使用 it.min 和 it.max 方法从迭代器中查找最大与最小元素
  println(it.max)
  println(it.min)

  //获取迭代器的长度 使用 it.size 或 it.length 方法来查看迭代器中的元素个数
  println(it.size)
  println(it.length)




}
