package com.boatfly.spark.rdd.operator.kv

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * aggregateByKey的简化操作，seqOp和combOp相同
 */
object FoldByKey {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[(String, Int)] = sc.parallelize(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)

    //rdd1.glom().collect() 查看数据在两个分区中的分布

    //需求：取出每个分区中相同key的最大值，然后相加
    //val rdd2: RDD[(String, Int)] = rdd1.aggregateByKey(0)(math.max(_, _), _ + _)
    //val rdd2: RDD[(String, Int)] = rdd1.aggregateByKey(0)(_+_, _ + _) //分区内相同key值相加，最后跨区再相加
    val rdd2: RDD[(String, Int)] = rdd1.foldByKey(0)(_ + _)

    rdd2.collect().foreach(tp => {
      println(tp._1 + "'s max=" + tp._2)
    })
  }
}
