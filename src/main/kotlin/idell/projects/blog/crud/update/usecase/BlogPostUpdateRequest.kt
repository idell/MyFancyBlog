package idell.projects.blog.crud.update.usecase

import idell.projects.blog.crud.common.BlogPostId

sealed class BlogPostUpdateRequest

data class BlogPostFullUpdateRequest(val postId: BlogPostId,
                                     val title: String,
                                     val content: String,
                                     val author: String,
                                     val image: String,
                                     val category: String,
                                     val tags: List<String>) : BlogPostUpdateRequest()
data class BlogPostPartialUpdateRequest(val postId: BlogPostId,
                                     val title: String?,
                                     val content: String?,
                                     val author: String?,
                                     val image: String?,
                                     val category: String?,
                                     val tags: List<String>?) : BlogPostUpdateRequest()