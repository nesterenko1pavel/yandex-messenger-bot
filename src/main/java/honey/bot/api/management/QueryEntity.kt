package honey.bot.api.management

import com.google.gson.Gson

internal class QueryEntity(
    private val gson: Gson,
    val key: String,
    val value: Any,
) {
    val valueAsString: String
        get() {
            return if (value is String) {
                value
            } else {
                gson.toJson(value)
            }
        }
}