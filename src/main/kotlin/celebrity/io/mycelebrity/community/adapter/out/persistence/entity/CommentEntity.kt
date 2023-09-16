package celebrity.io.mycelebrity.community.adapter.out.persistence.entity

import celebrity.io.mycelebrity.common.persistence.entity.DateTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "comment")
@Entity(name = "comment")
class CommentEntity(
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "community_id")
    val community: CommunityEntity,

    @Column(name = "content")
    val content: String,
) : DateTime(){
}
