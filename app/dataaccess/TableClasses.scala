package dataaccess

import java.sql.Timestamp
import models.{BusinessEntity, Citizenship, Industry, InjuryCause, Invitation, PasswordResetRequest, Region, User}
import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDateTime
import scala.reflect.ClassTag

class UsersTable(tag:Tag) extends Table[User](tag,"users") {

  def id                = column[Long]("id",O.PrimaryKey, O.AutoInc)
  def username          = column[String]("username")
  def name              = column[String]("name")
  def email             = column[String]("email")
  def encryptedPassword = column[String]("encrypted_password")

  def * = (id, username, name, email, encryptedPassword) <> (User.tupled, User.unapply)

}

class InvitationsTable(tag:Tag) extends Table[Invitation](tag, "invitations") {
  def email = column[String]("email", O.PrimaryKey)
  def date  = column[Timestamp]("date")
  def uuid  = column[String]("uuid")
  def sender  = column[String]("sender",O.PrimaryKey)

//  def pk = primaryKey("invitation_pkey", (email, sender))

  def * = (email, date, uuid, sender) <> (Invitation.tupled, Invitation.unapply)
}

class PasswordResetRequestsTable(tag:Tag) extends Table[PasswordResetRequest](tag, "password_reset_requests"){
  def username = column[String]("username")
  def uuid     = column[String]("uuid")
  def reset_password_date = column[Timestamp]("reset_password_date")

  def pk = primaryKey("uuid_for_forgot_password_pkey", (username, uuid))

  def * = (username, uuid, reset_password_date) <> (PasswordResetRequest.tupled, PasswordResetRequest.unapply)
}

class BusinessEntityTable(tag:Tag) extends Table[BusinessEntity](tag, "business_entities") {
  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name")
  def phone = column[Option[String]]("phone")
  def email = column[Option[String]]("email")
  def website = column[Option[String]]("website")
  def isPrivatePerson = column[Boolean]("is_private_person")
  def memo = column[Option[String]]("memo")
  
  def * = (
    id, name, phone, email, website, isPrivatePerson, memo
  ) <> (BusinessEntity.tupled, BusinessEntity.unapply)
  
  def nameIdx = index("business_entities_name", (name))
}

class IdNameTable[T](tag: Tag, tableName: String, apply: (Int, String) => T, unapply: T => Option[(Int, String)])
                    (implicit classTag: ClassTag[T]) extends Table[T](tag, tableName) {
  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)
  def name = column[String]("name", O.Unique)
  def * = (id, name) <> (apply.tupled, unapply)
}

class RegionsTable(tag: Tag) extends IdNameTable[Region](tag, "regions", Region.apply, Region.unapply)

class CitizenshipsTable(tag:Tag) extends IdNameTable[Citizenship](tag, "citizenships", Citizenship, Citizenship.unapply)

class IndustriesTable(tag:Tag) extends IdNameTable[Industry](tag, "industries", Industry, Industry.unapply)

class InjuryCausesTable(tag:Tag) extends IdNameTable[InjuryCause](tag, "injury_causes", InjuryCause, InjuryCause.unapply)

class WorkAccidentsTable(t:Tag) extends Table[WorkAccidentRecord](t, "work_accidents") {
  
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def date_time = column[LocalDateTime]("date_time")
  def entrepreneur_id = column[Option[Long]]("entrepreneur_id")
  def region_id = column[Option[Int]]("region_id")
  def blog_post_url = column[String]("blog_post_url")
  def details = column[String]("details")
  def investigation = column[String]("investigation")
  def mediaReports = column[String]("mediaReports")
  def public_remarks = column[String]("public_remarks")
  def sensitive_remarks = column[String]("sensitive_remarks")
  
  def * = (id, date_time, entrepreneur_id, region_id, blog_post_url, details, investigation, mediaReports, public_remarks, sensitive_remarks
  )<>(WorkAccidentRecord.tupled, WorkAccidentRecord.unapply)
  
  def fkEnt = foreignKey("fk_wa_ent", entrepreneur_id, TableRefs.businessEntities)(_.id.?)
  def fkRgn = foreignKey("fk_wa_rgn", region_id, TableRefs.regions)(_.id.?)
}

class InjuredWorkersTable(t:Tag) extends Table[InjuredWorkerRecord](t, "injured_workers") {
  
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def accident_id = column[Long]("accident_id")
  def name = column[String]("name")
  def age = column[Option[Int]]("age")
  def citizenship_id = column[Option[Int]]("citizenship_id")
  def industry_id = column[Option[Int]]("industry_id")
  def from_place = column[String]("from_place")
  def injury_cause_id = column[Option[Int]]("injury_cause_id")
  def injury_severity = column[Option[String]]("injury_severity")
  def injury_description = column[String]("injury_description")
  def public_remarks = column[String]("public_remarks")
  def sensitive_remarks = column[String]("sensitive_remarks")
  
  def * = (id, accident_id, name, age, citizenship_id, industry_id, from_place, injury_cause_id, injury_severity,
    injury_description, public_remarks, sensitive_remarks)<>(InjuredWorkerRecord.tupled, InjuredWorkerRecord.unapply)
  
  def fkAcc = foreignKey("fk_iw_wa", accident_id, TableRefs.accidents)(_.id)
  def fkCtz = foreignKey("fk_iw_cz", citizenship_id, TableRefs.citizenships)(_.id.?)
  def fkInd = foreignKey("fk_iw_in", industry_id, TableRefs.industries)(_.id.?)
  def fkICs = foreignKey("fk_iw_ic", injury_cause_id, TableRefs.injuryCauses)(_.id.?)
}

object TableRefs {
  val businessEntities = TableQuery[BusinessEntityTable]
  val regions = TableQuery[RegionsTable]
  val accidents = TableQuery[WorkAccidentsTable]
  val citizenships = TableQuery[CitizenshipsTable]
  val industries = TableQuery[IndustriesTable]
  val injuryCauses = TableQuery[InjuryCausesTable]
}