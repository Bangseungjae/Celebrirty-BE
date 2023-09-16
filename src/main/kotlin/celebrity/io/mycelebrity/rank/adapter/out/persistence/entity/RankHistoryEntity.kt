package celebrity.io.mycelebrity.rank.adapter.out.persistence.entity

import celebrity.io.mycelebrity.celebrity.adapter.out.persistence.entity.CelebrityEntity
import celebrity.io.mycelebrity.common.persistence.entity.DateTime
import jakarta.persistence.*

@Table(name = "rank_history")
@Entity(name = "rank_history")
class RankHistoryEntity(
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "rank_history_id")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "celebrity_id")
    val celebrity:  CelebrityEntity,

    @Column(name = "before_rank")
    val beforeRank: Long,
) : DateTime(){
}
