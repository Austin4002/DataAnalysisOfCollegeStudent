package com.ngx.boot.algorithm.SparkSqlTest;

/**
 * @author : 朱坤
 * @date :
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HiveJDBC {

    private static String driverName="org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://192.168.195.11:10000/analysisdata";
    private static String user = "hadoop";
    private static String password="hadoop";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    @Before
    public void init() throws Exception{
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
        stmt = conn.createStatement();
    }
    @Test
    public void createDatabase() throws Exception{
        String sql = "create database hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.executeQuery(sql);
    }
    @Test
    public void dropDatabase() throws Exception {
        String sql = "drop database if exists hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    @Test
    public void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql + "\n");
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) );
        }
    }

    @Test
    public void createTable() throws Exception {
        String sql = "create table t2(id int ,name String) row format delimited fields terminated by ',';";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    @Test
    public void loadData() throws Exception {
        String filePath = "/usr/tmp/student";
        String sql = "load data local inpath '" + filePath + "' overwrite into table t2";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    @Test
    public void selectData() throws Exception {
        String sql = "select * from t2";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("编号" + "\t" + "姓名" );
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
        }
    }
    @Test
    public static void drop(Statement stmt) throws Exception {
        String dropSQL = "drop table t2";
        boolean bool = stmt.execute(dropSQL);
        System.out.println("删除表是否成功:" + bool);
    }
    @After
    public void destory() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

}

