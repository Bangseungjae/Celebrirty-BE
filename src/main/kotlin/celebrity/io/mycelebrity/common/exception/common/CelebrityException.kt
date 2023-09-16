package celebrity.io.mycelebrity.common.exception.common

data class CelebrityException private constructor(
    private val baseException: BaseException,
) : RuntimeException() {

    companion object {
        operator fun invoke(baseException: BaseException) = CelebrityException(baseException)
    }

    override val message: String?
        get() = baseException.getMessage()
}
