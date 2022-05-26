package com.github.gcnyin.spider

import com.github.gcnyin.spider.Dto._
import io.circe.generic.auto._
import sttp.tapir.{Endpoint, EndpointIO, endpoint}
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

object Api {
  val messageBody: EndpointIO.Body[String, Message] =
    jsonBody[Message].example(Message("1001", "User not found"))

  private val versionInfoBody: EndpointIO.Body[String, VersionInfo] =
    jsonBody[VersionInfo].example(VersionInfo("com.github.gcnyin", "spider", "0.1.0-SNAPSHOT"))

  private val moduleInfoBody: EndpointIO.Body[String, ModuleInfo] =
    jsonBody[ModuleInfo].example(
      ModuleInfo(
        "com.github.gcnyin", "spider", "0.1.0-SNAPSHOT",
        Seq(VersionInfo("com.github.gcnyin", "slisp", "1.3.2"))
      )
    )

  private val basicEndpoint: Endpoint[Unit, Unit, Message, Unit, Any] =
    endpoint.in("api").errorOut(messageBody)

  val healthCheckEndpoint: Endpoint[Unit, Unit, Message, Message, Any] =
    basicEndpoint
      .in("health-check")
      .get
      .out(messageBody)

  val updateModuleInfoEndpoint: Endpoint[Unit, ModuleInfo, Message, Message, Any] =
    basicEndpoint
      .in("module-info")
      .post
      .in(moduleInfoBody)
      .description("update module info")
      .out(messageBody)
}
