package com.github.gcnyin.spider

import zio.{Scope, ZIO, ZIOAppDefault}

object Main extends ZIOAppDefault {
  override def run: ZIO[Scope, Any, Any] =
    new App().run(8080)
}
