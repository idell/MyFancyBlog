package idell.projects.blog.crud.retrieve.controller

sealed class BlogPostRetrieveResponse
data class BlogPostsResponse(val blogPosts: List<BlogPostResponse>) : BlogPostRetrieveResponse()
data object EmptyBlogPostsResponse : BlogPostRetrieveResponse()
data class BlogPostResponse(val title: String, val content: String, val author: String, val image: String, val category: String, val tags: List<String>)