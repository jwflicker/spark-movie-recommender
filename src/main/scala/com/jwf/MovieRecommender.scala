package com.jwf

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating


object MovieRecommender {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Movies")
    val sc = new SparkContext(conf)
    var sqlContext = SQLContext.getOrCreate(sc);
    
    val resourcesDir = args(0)
    val moviesText = sc.textFile(resourcesDir + "movies.csv")
    val ratingsText = sc.textFile(resourcesDir + "ratings.csv")

    val mHeader = moviesText.first
    val mData = moviesText.filter(_ != mHeader).map(_.split(","))
    sqlContext.createDataFrame(
      mData.map(row => Row(
        row(0).toInt,
        row(1),
        row(2))),
      StructType(Array(
        StructField("movieId", IntegerType, true),
        StructField("title", StringType, true),
        StructField("genres", StringType, true)))).registerTempTable("movies")

    val rHeader = ratingsText.first
    val rData = ratingsText.filter(_ != rHeader).map(_.split(","))
    val ratings = sqlContext.createDataFrame(
      rData.map(row => Row(
        row(0).toInt,
        row(1).toInt,
        row(2).toDouble,
        row(3).toLong)),
      StructType(Array(
        StructField("userId", IntegerType, true),
        StructField("movieId", IntegerType, true),
        StructField("rating", DoubleType, true),
        StructField("timestamp", LongType, true))))
    
        ratings.registerTempTable("ratings")
     
    val x = ratings.rdd.randomSplit(Array(0.6, 0.2, 0.2), 0L)
  
    val trainingSet = x(0).map(row=>Rating(row.getInt(0), row.getInt(1), row.getDouble(2)))
    val model = ALS.train(trainingSet, 10, 10, 0.01)

    val testSet = x(1).map(row=>(row.getInt(0), row.getInt(1)))
    val predictions = model.predict(testSet)
    
    val display = trainingSet.map(r=>(r, "training")).union(
                  predictions.map(r=>(r, "prediction")))
                  
    println(display.first)
  }
}