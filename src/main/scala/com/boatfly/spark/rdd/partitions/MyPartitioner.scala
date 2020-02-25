package com.boatfly.spark.rdd.partitions

import org.apache.spark.Partitioner

class MyPartitioner(partitions: Int) extends Partitioner {
  override def numPartitions: Int = {
    partitions
  }

  override def getPartition(key: Any): Int = {
    val rawMod = key.hashCode() % 2
    rawMod + (if (rawMod < 0) 2 else 0)
  }
}
