package safronoff2006.services


import zio.macros.accessible
import zio.{Task, ULayer, ZIO, ZLayer}

import java.util.UUID


case class PhoneRecordDTO(fio: String, phone: String)
case class AddPhoneRecordDTO(fio: String, phone: String)
case class PhoneRecord(id: String, fio: String, phone: String)

@accessible[PhoneBookService]
trait PhoneBookService {
  def find(phone: String): Task[PhoneRecord]
  def insert(phoneRecord: AddPhoneRecordDTO): Task[String]
  def update(id: String, phoneRecord: AddPhoneRecordDTO): Task[Unit]
  def delete(id: String): Task[PhoneRecord]
}

class PhoneBookServiceImpl extends PhoneBookService {
  val repo = scala.collection.mutable.Map[String, PhoneRecord]()

  override def find(phone: String): Task[PhoneRecord] = ZIO.fromOption(repo.find(_._2.phone == phone).map(v => PhoneRecord(v._1,v._2.fio, v._2.phone)))
    .orElseFail(new Throwable("Record not found"))

  override def insert(phoneRecord: AddPhoneRecordDTO): Task[String] = for {
    uuid <- ZIO.succeed(UUID.randomUUID().toString)
    _ <- ZIO.from(repo += uuid -> PhoneRecord(uuid, phoneRecord.fio, phoneRecord.phone))
  } yield uuid

  override def update(id: String, phoneRecord: AddPhoneRecordDTO): Task[Unit] = ZIO.from {
    val record = repo(id)
    repo.update(id, record.copy(phone = phoneRecord.phone, fio = phoneRecord.fio))
  }

  override def delete(id: String): Task[PhoneRecord] = ZIO.fromOption(repo.remove(id)).orElseFail(new Throwable(""))

}

object PhoneBookService{
  val live: ULayer[PhoneBookServiceImpl] = ZLayer.succeed(new PhoneBookServiceImpl)
}