package com.ngx.boot.algorithm.SparkSqlTest

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import shapeless.ops.nat.ToInt

import scala.concurrent.duration.DurationConversions.fromNowConvert.R

object hive{

  def main(args: Array[String]): Unit = {



    val spark: SparkSession = SparkSession.builder().master("spark://spark1:7077")
      .appName("hive")
      //.config("hive.metastore.uris", "thrift://spark1:9083")
      .enableHiveSupport()
      .getOrCreate()


    print("hive下的数据库：")
    println("------------------------")
    spark.sql("show databases").show()
    spark.sql("use analysisdata")
    //    spark.sql("show tables").show()

    print("连接数据库")
    println("------------------------")
    //spark.sql("select count(stu_sex) from stu_info where stu_sex='男'").show()


    val man = "select count(stu_sex) from stu_info where stu_sex='男'"
    val woman = "select count(stu_sex) from stu_info where stu_sex='女'"


    spark.sql(man).show(1)
    val womanrdd = spark.sql(woman).toString()


    //scalatest.getDFFromMysql(spark,man)

//    val conf = new SparkConf().setAppName("wordcount").setMaster("local")
//    val sc = new SparkContext(conf)

    //println(manrdd)
    //println(womanrdd)




//    s_c_join.coalesce(1).toJavaRDD.map(new Nothing() {
//      @throws[Exception]
//      def call(row: Nothing): String = row.toString
//    }).saveAsTextFile("E:\\data\\s_c_join3.txt")

   // val input = sc.textFile("filePath")



  }
}
