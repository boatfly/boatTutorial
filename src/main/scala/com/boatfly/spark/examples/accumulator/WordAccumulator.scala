package com.boatfly.spark.examples.accumulator

import java.util

import org.apache.spark.util.AccumulatorV2

//声明累加器
class WordAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  val list = new util.ArrayList[String]()

  //当前的累加器是否处于初始化状态
  override def isZero: Boolean = {
    list.isEmpty
  }

  //复制累加器对象
  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    new WordAccumulator()
  }

  //重置累加器
  override def reset(): Unit = {
    list.clear()
  }

  //向累加器中增加数据
  override def add(v: String): Unit = {
    if (v.contains("s")) {
      list.add(v)
    }
  }

  //合并
  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
    list.addAll(other.value)
  }

  //获取累加器的值
  override def value: util.ArrayList[String] = list
}
