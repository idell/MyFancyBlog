package idell.projects.blog.create

sealed class BlogPostCreateResponse
data class BlogPostCreated(val uri: String) : BlogPostCreateResponse()

data object BlogPostAlreadyPresent : BlogPostCreateResponse()
