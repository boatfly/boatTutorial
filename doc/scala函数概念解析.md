# Scala函数概念解析

scala作为支持函数式编程的语言，scala函数式编程是scala的重中之重，spark当中的计算都是用scala函数式编程来做，高级函数也是其独特的一个特性，并且spark基于集合，这样可以使scala发挥其对于集合计算的强大功能。首先，函数/变量同是一等公民，函数与变量同等地位，函数的定义可以单独定义，可以不依赖于类、接口或者object，而且独立存在，独立使用，并且可以赋值给变量。

#### 函数传名调用(Call-by-Name)、传值调用(Cal-by-Value)
Scala的解释器在解析函数参数(function arguments)时有两种方式：
- 传值调用（call-by-value）
先计算参数表达式的值(reduce the arguments)，再应用到函数内部；
- 传名调用（call-by-name）
将未计算的参数表达式直接应用到函数内部。
``` scala
package com.cgoshine.sh.demo  

object Add {  
  def addByName(a: Int, b: => Int) = a + b   
  def addByValue(a: Int, b: Int) = a + b   
}
```
addByName是传名调用，addByValue是传值调用。语法上可以看出，使用传名调用时，在参数名称和参数类型中间有一个`=>`符号。

觉得传名参数会很少用到，跟java的传参习惯看起就可以了。

#### 指定函数参数名
一般情况下函数调用参数，就按照函数定义时的参数顺序一个个传递。但是我们也可以通过指定函数参数名，并且不需要按照顺序向函数传递参数。
``` scala
object Test {
   def main(args: Array[String]) {
        printInt(b=5, a=7);
   }
   def printInt( a:Int, b:Int ) = {
      println("Value of a : " + a );
      println("Value of b : " + b );
   }
}
```
#### 可变参数
Scala 通过在参数的类型之后放一个`*`星号来设置可变参数(可重复的参数)。
``` scala
object Test {
   def main(args: Array[String]) {
        printStrings("Java", "Scala", "Python");
   }
   def printStrings( args:String* ) = {
      var i : Int = 0;
      for( arg <- args ){
         println("Arg value[" + i + "] = " + arg );
         i = i + 1;
      }
   }
}
```
#### 递归函数
``` scala
object Test {
   def main(args: Array[String]) {
      for (i <- 1 to 10)
         println(i + " 的阶乘为: = " + factorial(i) )
   }

   def factorial(n: BigInt): BigInt = {  
      if (n <= 1)
         1  
      else    
         n * factorial(n - 1)
   }
}
```
#### 默认参数值
Scala 可以为函数参数指定默认参数值，使用了默认参数，你在调用函数的过程中可以不需要传递参数，这时函数就会调用它的默认参数值，如果传递了参数，则传递值会取代默认值。
``` scala
object Test {
   def main(args: Array[String]) {
        println( "返回值 : " + addInt() );
   }
   def addInt( a:Int=5, b:Int=7 ) : Int = {
      var sum:Int = 0
      sum = a + b
      return sum
   }
}
```
#### 高阶函数
高阶函数（Higher-Order Function）就是操作其他函数的函数。
Scala 中允许使用高阶函数, 高阶函数可以使用其他函数作为参数，或者使用函数作为输出结果。
- 将定义的函数赋给某变量
``` scala
def func1(s:String):Unit = {
    println(s)
  }

val func2 = func1 _
```
> val变量名 = 函数名+空格+_

这里函数名后面必须要有空格，表明是函数的原型。
高阶函数是函数的参数也是函数。（因为函数的参数可以是变量，而函数又可以赋值给变量，即函数和变量地位一样，所以函数参数也可以是函数）。
``` scala
scala> val iGreeting = (content:String) => println(content)
iGreeting: String => Unit = $$Lambda$1052/5181771@257f30f7
scala> def sendGreeting(func:(String) => Unit,content:String){func(content)}
sendGreeting: (func: String => Unit, content: String)Unit
scala> sendGreeting(iGreeting,"scala")
scala
```
首先我们定义了一个函数sendGreeting,这个函数有两个参数，第一个参数是一个函数，函数名是func，他有一个String类型的参数并且返回值是unit空的；第二个参数是String类型的变量名为content的变量，函数体是将第二个参数作为第一个参数也就是函数func的参数，来调用第一个函数，整个函数返回值为unit空。这里只要传入的函数的格式与定义的一致就行。
``` scala
scala> val array = Array(1,2,3,4,5,6)
array: Array[Int] = Array(1, 2, 3, 4, 5, 6)

scala> array.map(item => item*2)
res2: Array[Int] = Array(2, 4, 6, 8, 10, 12)
```
Array.map()作用，他会遍历array中每一个元素，并将每个元素作为具体的值传给map中的作为参数的函数。

