package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity

import celebrity.io.mycelebrity.common.persistence.entity.DateTime
import celebrity.io.mycelebrity.common.persistence.entity.Deleted
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommunityEntity
import jakarta.persistence.*

@Table(name = "celebrity")
@Entity(name = "celebrity")
class CelebrityEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "celebrity_id")
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "is_deleted")
    @Enumerated(EnumType.STRING)
    val deleted: Deleted = Deleted.NOT,

    @ManyToOne
    @JoinColumn(name = "celebrity_group_id")
    val group: CelebrityGroupEntity,

    @OneToMany(mappedBy = "to")
    val history: MutableList<CelebrityHistoryEntity> = mutableListOf(),

    @OneToOne(mappedBy = "celebrity")
    val community: CommunityEntity? = null
) : DateTime() {

}
