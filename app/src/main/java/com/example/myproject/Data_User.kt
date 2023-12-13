package com.example.myproject

import android.media.Image
import com.google.firebase.database.Exclude

data class Data_User(
    var uid: String? = "",
    var nick: String? = "",
    var email: String? = "",
    var profilePicture: String? = "",
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
            "profilePicture" to profilePicture,
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
    var votePost: Array<Data_VotePost>?,
    var totalVotes: Int  = 0        ,
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
            "votePost" to votePost,
            "totalVotes" to totalVotes,
            "timeOver" to timeOver,
        )
    }
}

data class Data_VotePost(
    var owner: Data_User?,
    var img: String?,
    var title: String?,
    var description: String?,
    var votes: Int = 0,
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