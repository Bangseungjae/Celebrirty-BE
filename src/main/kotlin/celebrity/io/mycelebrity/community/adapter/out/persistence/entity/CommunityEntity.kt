package celebrity.io.mycelebrity.community.adapter.out.persistence.entity

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.common.persistence.entity.DateTime
import jakarta.persistence.*

@Table(name = "community")
@Entity(name = "community")
class CommunityEntity(
    @Id
    @Column(name = "community_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne
    @JoinColumn(name = "celebrity_id")
    var celebrity: CelebrityEntity,

    @Column(name = "description")
    val description: String,

    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        mappedBy = "community"
    )
    val comment: MutableList<CommentEntity> = mutableListOf(),

    @Column(name = "url")
    val url: String,
) : DateTime() {
}
