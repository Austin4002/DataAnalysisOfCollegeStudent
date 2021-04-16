package com.ngx.boot.algorithm.SparkSqlTest

import java.util
import java.lang

import org.apache.spark.{SparkConf, SparkContext, sql}
import org.apache.spark.sql.{Row, SQLContext, SparkSession}
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormatter
import shapeless.ops.nat.ToInt

import scala.concurrent.duration.DurationConversions.fromNowConvert.R

object hive{

  def main(): Row = {



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


    val man = "select stu_name from stu_info"
    val woman = "select count(stu_sex) from stu_info where stu_sex='女'"
    //SQLContext st = new SQLContext(spark)

    val st = spark.sql(man).collect()
    println("arr(0) is " + st(0) + " arr(2) is " + st(1) + " arr(4) is " + st(2))

    st(0)







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
