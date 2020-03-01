# 深度剖析scala中的隐式转换

对于scala的学习成本，大多会体现在对于`隐式转换`的理解应用上。隐式转换伴随着泛型，scala所有的神秘之处、优雅之处皆因于此。

隐式在scala中，应用场景会在如下四个方面：
- 隐式参数
``` scala
scala> def razor(implicit name:String) = name
razor: (implicit name: String)String
```
直接调用razor方法
``` scala
scala> razor
<console>:13: error: could not find implicit value for parameter name: String
       razor
       ^
```
报错！编译器说无法为参数name找到一个隐式值
定义一个隐式值后再调用razor方法
``` scala
scala> implicit val iname = "Philips"
iname: String = Philips

scala> razor
res12: String = Philips
```
因为将iname变量标记为implicit，所以编译器会在方法省略隐式参数的情况下去搜索作用域内的隐式值作为缺少参数。
但是如果此时你又重复定义一个隐式变量，再次调用方法时就会报错
``` scala
scala> implicit val iname2 = "Gillette"
iname2: String = Gillette

scala> razor
<console>:15: error: ambiguous implicit values:
 both value iname of type => String
 and value iname2 of type => String
 match expected type String
       razor
       ^
```
匹配失败，所以隐式转换必须满足`无歧义规则`，在声明隐式参数的类型是最好使用特别的或自定义的数据类型，不要使用Int,String这些常用类型，避免碰巧匹配。
- 隐式函数
``` scala
class Person(val name:String)

class Engineer(val name:String,val salary:Double){
  def mySalary(): Unit ={
     println(name+",your monthly salary is is:"+salary)
  }
}

object Test {
  def main(args: Array[String]): Unit = {
    new Person("sutong").mySalary
  }
}
```
实例化一个person对象，然后调用mySalary方法时，此时编译是通不过的，因为person类没有mySalary方法。但是如果我们加入一个隐式函数就能时编译通过并能顺利运行，如下：
``` scala
object Test {
  implicit def person2Engineer(p:Person):Engineer = {
    new Engineer(p.name,100000)
  }
  def main(args: Array[String]): Unit = {
    new Person("sutong").mySalary
  }
}
```
输出：
``` scala
sutong,your salary is:100000.0
```
Scala会在报错前，去找隐式函数，例如我们定义的隐式函数，根据函数签名，主要是输入参数，找到了person2Engineer方法，并利用此方法将person对象转换为了Engineer对象，然后发现Engineer对象有mySalary方法，直接调用其mySalary方法，打印输出。运行后，Person对象和Engineer类型无任何关系。
**约定：**
>虽然在定义implicit函数时scala并未要求要写明返回类型，但我们应写明返回类型，这样有助于代码阅读。

- 隐式对象
用implicit修饰的object就是隐式对象。
``` scala
abstract class Template[T]{
  def add(a:T,b:T):T
}

abstract class SubTemplate[T] extends Template[T] {
  def unit:T
}

object Implicit_Object_Test {
  def main(args: Array[String]): Unit = {
    implicit object StringAdd extends SubTemplate[String]{
      override def unit: String = ""
      override def add(a:String,b:String):String = a concat b
    }
    implicit object IntAdd extends SubTemplate[Int]{
      override def unit: Int = 0
      override def add(a:Int,b:Int):Int = a+b
    }

    def sum[T](x:List[T])(implicit m:SubTemplate[T]):T = {
      if(x.isEmpty) m.unit
      else m.add(x.head,sum(x.tail))
    }

    println(sum(List(2,4,6,8,10)))
    println(sum(List("a","b","c")))
  }
}
输出：
30
abc
```
首先定义了两个含有泛型的抽象类，Template是父类，而SubTempalte是子类，而这个子类有有两个子对象StringAdd和IntAdd，下面来看下主函数中定义的sum函数，它是一个含有泛型的函数，它的第一个参数是含有泛型的List类型的xs，第二个参数是含有泛型的SubTemplate类型的隐式参数m，函数返回值是一个泛型，首先，函数里先判断传入的第一个参数是否为空，若为空则调用隐式参数m，有由于scala可以自动进行类型推到，所以运行时，泛型T是一个确定类型，要么为Int要么为String，但是为空时，我们调用隐式参数m的unit是不同的，Int为0，而String为“”，所以我们定义了两个隐式对象对其进行处理，IntAdd隐式类复写了unit，使之为0，StringAdd隐式类复写了unit，使之为””,这样程序就可以正常执行了。同理，隐式对象方法对add方法进行复写，完成了sum操作。
- 隐式类
在scala2.10后提供了隐式类，可以使用implicit声明类，但是需要注意以下几点：
1.其所带的构造参数有且只能有一个
2.隐式类必须被定义在类，伴生对象和包对象里
3.隐式类不能是case class（case class在定义会自动生成伴生对象与2矛盾）
4.作用域内不能有与之相同名称的标示符

``` scala
import scala.io.Source
import java.io.File

object Implicit_Class {
  import Context_Helper._
  def main(args: Array[String]) {
    println(1.add(2))  
    println(new File("I:\\aa.txt").read())
  }
}

object Context_Helper{
  implicit class ImpInt(tmp:Int){
    def add(tmp2: Int) = tmp + tmp2
  }
  implicit class FileEnhance(file:File){
    def read() = Source.fromFile(file.getPath).mkString
  }
}
```
其中`1.add(2)`这个可以这样理解：
>当 1.add（2） 时，scala 编译器不会立马报错，而检查当前作用域有没有 用implicit 修饰的，同时可以将Int作为参数的构造器，并且具有方法add的类，经过查找 发现 ImpInt 符合要求，利用隐式类 ImpInt 执行add 方法。

## 归纳一下
- 隐式转换的时机
a.当方法中的参数的类型与目标类型不一致时
b.当对象调用类中不存在的方法或成员时，编译器会自动将对象进行隐式转换
- 隐式解析机制
a.首先会在当前代码作用域下查找隐式实体（隐式方法  隐式类 隐式对象）
b.如果第一条规则查找隐式实体失败，会继续在隐式参数的类型的作用域里查找
- 隐式转换的前提
a.不存在二义性
b.隐式操作不能嵌套使用
c.代码能够在不使用隐式转换的前提下能编译通过，就不会进行隐式转换
