MY_DIR=$(cd `dirname $0` ; pwd)
cd $MY_DIR
source ./setenv.sh
cd ..
sbt package
$SPARK_HOME/bin/spark-submit \
  --class "com.jwf.MovieRecommender" \
  --master local[4] \
  target/scala-2.10/spark-movies_2.10-0.0.1.jar \
  $MY_DIR/../src/test/resources/
