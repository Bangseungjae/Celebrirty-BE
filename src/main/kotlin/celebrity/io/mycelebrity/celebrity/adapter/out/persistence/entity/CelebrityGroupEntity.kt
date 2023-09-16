package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(name = "celebrity_group")
@Entity(name = "celebrity_group")
class CelebrityGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "group_name")
    val name: String,

    @OneToMany(mappedBy = "group")
    val celebrityEntity: MutableList<CelebrityEntity> = mutableListOf()
) {
}
