package com.boatfly.spark.examples.streaming.receiver

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

//自定义采集器
object MyReceiver {
  def main(args: Array[String]): Unit = {

  }
}

//声明采集器
class MyReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){
  override def onStart(): Unit = {
    //@TODO 参考socketreceiver
  }

  override def onStop(): Unit = {
    //@TODO 参考socketreceiver
  }
}