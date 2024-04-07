package safronoff2006


import com.comcast.ip4s.{Host, Port}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import safronoff2006.endpoints.PhoneBookEndpoints
import safronoff2006.services.PhoneBookService
import zio.interop.catz._
import zio.{RIO, ZIO}

object Server {

  type AppEnv = PhoneBookService

  val appEnv = PhoneBookService.live

  type AppTask[A] = RIO[AppEnv, A]

  val httpApp = Router("api" -> new PhoneBookEndpoints[AppEnv]().routes).orNotFound

  val server: ZIO[AppEnv, Serializable, Unit] = for{
    host <- ZIO.from(Host.fromString("0.0.0.0"))
    port <- ZIO.from(Port.fromInt(8081))
    _ <- EmberServerBuilder.default[AppTask]
      .withHost(host)
      .withPort(port)
      .withHttpApp(httpApp)
      .build
      .useForever
  } yield ()


}
