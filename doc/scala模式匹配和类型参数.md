## scala模式匹配和类型参数

#### 模式匹配
关于scala的`模式匹配`在上篇博客[《从java(python)到scala的n种记忆》](https://www.jianshu.com/p/0cd280dbddef)中有提及，但是模式匹配在scala中应用的地方太多了，有必要单独攒一篇文字来记录它。

模式匹配跟java中的switch-case绝对的相似，只是java中只能匹配值，而scala中的这个不仅能匹配值，而且可以匹配类型和类。

- 按值匹配
``` scala
def bigData(content:String):Unit = {
    content match{
        case "spark" => println("yeah!spark!")
        case "kafka" => println("wow!kafka!")
        case _ => println("anything else?")
    }
  }
def main(args: Array[String]) {
    bigData("spark")
    bigData("kafka")
    bigData("zipkin")
  }
```
输出：
``` scala
yeah!spark!
wow!kafka!
anything else?
```
case 后面还可以加**条件判断**，不过貌似不会很常用。
- 类型模式匹配
``` scala
object Demo {
   def main(args: Array[String]) {
      println(matchTest("two"))
      println(matchTest("test"))
      println(matchTest(1))
   }

   def matchTest(x: Any): Any = x match {
      case 1 => "one"
      case "two" => 2
      case y: Int => "scala.Int"
      case _ => "many"
   }
}
```
输出：
``` scala
2
many
one
```

- 样例类(case class)模式匹配
Scala `Case`类只是常规类，默认情况下是`不可变`的，可通过模式匹配可`分解`。它使用相等(equal)方法在结构上比较实例。它不使用new关键字实例化对象。默认情况下，case类中列出的所有参数默认使用public和immutable修辞符。
语法：
``` scala
case class className(parameters)
```
``` scala
case class CaseClass(a:Int, b:Int)  

object Demo{  
    def main(args:Array[String]){  
        var c =  CaseClass(13,14)       // Creating object of case class  
        println("a = "+c.a)               // Accessing elements of case class  
        println("b = "+c.b)  
    }  
}
```
输出：
``` scala
a = 13
b = 14
```
Case类支持模式匹配。 所以，可以在模式中使用它。没有参数的case类将被声明为case对象而不是case类。 默认情况下，case对象是`可序列化`的。
``` scala
trait SuperTrait  
case class CaseClass1(a:Int,b:Int) extends SuperTrait  
case class CaseClass2(a:Int) extends SuperTrait         // Case class  
case object CaseObject extends SuperTrait               // Case object  
object Demo{  
    def main(args:Array[String]){  
        callCase(CaseClass1(10,10))  
        callCase(CaseClass2(10))  
        callCase(CaseObject)  
    }  
    def callCase(f:SuperTrait) = f match{  
        case CaseClass1(f,g)=>println("a = "+f+" b ="+g)  
        case CaseClass2(f)=>println("a = "+f)  
        case CaseObject=>println("No Argument")  
    }  
}
```
输出：
``` scala
a = 10 b =10
a = 10
No Argument
```
#### 类型参数
Scala中的类型参数，与java中的泛型类似。
``` scala
class Person[T](val content:T){
  def getContent(id:T)=id+"_"+content
}
……
def main(args: Array[String]) {
    val person_Str = new Person[String]("String")
    println(person_Str.getContent("spark"))
    val person_Int = new Person[Int](14)
    println(person_Int.getContent(13))
  }
```
输出：
``` scala
spark_String
13_14
```
所以，我们使用类型参数，可以很好的指定特定值得输入类型，然后基于该类型进行一些操作，增加了程序的健壮性。Spark中的RDD就是这样的。
上面只是一个简单的例子，scala的泛型[T]有如下六种方式：
- scala的类和方法、函数都可以是泛型。
- 关于对类型边界的限定分为上边界和下边界（对类进行限制）
上边界：表达了泛型的类型必须是某种类型或者某种类的子类，语法为`<:`,例如T <: AnyVal表示泛型T的类型的最顶层类是AnyVal。
下边界：表达了泛型的类型必须是某种类型或者某种类的父类，语法为`>:`,例如T >: S表示泛型T的类型必须是S的超类，下界的作用主要是保证类型安全。
- "<%" :view bounds可以进行某种神秘的转换，把你的类型在没有知觉的情况下转换成目标类型
如果希望类型变量界定能跨越类继承层次结构时，可以使用视图界定来实现的，其后面的原理是通过隐式转换来实现。视图界定利用<%符号来实现，例如后面的例子[T <% Person]，T是Person继承的或者是能变成Person。
- "T:classTag":相当于动态类型，你使用时传入什么类型就是什么类型
- 逆变和协变：-T和+T
逆变和协变最容易理解的便是用List来理解。
协变定义形式如：trait List[+T] {} 。当类型S是类型A的子类型时，则List[S]也可以认为是List[A}的子类型，
即List[S]可以泛化为List[A]。
逆变定义形式如：trait List[-T] {}
当类型S是类型A的子类型，则Queue[A]反过来可以认为是Queue[S}的子类型。
- "T:Ordering" :表示将T变成Ordering[T],可以直接用其方法进行比大小,可完成排序等工作
