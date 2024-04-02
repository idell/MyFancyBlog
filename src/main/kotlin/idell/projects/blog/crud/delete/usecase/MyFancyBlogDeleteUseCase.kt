package idell.projects.blog.crud.delete.usecase

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class MyFancyBlogDeleteUseCase(private val repository: BlogPostRepository) {

    fun delete(blogPostKey: BlogPostKey) : DeleteResult {
        val blogPosts: List<BlogPost> = repository.retrieve(blogPostKey)
        if (blogPosts.size != 1){
            return NoPostDeleted
        }
        repository.delete(blogPosts.first())
        return PostDeleted
    }

}
