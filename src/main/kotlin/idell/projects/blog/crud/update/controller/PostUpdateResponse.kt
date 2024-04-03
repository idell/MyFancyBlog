package idell.projects.blog.crud.update.controller

sealed class PostUpdateResponse

data class PostUpdateSuccessResponse(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>) : PostUpdateResponse()