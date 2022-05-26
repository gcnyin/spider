package com.github.gcnyin.spider

import com.github.gcnyin.spider.Dto._
import io.circe.generic.auto._
import io.circe.syntax._
import zio.kafka.producer.Producer
import zio.kafka.serde.Serde
import zio.{Scope, ZIO}

class ModuleInfoService(kafkaProducer: Producer, kafkaTopic: String) {

  def update(module: ModuleInfo): ZIO[Scope, Message, Message] = {
    val key = s"${module.group}.${module.name}"
    kafkaProducer
      .produce(kafkaTopic, key, module.asJson.noSpaces, Serde.string, Serde.string)
      .catchAll(t => ZIO.fail(Message("500", t.getMessage)))
      .map(_ => Message("200", "success"))
  }
}
