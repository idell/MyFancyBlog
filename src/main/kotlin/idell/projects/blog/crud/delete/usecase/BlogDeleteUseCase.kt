package idell.projects.blog.crud.delete.usecase

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class BlogDeleteUseCase(private val repository: BlogPostRepository) {

    fun delete(blogPostId: BlogPostId) : DeleteResult {
        val deletedPost: BlogPost? = repository.delete(blogPostId)
        return if (deletedPost!=null) {
            PostDeleted
        } else {
            NoPostDeleted
        }

    }

}
