package idell.projects.blog.crud

class BlogPostUseCase(private val blogPostRepository: BlogPostRepository) {

    fun publish(blogPostDomainRequest: BlogPostDomainRequest) : BlogPostCreateResponse{
        return try {
            blogPostRepository.create(blogPostDomainRequest)
        } catch (e: Exception) {
            return BlogPostCreationError(e.message?:"Repository error while saving post [$blogPostDomainRequest]")
        }
    }

}
