package com.examen.hackernews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDateTime

@Entity
data class Articulo(
    @PrimaryKey(autoGenerate = false)
    val objectID: Long,
    val created_at: LocalDateTime,
    val title:String?,
    val url:String?,
    val author:String,
    val points:String?,
    val story_text:String?,
    val comment_text:String?,
    val num_comments:Long?,
    val story_id:Long?,
    val story_title:String?,
    val story_url:String?,
    val parent_id:Long,
    val created_at_i:Long
)


 /*   "_highlightResult":{
        "author":{
            "value":"n8cpdx",
            "matchLevel":"none",
            "matchedWords":[

            ]
        },
        "comment_text":{
            "value":"GrapheneOS has been a dream so far on my used Pixel 4.\u003cp\u003eTurns out \u003cem\u003eAndroi\u003c/em\u003ed can be pleasant when you don't have to deal with oem ui changes and non-optional bloatware.",
            "matchLevel":"full",
            "fullyHighlighted":false,
            "matchedWords":[
            "androi"
            ]
        }
    }*/
