package safronoff2006.endpoints




import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import org.http4s._
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.dsl._
import safronoff2006.services.{AddPhoneRecordDTO, PhoneBookService}
import zio._
import zio.interop.catz._









class PhoneBookEndpoints[R <: PhoneBookService ] {

  type PhoneBookTask[A] = RIO[R, A]

  private val dsl  = Http4sDsl[PhoneBookTask]

  import dsl._








    implicit def jsonDecoder[A](implicit decoder: Decoder[A]): EntityDecoder[PhoneBookTask, A] = jsonOf[PhoneBookTask, A]


    implicit def jsonEncoder[A](implicit encoder: Encoder[A]): EntityEncoder[PhoneBookTask, A] = jsonEncoderOf[PhoneBookTask, A]




  val routes: HttpRoutes[PhoneBookTask] = HttpRoutes.of[PhoneBookTask]{

    case GET -> Root / phone =>
      for {
      _ <- ZIO.from(println(phone))
      res <-  PhoneBookService.find(phone).foldZIO(
        err => NotFound(),
        result => Ok(result))
      } yield res



    case req @ POST -> Root => {
      for {
        record <- req.as[AddPhoneRecordDTO]
        _ <- ZIO.from(println(record.toString))
        result <- PhoneBookService.insert(record)
      } yield ()
    }.foldZIO(
      err => NotFound(),
      result => Ok(result)
    )


    case req @ PUT -> Root / id => {
      for {
        record <- req.as[AddPhoneRecordDTO]
        _ <- ZIO.from(println(record.toString))
        result <- PhoneBookService.update(id, record)
      } yield ()
    }.foldZIO(
      err => NotFound(),
      result => Ok(result)
    )

    case DELETE -> Root / id => PhoneBookService.delete(id).foldZIO(
      err => NotFound(),
      result => Ok(result)
    )

  }

}
