package com.github.gcnyin.spider

final case class Config(kafka: Kafka)

final case class Kafka(bootstrapServers: List[String], topic: String, groupId: String, clientId: String, closeTimeout: Int)