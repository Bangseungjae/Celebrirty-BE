package celebrity.io.mycelebrity.common.dto

data class CursorResult<T> (
    val values: List<T>,
    val hasNext: Boolean,
    val lastIndex: Long,
){
}
