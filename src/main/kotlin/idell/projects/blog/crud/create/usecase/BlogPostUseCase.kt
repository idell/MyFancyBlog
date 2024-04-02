package idell.projects.blog.crud.create.usecase

import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class BlogPostUseCase(private val blogPostRepository: BlogPostRepository) {

    fun publish(blogPost: BlogPost) : BlogPostCreateResponse {
        return try {
            blogPostRepository.create(blogPost)
        } catch (e: Exception) {
            return BlogPostCreationError(e.message?:"Repository error while saving post [$blogPost]")
        }
    }

}
