package idell.projects.blog.crud.update.usecase

import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.update.controller.PostUpdateResponse

class BlogPostUpdateUseCase(private val repository: BlogPostRepository) {


    fun update(blogPostUpdateRequest: BlogPostUpdateRequest): PostUpdateResult{
        return PostUpdateSuccess("",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
    }


}