package com.boatfly.spark.examples.structuredstreaming.source.kafka

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *
 */
object FromKafka {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[*]").appName("ss-wordcount").getOrCreate()
    import spark.implicits._

    val kafkaDF: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "topic1,topic2")
      .load
      //.select("value")
      .selectExpr("cast(value as string)")
      .as[String]
      .flatMap(_.split(" "))
      .groupBy("value")
      .count()

    kafkaDF.writeStream
      .format("console")
      .outputMode("update")
      .start()
      .awaitTermination()
  }
}
