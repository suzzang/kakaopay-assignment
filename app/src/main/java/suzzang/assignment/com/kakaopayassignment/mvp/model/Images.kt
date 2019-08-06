package suzzang.assignment.com.kakaopayassignment.mvp.model

import java.io.Serializable

data class Images (
    val thumbnail_url : String,
    val collection: String,
    val display_sitename : String?,
    val doc_url : String,
    val image_url: String,
    val height:Int,
    val width:Int,
    val datetime: String
) : Serializable

