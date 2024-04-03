package idell.projects.blog.crud.retrieve.usecase

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository

class BlogPostSearchUseCase(private val blogPostRepository: BlogPostRepository) {
    fun search(blogPostRetrieveRequest: BlogPostKey): BlogPosts {

        val result: List<BlogPost> = blogPostRepository.search(blogPostRetrieveRequest)
        return result
    }
    fun retrieve(blogPostId: BlogPostId): BlogPost? {
        return blogPostRepository.retrieve(blogPostId)
    }



}