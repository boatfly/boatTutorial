# Proxy 代理模式应用浅析

为一个对象提供一个替身，以控制对这个对象的访问。即通过代理对象访问目标对象，好处：可以在目标对象的基础上，增强额外的功能操作，即扩展目标对象的功能。

被代理对象：
- 远程对象
  - RPC
- 创建开销大的对象
- 需要安全控制的对象

三种模式：
- 静态代理
- 动态代理 （JDK代理，接口代理）
- Cglib代理
  - 可以在内存中动态的创建对象，而不需要实现接口
  - 也属动态代理的范畴


`static(均要实现接口) -> jdk dynamic proxy(只目标对象实现接口) -> 子类代理(不要求实现接口)`
  

## 静态代理
- code:com.boatfly.designpattern.proxy.statics
## 动态代理
- 代理对象不需要实现接口
- 代理对象的生成，是利用JDK的api，动态的在内存中构建

```java
@CallerSensitive
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,//目标类实现的接口
                                          InvocationHandler h)
```
- code:com.boatfly.designpattern.proxy.dynamic.jdk

## Cglib
Cglib是一个强大的高性能代码生成包，它可以在运行期扩展java类与实现类java接口，它广泛的被许多AOP的框架使用，实现方法拦截。
- 目标对象需要实现接口 -> jdk动态代理
- 目标对象不需要实现接口 -> 用Cglib代理
- Cglib的底层是通过使用`字节码处理框架ASM`来转换字节码并生成新的类。

细节：
- 代理的类不能为final
- 代理类的方法不能为static、final，否则不会被拦截

![Cglib实现代理](./cglib/cglib.png)



