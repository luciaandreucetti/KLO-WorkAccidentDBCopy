package dataaccess


import java.time.LocalDateTime

// Data Transfer objects for the DB layer

case class WorkAccidentRecord(
                         id: Long,
                         when: LocalDateTime,
                         location: String,
                         regionId: Option[Int],
                         blogPostUrl: String,
                         details: String,
                         investigation:String,
                         initialSource:String,
                         mediaReports:String,
                         publicRemarks:String,
                         sensitiveRemarks:String
                       )

case class InjuredWorkerRecord(
                          id:Long,
                          accidentId:Long,
                          name:String,
                          age:Option[Int],
                          citizenship:Option[Int],
                          industry: Option[Int],
                          employer: Option[Long],
                          from:String,
                          injuryCause: Option[Int],
                          injurySeverity: Option[Int],
                          injuryDescription: String,
                          publicRemarks:String,
                          sensitiveRemarks:String
                        )

case class RelationToAccidentRecord(
                                   accidentId: Long,
                                   relationTypeId: Int,
                                   businessEntityId: Long
                                   )