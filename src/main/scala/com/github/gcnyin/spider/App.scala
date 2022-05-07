package com.github.gcnyin.spider

import com.github.gcnyin.spider.Dto._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.service.Server
import zio.kafka.producer._
import zio.kafka.serde.Serde
import zio.{Scope, ZIO}

class App {
  private val producerSettings: ProducerSettings = ProducerSettings(List("localhost:9092"))

  import Api._

  private val healthCheckLogic: ZServerEndpoint[Any, Any] =
    healthCheckEndpoint.zServerLogic(_ => ZIO.succeed(Message("200", "success")))

  private def updateModuleInfoLogic(kafkaProducer: Producer): ZServerEndpoint[Any, Any] =
    updateModuleInfoEndpoint.zServerLogic(moduleInfo => {
      kafkaProducer
        .produce(
          "module_info",
          s"${moduleInfo.group}.${moduleInfo.name}",
          moduleInfo.version,
          Serde.string,
          Serde.string)
        .catchAll(t => ZIO.fail(Message("500", t.getMessage)))
        .map(_ => Message("200", "success"))
    })

  def run(port: Int): ZIO[Scope, Throwable, Nothing] =
    for {
      producer <- Producer.make(producerSettings)
      healthCheckApp = ZioHttpInterpreter().toHttp(healthCheckLogic)
      updateModuleInfoApp = ZioHttpInterpreter().toHttp(updateModuleInfoLogic(producer))
      app = healthCheckApp ++ updateModuleInfoApp
      _ <- Server.start(port, app)
    } yield ()
}
