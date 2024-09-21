package honey.bot.api.management

import com.google.gson.Gson

internal class QueryEntity(
    val gson: Gson,
    val key: String,
    val value: Any,
) {
    val valueAsString: String
        get() {
            return if (value is String) {
                value
            } else {
                valueAsJson
            }
        }

    val valueAsJson: String
        get() = gson.toJson(value)
}