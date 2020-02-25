package com.boatfly.spark.examples

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator

/**
 * 累加器 & 广播变量
 */
object ShareData {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val rdd1: RDD[Int] = sc.makeRDD(List(1, 2, 3, 4, 5))
    val accumulator: LongAccumulator = sc.longAccumulator //声明累加器
    rdd1.foreach {
      case i => {
        //执行累加器的累加功能
        accumulator.add(i)
      }
    }
    println("sum=" + accumulator.value)
  }
}
