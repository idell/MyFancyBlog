package idell.projects.blog.crud.update.usecase

import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class BlogPostUpdateUseCase(private val repository: BlogPostRepository) {

    fun update(blogPostUpdateRequest: BlogPostUpdateRequest): PostUpdateResult{

        return when(blogPostUpdateRequest){
            is BlogPostFullUpdateRequest -> fullUpdate(blogPostUpdateRequest)
            is BlogPostPartialUpdateRequest -> partialUpdate(blogPostUpdateRequest)
            is BlogPostCategoryUpdateRequest -> categoryUpdate(blogPostUpdateRequest)
        }
    }

    private fun categoryUpdate(blogPostUpdateRequest: BlogPostCategoryUpdateRequest): PostUpdateResult {
        val previousPostVersion: BlogPost = repository.retrieve(blogPostUpdateRequest.postId) ?: return PostUpdateError
        val updatedPost = previousPostVersion.copy(category = blogPostUpdateRequest.category)
        repository.update(blogPostUpdateRequest.postId, updatedPost)
        return PostUpdateSuccess(updatedPost.title,updatedPost.content,updatedPost.author,updatedPost.image,updatedPost.category,updatedPost.tags)
    }

    private fun partialUpdate(blogPostUpdateRequest: BlogPostPartialUpdateRequest): PostUpdateResult {
        val previousPostVersion: BlogPost = repository.retrieve(blogPostUpdateRequest.postId) ?: return PostUpdateError
        var author = previousPostVersion.author
        var image = previousPostVersion.image
        var category = previousPostVersion.category
        var tags = previousPostVersion.tags

        if (blogPostUpdateRequest.author!=null){
            author = blogPostUpdateRequest.author
        }
        if (blogPostUpdateRequest.image!=null){
            image=blogPostUpdateRequest.image
        }
        if (blogPostUpdateRequest.category!=null){
            category=blogPostUpdateRequest.category
        }
        if (blogPostUpdateRequest.tags!=null){
            tags=blogPostUpdateRequest.tags
        }
        val title=blogPostUpdateRequest.title?:previousPostVersion.title
        val content = blogPostUpdateRequest.content?:previousPostVersion.title
        repository.update(blogPostUpdateRequest.postId, BlogPost(title, content, author, image, category, tags))
        return PostUpdateSuccess(title, content, author, image, category, tags)

    }

    private fun fullUpdate(blogPostUpdateRequest: BlogPostFullUpdateRequest): PostUpdateResult {
        val result = repository.update(blogPostUpdateRequest.postId,
                BlogPost(blogPostUpdateRequest.title,
                        blogPostUpdateRequest.content,
                        blogPostUpdateRequest.author,
                        blogPostUpdateRequest.image,
                        blogPostUpdateRequest.category,
                        blogPostUpdateRequest.tags))
        return if (result != null) {
            PostUpdateSuccess(result.title, result.content, result.author, result.image, result.category, result.tags)
        } else PostUpdateError
    }


}