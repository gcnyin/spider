package com.github.gcnyin.spider

object Dto {
  final case class Message(code: String, message: String)

  final case class ModuleInfo(group: String, name: String, version: String, dependencies: Seq[VersionInfo])

  final case class VersionInfo(group: String, name: String, version: String)
}
