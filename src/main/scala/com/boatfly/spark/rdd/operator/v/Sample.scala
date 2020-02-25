package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 以指定的随机种子随机抽样出数量为fraction的数据，withReplacement表示数据是否放回，true：有放回的抽样；false：不放回的抽样；seed用于指定随机数生成器种子。
 */
object Sample {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

    val sampleRDD: RDD[Int] = listRDD.sample(false, 0.4, 1)

    sampleRDD.collect().foreach(println)
  }
}
