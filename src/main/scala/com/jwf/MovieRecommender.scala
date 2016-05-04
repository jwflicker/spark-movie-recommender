/*
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015. All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
package com.jwf

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.ArrayType
import scala.collection.mutable.WrappedArray
import org.apache.spark.sql.Row
import org.apache.spark.streaming.dstream.DStream

object MovieRecommender {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Movies")
    conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

    val sc = new SparkContext(conf)
    var sqlContext = new SQLContext(sc);

    val resourcesDir = args(0)
    val links = sc.textFile(resourcesDir+"links.csv")
    val moviesText = sc.textFile(resourcesDir+"movies.csv")
    val ratingsText = sc.textFile(resourcesDir+"ratings.csv")
    val tags = sc.textFile(resourcesDir+"tags.csv")
    
    println("hello")
    println(tags.first)
  }
}