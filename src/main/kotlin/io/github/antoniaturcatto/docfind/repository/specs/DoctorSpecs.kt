package io.github.antoniaturcatto.docfind.repository.specs

import io.github.antoniaturcatto.docfind.model.Doctor
import io.github.antoniaturcatto.docfind.model.Role
import org.springframework.data.jpa.domain.Specification
import java.util.UUID

class DoctorSpecs {

    companion object{

        fun idEqual(id: UUID):Specification<Doctor>{
            return Specification { root, query, cb ->
                cb.equal(root.get<UUID>("_id"), id)
            }
        }

        fun nameLike(name: String): Specification<Doctor> {
            return Specification { root, query, cb->
                cb.like(cb.upper(root.get("_name")), "%${name.uppercase()}%")
            }
        }

        fun roleEqual(role: Role):Specification<Doctor>{
            return Specification { root, query, cb ->
                cb.equal(root.get<Role>("_role"), role)
            }
        }

    }
}