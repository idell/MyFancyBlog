package idell.projects.blog.crud

class BlogPostUseCase(private val blogPostRepository: BlogPostRepository) {

    fun publish(blogPostDomainRequest: BlogPostDomainRequest) : BlogPostCreateResponse{
        return blogPostRepository.create(blogPostDomainRequest)
    }

}
