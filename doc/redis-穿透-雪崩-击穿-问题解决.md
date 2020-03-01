## 如何应对Redis在缓存应用中的穿透、击穿以及雪崩问题？

### Redis在做缓存时，通常在代码逻辑上会做如下处理：

1. 查询缓存
2. 缓存有，则返回；缓存没有则查询数据库；
3. 查询数据库，返回；并放入缓存

### 问题
#### 问题1: 如果缓存没有，数据库也没有，此为缓存穿透。

譬如获取用户信息，当请求携带一个并不存在的id时（譬如：被攻击），就会将请求集中落在访问数据库上。
解决方法：
在缓存和数据库都无法获取对应信息时，将返回的空对象放入缓存，并设置一个合理的过期时间（譬如30秒等）。

#### 问题2: 高并发访问，导致还是有部分的查询接触到了数据库层面，此为缓存击穿。

解决方法：
在`2`步从数据库获取信息代码块上包上`同步锁 synchronized `即可。

#### 当某个时刻由于某种原因，缓存集体时效了，导致缓存雪崩。
解决方法：
1.粗暴，设置永不过期；弊端在于需要编写`定时任务`跑批。
2.对不同类别不同场景的对象设置`离散的`过期时间。

`如下代码供参考：`
```
public String getNameByUserId(@RequestParam(value = "userId") Integer userId){
    String name = redisServiceClient.get("marvel:user:"+userId);
    //双重检测
    if(name==null){
        name = redisServiceClient.get("marvel:user:"+userId);
        //同步锁 //防止击穿
        synchronized (this){
            TUser user = userRepository.getOne(userId);
            if(user==null){
                redisServiceClient.set("marvel:user:"+userId,null,30);//防止穿透
            }
            redisServiceClient.set("marvel:user:"+userId,name,60*60*24);//24小时过期
        }
    }
    return name;
}
```