**高阶函数有个非常有用的特性是类型推断**。其可以自动推断出参数的类型，而且对于只有一个的参数的函数，可以省略掉小括号，并且在函数的参数作用的函数体内只是用一次函数的输入参数的值话，就可省略掉函数名，用下划线（_）代替。
``` scala
scala> val array = Array(1,2,3,4,5,6)
array: Array[Int] = Array(1, 2, 3, 4, 5, 6)

scala> array.map(_ * 2)
res3: Array[Int] = Array(2, 4, 6, 8, 10, 12)

scala> array.map(_ * 2).foreach(println(_))
2
4
6
8
10
12

scala> array.map(_ * 2).foreach(println _)
2
4
6
8
10
12

scala> array.map(_ * 2).foreach(println)
2
4
6
8
10
12

scala> array.map(_ * 2).filter(_ > 6).foreach(println)
8
10
12
```

#### 内嵌函数
我们可以在 Scala 函数内定义函数，定义在函数内的函数称之为局部函数。
``` scala
object Test {
   def main(args: Array[String]) {
      println( factorial(0) )
      println( factorial(1) )
      println( factorial(2) )
      println( factorial(3) )
   }

   def factorial(i: Int): Int = {
      def fact(i: Int, accumulator: Int): Int = {
         if (i <= 1)
            accumulator
         else
            fact(i - 1, i * accumulator)
      }
      fact(i, 1)
   }
}
```

#### 匿名函数
spark中大都用的是匿名函数(不为函数命名)，然后将其复制个一个变量。
匿名函数格式：
>Val 变量名 = （参数：类型） => 函数体

``` scala
val func1 = (s:String) => println(s)
func1("scala")
```
#### 偏应用函数
Scala 偏应用函数是一种表达式，你不需要提供函数需要的所有参数，只需要提供部分，或不提供所需参数。
``` scala
import java.util.Date

object Test {
   def main(args: Array[String]) {
      val date = new Date
      log(date, "message1" )
      Thread.sleep(1000)
      log(date, "message2" )
      Thread.sleep(1000)
      log(date, "message3" )
   }

   def log(date: Date, message: String)  = {
     println(date + "----" + message)
   }
}
```
输出结果：
``` scala
$ scalac Test.scala
$ scala Test
Tue May 22 15:53:39 CST 2018----message1
Tue May 22 15:53:39 CST 2018----message2
Tue May 22 15:53:39 CST 2018----message3
```
log() 方法接收两个参数：date 和 message。我们在程序执行时调用了三次，参数 date 值都相同，message 不同。
我们可以使用偏应用函数优化以上方法，绑定第一个 date 参数，第二个参数使用下划线(_)替换缺失的参数列表，并把这个新的函数值的索引的赋给变量。
``` scala
import java.util.Date

object Test {
   def main(args: Array[String]) {
      val date = new Date
      val logWithDateBound = log(date, _ : String)

      logWithDateBound("message1" )
      Thread.sleep(1000)
      logWithDateBound("message2" )
      Thread.sleep(1000)
      logWithDateBound("message3" )
   }

   def log(date: Date, message: String)  = {
     println(date + "----" + message)
   }
}
```
输出结果：
``` scala
$ scalac Test.scala
$ scala Test
Tue May 22 15:53:39 CST 2018----message1
Tue May 22 15:53:39 CST 2018----message2
Tue May 22 15:53:39 CST 2018----message3
```
#### 函数柯里化(Function Currying)
柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。新的函数返回一个以原有第二个参数为参数的函数。**其也利用了闭包的特性。**
定义一个函数：
``` scala
def add(x:Int,y:Int)=x+y
```
把这个函数变一下形：
``` scala
def add(x:Int)(y:Int) = x + y
```
这种方式（过程）就叫柯里化。
