package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 对相同key，把v合并成一个集合
 * createCombiner:初始的结构转换规则
 *
 */
object CombineByKey {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("a", 3), ("a", 2), ("b", 4), ("b", 3), ("b", 6), ("a", 8)), 2)

    //rdd1.glom().collect() 查看数据在两个分区中的分布

    //需求：取出相同key的平均值
    val rdd2: RDD[(String, (Int, Int))] = rdd1.combineByKey((_, 1), (acc: (Int, Int), v) => (acc._1 + v, acc._2 + 1), (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._1, acc1._2 + acc2._2))

    val rdd3: RDD[(String, Double)] = rdd2.map { case (key, value) => (key, value._1 / value._2.toDouble) }

    rdd3.collect().foreach(tp => {
      println(tp._1 + "'s average=" + tp._2)
    })

  }
}
