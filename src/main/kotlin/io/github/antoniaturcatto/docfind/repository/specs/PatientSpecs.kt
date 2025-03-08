package io.github.antoniaturcatto.docfind.repository.specs

import io.github.antoniaturcatto.docfind.common.model.Patient
import org.springframework.data.jpa.domain.Specification
import java.util.UUID

class PatientSpecs {

    companion object{
        fun idEqual(id:UUID):Specification<Patient>{
            return Specification { root, query, criteriaBuilder ->
                criteriaBuilder.equal(root.get<UUID>("_id"), id)
            }
        }

        fun nameLike(name:String):Specification<Patient>{
            return Specification { root, query, criteriaBuilder ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("_name")), name.uppercase())
            }
        }

        fun ageEqual(age:Int):Specification<Patient>{
            return Specification { root, query, cb ->
                cb.equal(root.get<Int>("age"), age)
            }
        }

        fun addressLike(address: String):Specification<Patient>{
            return Specification { root, query, cb ->
                cb.like(cb.upper(root.get("_address")), address.uppercase())
            }
        }
    }
}