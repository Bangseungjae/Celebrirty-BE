package celebrity.io.mycelebrity.community.adapter.out.persistence

import celebrity.io.mycelebrity.common.annotation.PersistenceAdapter
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.CommentEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommentEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommentEntity.commentEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommunityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.entity.QCommunityEntity.communityEntity
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommentEntityJpaRepository
import celebrity.io.mycelebrity.community.adapter.out.persistence.repository.CommunityEntityJpaRepository
import celebrity.io.mycelebrity.community.application.dto.CommentResponse
import celebrity.io.mycelebrity.community.application.port.out.CommentPagingPort
import celebrity.io.mycelebrity.community.application.port.out.LoadComment
import celebrity.io.mycelebrity.community.domain.Comment
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException

@PersistenceAdapter
class CommentPersistenceAdapter(
    private val commentEntityJpaRepository: CommentEntityJpaRepository,
    private val communityEntityJpaRepository: CommunityEntityJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : LoadComment, CommentPagingPort {

    private val logger = LoggerFactory.getLogger(CommentPersistenceAdapter::class.java)

    override fun loadComment(
        comment: Comment,
        communityId: Long,
    ): Unit = communityEntityJpaRepository.findById(communityId)
        .orElseThrow { IllegalArgumentException("해당 community id의 커뮤니티가 존재하지 않습니다.: id=${communityId}") }
        .let {
            CommentEntity(
                community = it,
                content = comment.comment
            )
        }.let {
            commentEntityJpaRepository.save(it)
            logger.info("comment 작성 : $it")
        }

    override fun cursorPaging(
        next: Long,
        size: Long,
        communityId: Long,
    ): List<CommentResponse> {
        return queryFactory.select(
            Projections.constructor(
                CommentResponse::class.java,
                commentEntity.id,
                commentEntity.content,
                commentEntity.createdAt,
            )
        )
            .from(commentEntity)
            .where(
                commentEntity.id.lt(next)
                    .and(commentEntity.community.id.eq(communityId))
            )
            .orderBy(commentEntity.id.desc())
            .limit(size)
            .fetch()
    }

    override fun cursorPaging(
        size: Long,
        communityId: Long,
    ): List<CommentResponse> {
        return queryFactory.select(
            Projections.constructor(
                CommentResponse::class.java,
                commentEntity.id,
                commentEntity.content,
                commentEntity.createdAt,
            )
        )
            .from(commentEntity)
            .where(
                commentEntity.community.id.eq(communityId)
            )
            .orderBy(commentEntity.id.desc())
            .limit(size)
            .fetch()
    }

    override fun hasNext(communityId: Long, lastIndex: Long): Boolean {
        val fetch: CommentEntity? = queryFactory
            .select(commentEntity)
            .from(commentEntity)
            .where(
                commentEntity.community.id.eq(communityId)
                    .and(commentEntity.id.lt(lastIndex))
            )
            .orderBy(commentEntity.id.desc())
            .limit(1)
            .fetchOne()
        return fetch?.let { true }?:let { false }
    }
}
