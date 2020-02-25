package com.boatfly.spark.examples.sql

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object SparkSQL {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql_demo_rdd_df_ds")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //创建rdd
    val rdd1: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(Array((1, "spark", 75), (2, "scala", 60), (3, "ethereum", 100)))

    //在进行rdd df ds之间进行转换之前，需要导入隐式转换规则：
    import spark.implicits._

    //转换为DF
    val df: DataFrame = rdd1.toDF("id", "name", "price")
    //df.show()

    //转换为DS
    val ds: Dataset[Book] = df.as[Book]
    //ds.show()

    //再转换为df
    val df1: DataFrame = ds.toDF()
    //df1.show()

    //再转为rdd
    val rdd2: RDD[Row] = df1.rdd
    rdd2.foreach(row => {
      println(row.get(0) + "," + row.get(1) + "," + row.get(2))
    })

    val rdd3: RDD[Book] = ds.rdd
    rdd3.foreach(book => {
      println(book.id + "," + book.name + "," + book.price)
    })

    //rdd->ds
    val bookRDD: RDD[Book] = rdd1.map {
      case (id, name, price) => {
        Book(id, name, price)
      }
    }
    bookRDD.foreach(book=>{
      println("-:"+book.id + "," + book.name + "," + book.price)
    })

    val ds2: Dataset[Book] = bookRDD.toDS()
    ds2.show()

    //释放资源
    spark.stop()
  }
}