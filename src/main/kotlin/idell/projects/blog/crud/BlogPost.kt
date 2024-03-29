package idell.projects.blog.crud

sealed class BlogPostCreateResponse
data class BlogPostCreated(val uri: String) : BlogPostCreateResponse()
data class BlogPostCreationError(val error:String) : BlogPostCreateResponse()
