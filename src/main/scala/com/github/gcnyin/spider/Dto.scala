package com.github.gcnyin.spider

object Dto {
  final case class Message(code: String, message: String)

  final case class RichModuleInfo(group: String, name: String, version: String, dependencies: Seq[ModuleInfo])

  final case class ModuleInfo(group: String, name: String, version: String)
}
