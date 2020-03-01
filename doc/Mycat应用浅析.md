# Mycat
基于阿里`cobar`基础上进行二次开发。
- 解决客户端（譬如java应用）与数据库（不限于mysql）之间的紧耦合。
- 便于做数据的读写分离，缓解数据库压力
- 便于做数据库分库分表

类比`nginx`映射应用主机，`mycat`映射数据库节点。

## 能干什么？

- 主从复制（双主双从）
- 数据分片
  - 垂直拆分（分库）
  - 水平拆分（分表）
  - 垂直+水平拆分（分库分表）
- 多数据源整合 （集群、关系型数据库[mysql]、nosql）

## 原理
“拦截”：Mycat拦截了客户端发送过来的SQL，对其进行了特定的分析：分片分析、路由分析、读写分离分析、缓存分析等；然后将此SQL发往后端真实数据库，并将返回的结果做适当的处理，最终再返回给客户端。

## INSTALL in mac
```
get Mycat-server-1.6.7.4-release-20200105164103-mac.tar.gz
tar -zxvf Mycat-server-1.6.7.4-release-20200105164103-mac.tar.gz
cp -r Mycat /usr/local/

./mycat start
```
### 配置文件 （./conf/）
- schema.xml 定义逻辑库、表、分片节点等内容
- rule.xml 定义分片规则
- server.xml 定义用户以及系统相关变量


## 登录

- 登录运维窗口
  mysql -umycat -p123456 -P 9066 -h 127.0.0.1
- 登录数据窗口
  mysql -umycat -p123456 -P 8066 -h 127.0.0.1

## 搭建读写分离

### 搭建mysql数据库主从复制
GRANT REPLICATION SLAVE ON *.* TO 'slave@%' IDENTIFIED BY '123456';
以docker的方式在本地安装两个mysql实例，端口分别为：3007（主），3008（从）。
mysql_3007's docker-compose.yml:
```
version: '2'
services:
  mysql-local:
    image: mysql:5.6                
    ports:
    - "3307:3306"
    restart: always
    volumes:
    - "./db:/var/lib/mysql"
    - "./my.cnf:/etc/my.cnf"   
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      TZ:                  Asia/Shanghai
```
mysql_3308's docker-compose.yml
```
version: '2'
services:
  mysql-local:
    image: mysql:5.6                
    ports:
    - "3308:3306"
    restart: always
    volumes:
    - "./db:/var/lib/mysql"
    - "./my.cnf:/etc/my.cnf"   
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      TZ:                  Asia/Shanghai
```
mysql-3007's my.cnf 注意：需将下面代码贴至[mysqld]标签内
```
# 主服务器唯一ID
server-id=1
# 启用二进制日志
log-bin=mysql-bin
# 设置不需要复制的数据库（可设置多个）
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
# 设置需要复制的数据库
binlog-do-db=testdb
binlog-format=STATEMENT
```
mysql-3008's my.cnf 注意：需将下面代码贴至[mysqld]标签内
```
server-id=2
# 启用中继日志
relay-log=mysql-relay
```
分别启动两个mysql实例，然后在主节点上建立账户并授权slave。
执行`docker exec -it mysql_local_3007 bash`以命令行方式进入主节点。
```
mysql -uroot -p123456
mysql> GRANT REPLICATION SLAVE ON *.* TO 'slave@%' IDENTIFIED BY '123456';
```
查询master的状态，列表第一列为binlog的名字。
```
mysql> show master status;
+------------------+----------+--------------+--------------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB         | Executed_Gtid_Set |
+------------------+----------+--------------+--------------------------+-------------------+
| mysql-bin.000001 |      321 | testdb       | mysql,information_schema |                   |
+------------------+----------+--------------+--------------------------+-------------------+
1 row in set (0.00 sec)
```

### 垂直拆分（分库）
根据业务将需要join等操作的一系列表归为一个库。微服务的方式就是类似垂直拆分。每个库的每张表包换所有的数据。
### 水平拆分（分表）
相对于垂直拆分，水平拆分不是将表做分类，而是按照某个字段的某种规则分散到`多个数据库`中，`每张表`都不包含所有的数据。
譬如订单表，可以根据customer_id 取模进行分片。
- ER表  主表和关联表的映射配置
- 全局表

#### 操作步骤
- 指定分表计划
- 准备mysql节点环境（空白）
- 搭建mycat，启动
- 执行建库建表操作
- 执行插入数据语句

#### 常用分片规则
- 取模
- 分片枚举，譬如业务根据省份来做保存
- 范围约定

### 全局序列
- 本地文件，不推荐
- 数据库方式 推荐 （server.xml ）
- 时间戳方式
- 自主生成全局序列

#### CONF
- wrapper.conf 
  - wrapper.java.additional.9=-Xmx4G
  - wrapper.java.additional.10=-Xms1G
- server.xml
  - serverPort 8066
  - managePort 9066
  - sequenceHandlerType 
    - 0 本地文件方式
      - sequence_conf.properties
    - 1 数据库方式
      - sequence_db_conf.properties
    - 2 时间戳方式
      - sequence_time_conf.properties
  - user setting
    - property schemas
      - vip_admin,vip_order,vip_payment//定义用户权限内的逻辑库
    - property readOnly 默认false
      - false //如果对上述逻辑库只读，需要更改为false
- schema.xml
  - schema
    - 逻辑数据库 映射 真实物理数据定义
      - navicat连接mycat(用server.xml中定义的user连接)，可见的就是配置的逻辑库
        - 用navicat连接后，看到的表无法双击打开，只能通过查询语句查看数据；
    - primaryKey
      - rule 分片规则，具体规则在rule.xml中定义
    - dataNode
      - dn1,dn2,dn3
    - rule
      - mode-long
  - dataNode
    - dataHost
    - database
      - 真实数据库
  - dataHost
    - connection pool
    - readonly
      - 设置balance (0,1,2,3)
        - 0 不开启读写分离
        - 1 所有的读随机分配到（readnode or non-writenode）上，读写推荐配置此项=1
        - 2 所有的读随机分配到所有（r/w）节点
        - 3 
    - writeonly 
- rule.xml 定义分片规则
  - tableRule
    - algorithm 算法规则，具体规则在function模块定义
  - function
    - name
    - class="com.boatfly.codehub.xxx"" //实现某个分片算法的实现类
      - property name="count" //分片的数量
      
#### 常见问题
- 修改mycat配置文件，需要重启，如何避免？
  - mycat高可用集群
  - zk自动发现配置信息
- 将原有连mysql系统升级到mycat
  - 只需在connection配置的地方直接连mycat中的逻辑库即可
  - 主键生成策略需要使用分布式策略
    - 更新插入语句id策略
      - insert into order(id,....) values(next value for MYCATSEQ_GLOBAL,....)
      - MYCATSEQ_ 对应id生成策略文件
        - sequence_conf.properties (本地文件方式，不推荐，见全局系列 部分说明)

## 高可用
HAProxy+Keepalived配合两台Mycat搭建Mycat集群。

## 安全
- 权限配置
- SQL拦截

## 监控
- mycat-web 可视化运维的管理和监控平台
