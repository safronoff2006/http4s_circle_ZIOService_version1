package safronoff2006

import zio.{Scope, ZIOAppDefault}

object Main extends ZIOAppDefault {
  override def run =
    Server.server.provideSome[Scope](Server.appEnv).exitCode
}