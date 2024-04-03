package idell.projects.blog.crud.update.usecase

sealed class PostUpdateResult
data class PostUpdateSuccess(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>) : PostUpdateResult()
data object PostNotFound : PostUpdateResult()
data object PostUpdateError : PostUpdateResult()