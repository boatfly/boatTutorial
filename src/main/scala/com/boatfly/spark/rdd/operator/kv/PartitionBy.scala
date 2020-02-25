package com.boatfly.spark.rdd.operator.kv

import com.boatfly.spark.rdd.partitions.MyPartitioner
import org.apache.spark.{Partitioner, SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * k-v类型操作
 * 自定义分区器
 */
object PartitionBy {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val rdd1: RDD[(Int, String)] = sc.parallelize(Array((1, "aaa"), (2, "bbb"), (3, "ccc"), (4, "ddd")), 4)
    //val rdd2: RDD[(Int, String)] = rdd1.partitionBy(new org.apache.spark.HashPartitioner(2)) //以key对2取模的值进行分区，自定义分区器
    val rdd2: RDD[(Int, String)] = rdd1.partitionBy(new MyPartitioner(2)) //使用自定义分区器

    rdd2.glom().collect().foreach(array => {
      println(array.mkString(","))
    })

  }
}
