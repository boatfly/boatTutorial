package com.boatfly.spark.examples.sql.udf

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

case class BookBean(id: Int, name: String, price: Int)

case class AvgBuf(var sum: Int, var count: Int)

//声明用户自定义的聚合函数
class MyAgeAvgClassFunction extends Aggregator[BookBean, AvgBuf, Double] {
  //初始化
  override def zero: AvgBuf = {
    AvgBuf(0, 0)
  }

  //聚合数据
  override def reduce(b: AvgBuf, a: BookBean): AvgBuf = {
    b.sum = b.sum + a.price
    b.count = b.count + 1
    b
  }

  //缓冲区合并
  override def merge(b1: AvgBuf, b2: AvgBuf): AvgBuf = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }

  //计算结果
  override def finish(reduction: AvgBuf): Double = {
    reduction.sum.toDouble / reduction.count
  }

  override def bufferEncoder: Encoder[AvgBuf] = Encoders.product

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
