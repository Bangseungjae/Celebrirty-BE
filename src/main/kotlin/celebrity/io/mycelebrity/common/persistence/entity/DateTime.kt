package celebrity.io.mycelebrity.common.persistence.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.ZoneId
import java.time.ZonedDateTime

@MappedSuperclass
abstract class DateTime {
    @Column(name = "created_at", nullable = false)
    @get:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = DATE_PATTERN
    )
    var createdAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of(KR_LOCATION))

    @Column(name = "updated_at", nullable = false)
    @get:JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = DATE_PATTERN
    )
    var updatedAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of(KR_LOCATION))

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of(KR_LOCATION))
        updatedAt = ZonedDateTime.now(ZoneId.of(KR_LOCATION))
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now(ZoneId.of(KR_LOCATION))
    }
}

const val KR_LOCATION = "Asia/Seoul"
const val DATE_PATTERN = "yyyy-MM-dd HH:mm"
