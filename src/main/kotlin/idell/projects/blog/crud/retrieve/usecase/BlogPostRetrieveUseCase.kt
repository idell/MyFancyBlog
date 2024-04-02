package idell.projects.blog.crud.retrieve.usecase

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository

class BlogPostRetrieveUseCase(private val blogPostRepository: BlogPostRepository) {
    fun retrieve(blogPostRetrieveRequest: BlogPostKey): BlogPosts {
        val blogPostKey = blogPostRetrieveRequest

        val result: List<BlogPost> = blogPostRepository.retrieve(blogPostKey)
        return result
    }



}