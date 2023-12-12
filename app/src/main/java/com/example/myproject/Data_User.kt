package com.example.myproject

import android.media.Image
import com.google.firebase.database.Exclude

data class Data_User(
    var uid: String? = "",
    var nick: String? = "",
    var email: String? = "",
    //var body: String? = "",
    //var starCount: Int = 0,
    //var stars: MutableMap<String, Boolean> = HashMap(),
)
{
    @Exclude
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


data class Data_Post(
    var owner: Data_User?           ,
    var title: String?              ,
    var description: String?        ,
    var guests: Array<Data_User>?   ,
    var guestPost: Array<Data_GuestPost>?     ,
    var totalVotes: Int             ,
    var timeOver: Long              ,//tempo para finalizar votos
    )
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "title" to title,
            "description" to description,
            "guests" to guests,
            "guestPost" to guestPost,
            "totalVotes" to totalVotes,
            "timeOver" to timeOver,
        )
    }
}

data class Data_GuestPost(
    var owner: Data_User?,
    var img: Image?,
    var title: String?,
    var description: String?,
    var votes: Int?,
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "owner" to owner,
            "img" to img,
            "title" to title,
            "description" to description,
            "votes" to votes,
        )
    }
}