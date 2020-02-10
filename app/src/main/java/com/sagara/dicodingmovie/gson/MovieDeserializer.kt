package com.sagara.dicodingmovie.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.sagara.dicodingmovie.service.response.Movie
import java.lang.reflect.Type

class MovieDeserializer: JsonDeserializer<Movie> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Movie {
        val jsonObject = json?.asJsonObject ?: JsonObject()

        return Movie(
            jsonObject.get("id")?.asInt ?: 0,
            jsonObject.get("popularity")?.asDouble ?: 0.0,
            jsonObject.get("vote_count")?.asInt ?: 0,
             if (jsonObject.get("poster_path").isJsonNull) "" else jsonObject.get("poster_path").asString,
            jsonObject.get("title")?.asString ?: jsonObject.get("name")?.asString ?: "",
            jsonObject.get("vote_average")?.asDouble ?: 0.0,
            jsonObject.get("overview")?.asString ?: "",
            jsonObject.get("release_date")?.asString ?: jsonObject.get("first_air_date")?.asString ?: ""
        )
    }
}