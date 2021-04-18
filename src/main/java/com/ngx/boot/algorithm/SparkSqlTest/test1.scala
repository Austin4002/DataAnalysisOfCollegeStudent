package com.ngx.boot.algorithm.SparkSqlTest

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  *
  * @author : 朱坤
  * @date : ${DATA}  
*/object test1 {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local").appName("AreaTopNRoadFlowAnalyzer")
    .config("fs.defaultFS", "hdfs://ip:9000")
    //数据存放位置
    .config("spark.sql.warehouse.dir", "hdfs://ip:9000/test")
    .enableHiveSupport()
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    val df =spark.sql("select * from 数据库名字.表名")

    //往mysql里写入输入
    df.write.format("jdbc").option("driver","com.mysql.jdbc.Driver")
    //数据库名字自己创建
    .option("url", "jdbc:mysql://ip:3306/数据库名字")
    //topn_road_car_count 表名字 自动创建
    .option("dbtable", "topn_road_car_count")
    .option("user", "root")
    .option("password","root")
    .mode(SaveMode.Append).save()
    spark.close()



  }


}
