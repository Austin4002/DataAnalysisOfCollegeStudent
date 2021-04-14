package com.ngx.boot.algorithm.SparkSqlTest

import org.apache.spark.sql.SparkSession

object hive {

  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder().master("spark://spark1:7077")
      .appName("hive")
      //.config("hive.metastore.uris", "thrift://spark1:9083")
      .enableHiveSupport()
      .getOrCreate()



    print("hive下的数据库：")
    println("------------------------")
    spark.sql("show databases").show()
    //    spark.sql("use default").show()
    //    spark.sql("show tables").show()

    print("连接数据库")
    println("------------------------")
    spark.sql("select * from test.city_info").show()



  }
}
