package com.example.myproject

data class Data_User(
    var uid: String? = "",
    var nick: String? = "",
    var email: String? = "",
    //var body: String? = "",
    //var starCount: Int = 0,
    //var stars: MutableMap<String, Boolean> = HashMap(),
) {

    //@Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "nick" to nick,
            "email" to email,
            //"body" to body,
            //"starCount" to starCount,
            //"stars" to stars,
        )
    }
}