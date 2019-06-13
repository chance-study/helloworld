package org.chance

/**
  * Created by Administrator on 2016/9/22.
  */
class Hello {

  def sayHello(x: String): Unit = {
    println("hello," + x)
  }

  /**
    * def main(args: Array[String]) -Scala程序的强制入口
    * 这是我的第一个 Scala 程序
    * 以下程序将输出'Hello World!'
    */
  def main(args: Array[String]) = {
    HelloWorld.sayHello("java")
  }

}
