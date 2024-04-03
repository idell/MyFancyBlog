package idell.projects.blog.crud.update.usecase

import idell.projects.blog.crud.common.BlogPostRepository

class BlogPostUpdateUseCase(private val repository: BlogPostRepository) {


    fun update(blogPostUpdateRequest: BlogPostUpdateRequest): PostUpdateResult{
        return PostUpdateSuccess
    }


}