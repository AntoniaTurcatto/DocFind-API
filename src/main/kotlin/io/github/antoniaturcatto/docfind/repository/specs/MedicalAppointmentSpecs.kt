package io.github.antoniaturcatto.docfind.repository.specs

import io.github.antoniaturcatto.docfind.common.model.MedicalAppointment
import io.github.antoniaturcatto.docfind.common.model.Role
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import java.util.*

class MedicalAppointmentSpecs {
    companion object{
        fun idEqual(id: UUID):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                cb.equal(root.get<UUID>("id"), id)
            }
        }

        fun doctorIdEqual(id: UUID):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinDoctor: Join<Any,Any> = root.join("doctor", JoinType.INNER)
                cb.equal(joinDoctor.get<UUID>("id"), id)
            }
        }

        fun doctorNameLike(name: String):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinDoctor: Join<Any, Any> = root.join("doctor", JoinType.INNER)
                cb.like(cb.upper(joinDoctor.get("name")), "%${name.uppercase()}%")
            }
        }

        fun doctorRoleEqual(role: Role):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinDoctor: Join<Any, Any> = root.join("doctor", JoinType.INNER)
                cb.equal(joinDoctor.get<String>("role"), role.name)
            }
        }

        fun patientIdEqual(id: UUID):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinPatient: Join<Any,Any> = root.join("patient", JoinType.INNER)
                cb.equal(joinPatient.get<UUID>("id"), id)
            }
        }

        fun patientNameLike(name: String):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinPatient: Join<Any,Any> = root.join("patient", JoinType.INNER)
                cb.like(cb.upper(joinPatient.get("name")), "%${name.uppercase()}%")
            }
        }

        fun patientAddressLike(address: String):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinPatient: Join<Any,Any> = root.join("patient", JoinType.INNER)
                cb.like(cb.upper(joinPatient.get("address")), "%${address.uppercase()}%")
            }
        }

        fun patientAgeEqual(age: Int):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                val joinPatient: Join<Any,Any> = root.join("patient", JoinType.INNER)
                cb.equal(joinPatient.get<Int>("age"), age)
            }
        }

        fun dayIn(days: List<Int>):Specification<MedicalAppointment>{
            println("Comparando dias: $days")
            return Specification { root, query, cb ->
                root.get<LocalDateTime>("dateTime")?.let { it ->
                    cb.function(
                        "to_char",
                        String::class.java,
                        it,
                        cb.literal("dd")
                    ).`in`(days.map { it.toString() })
                }
            }
        }

        fun monthIn(months: List<Int>):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                root.get<LocalDateTime>("dateTime")?.let { it ->
                    cb.function(
                    "to_char",
                    String::class.java,
                    it,
                    cb.literal("mm")
                ).`in`(months.map { it.toString() })
                }
            }
        }

        fun yearIn(years: List<Int>):Specification<MedicalAppointment>{
            return Specification { root, query, cb ->
                root.get<LocalDateTime>("dateTime")?.let { it ->
                    cb.function(
                        "to_char",
                        String::class.java,
                        it,
                        cb.literal("mm")
                    ).`in`(years.map { it.toString() })
                }
            }
        }

    }
}