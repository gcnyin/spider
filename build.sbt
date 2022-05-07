ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.github.gcnyin"

val tapirVersion = "1.0.0-M8"
val zioVersion = "2.0.0-RC5"
val zioHttpVersion = "2.0.0-RC7"
val quillVersion = "3.17.0-RC3"
val log4jVersion = "2.17.2"
val zioKafkaVersion = "2.0.0-M3"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "spider",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-jsoniter-scala" % tapirVersion,
      //"io.getquill" %% "quill-zio" % quillVersion,
      //"io.getquill" %% "quill-jasync-mysql" % quillVersion,
      "dev.zio" %% "zio" % zioVersion,
      //"dev.zio" %% "zio-logging" % zioVersion,
      //"dev.zio" %% "zio-logging-slf4j" % zioVersion,
      "dev.zio" %% "zio-kafka" % zioKafkaVersion,
      "io.d11" %% "zhttp" % zioHttpVersion,
      "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
      "dev.zio" %% "zio-test" % zioVersion % Test
    )
  )
