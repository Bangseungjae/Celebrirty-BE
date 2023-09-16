package celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity

import celebrity.io.mycelebrity.common.persistence.entity.DateTime
import jakarta.persistence.*

@Table(name = "celebrity_history")
@Entity(name = "celebrity_history")
class CelebrityHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "celebrity_history_id")
    val id: Long? = null,

    @Column(name = "from_user")
    val from: String,

    @ManyToOne
    @JoinColumn(name = "to_celebrity")
    val to: CelebrityEntity,
): DateTime() {

}
