package com.github.gcnyin.spider

import com.github.gcnyin.spider.Dto._
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.service.Server
import zio.kafka.producer._
import zio.{Scope, ZIO}

class App {

  import Api._

  private val healthCheckLogic: ZServerEndpoint[Any, Any] =
    healthCheckEndpoint.zServerLogic(_ => ZIO.succeed(Message("200", "success")))

  def run(port: Int): ZIO[Scope, Throwable, Unit] =
    for {
      config <- ZIO.fromEither(ConfigSource.default.load[Config])
        .mapError(f => new RuntimeException(f.head.description))
      producer <- Producer.make(ProducerSettings(config.kafka.bootstrapServers))
      service = new ModuleInfoService(producer, config.kafka.topic)
      healthCheckApp = ZioHttpInterpreter().toHttp(healthCheckLogic)
      updateModuleInfoApp = ZioHttpInterpreter().toHttp(updateModuleInfoEndpoint.zServerLogic(service.update))
      app = healthCheckApp ++ updateModuleInfoApp
      _ <- Server.start(port, app)
    } yield ()
}
