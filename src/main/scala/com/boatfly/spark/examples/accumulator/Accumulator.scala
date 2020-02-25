package com.boatfly.spark.examples.accumulator

import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.util.AccumulatorV2

/**
 * 自定义累加器
 */
object Accumulator {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val accumulator = new WordAccumulator //声明累加器
    sc.register(accumulator) //注册累加器

    val rdd: RDD[String] = sc.makeRDD(List("spark", "scala", "solidity", "ethereum"), 2)

    rdd.foreach{
      case word=>{
        accumulator.add(word)
      }
    }

    println(accumulator.value)

  }
}

