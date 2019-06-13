package org.chance

import java.io._

import scala.io.Source

/**
  * Created by Administrator on 2016/9/26.
  */
class File1 {

  def main(args: Array[String]): Unit = {

  // Scala 进行文件写操作，直接用的都是 java中 的 I/O 类 （java.io.File)：
    val writer = new PrintWriter(new File("test.txt"))
    writer.write("aaa")
    writer.close()

    // 从屏幕上读取用户输入
//    var line = Console.readLine
    println("tks")


    // 从文件上读取内容
    //从文件读取内容非常简单。我们可以使用 Scala 的 Source 类及伴生对象来读取文件。以下实例演示了从 "test.txt"(之前已创建过) 文件中读取内容:
    Source.fromFile("test.txt").foreach(print)

  }

}
