package idell.projects.blog.crud

sealed class BlogPostCreateResponse
data object BlogPostCreated : BlogPostCreateResponse()
data class BlogPostCreationError(val error:String) : BlogPostCreateResponse()
