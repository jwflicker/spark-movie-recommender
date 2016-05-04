scalaVersion := "2.10.5"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

organization := "com.ibm.mf"

name := "spark-movies"

publishMavenStyle := true

version := "0.0.1"


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")
addSbtPlugin("org.spark-packages" % "sbt-spark-package" % "0.2.2")
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")

sparkVersion := "1.6.1"

sparkComponents ++= Seq("core", "streaming", "sql", "mllib", "streaming-kafka")

parallelExecution in Test := false

// additional libraries
libraryDependencies ++= Seq(
  "com.holdenkarau" %% "spark-testing-base" % "1.6.0_0.3.1")

scalacOptions ++= Seq("-deprecation", "-unchecked")

pomIncludeRepository := { x => false }

resolvers ++= Seq(
   "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
   "Spray Repository" at "http://repo.spray.cc/",
   "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
   "Akka Repository" at "http://repo.akka.io/releases/",
   "Twitter4J Repository" at "http://twitter4j.org/maven2/",
   "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
   "Twitter Maven Repo" at "http://maven.twttr.com/",
   "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
   Resolver.sonatypeRepo("public")
)
