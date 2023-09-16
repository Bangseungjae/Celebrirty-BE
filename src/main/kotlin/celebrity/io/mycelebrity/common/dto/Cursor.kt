package celebrity.io.mycelebrity.common.dto

import celebrity.io.mycelebrity.common.exception.common.CelebrityException
import celebrity.io.mycelebrity.common.exception.error.CommonTypeException

data class Cursor private constructor(
    var next: Long? = null,
    var size: Long,
) {

    companion object {

        operator fun invoke(
            next: Long?,
            size: Long?,
        ): Cursor {
            validateNext(next)
            return Cursor(
                size = if (size == null || size < 1) DEFAULT_SIZE else size,
                next = next,
            )
        }

        private fun validateNext(next: Long?) {
            if (next == null) return

            if (next < 0L) throw CelebrityException(CommonTypeException.BAD_REQUEST)
        }
    }
}

const val DEFAULT_SIZE = 10L
