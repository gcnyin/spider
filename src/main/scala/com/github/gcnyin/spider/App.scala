package com.github.gcnyin.spider

import com.github.gcnyin.spider.Dto._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.service.Server
import zio.ZIO

class App {

  import Api._

  private val healthCheckLogic =
    healthCheckEndpoint.zServerLogic(_ => ZIO.succeed(Message("200", "success")))

  private val updateModuleInfoLogic =
    updateModuleInfoEndpoint.zServerLogic(_ => ZIO.succeed(Message("200", "success"))) // TODO

  private val healthCheckApp =
    ZioHttpInterpreter().toHttp(healthCheckLogic)

  private val updateModuleInfoApp =
    ZioHttpInterpreter().toHttp(updateModuleInfoLogic)

  private val app =
    healthCheckApp ++ updateModuleInfoApp

  def run(port: Int): ZIO[Any, Throwable, Nothing] =
    Server.start(port, app)
}
