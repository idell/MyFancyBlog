package idell.projects.blog.create

class BlogPostUseCase(private val blogPostRepository: BlogPostRepository) {
    fun publish(blogPostRequest: BlogPostCreateRequest) : BlogPostCreateResponse{
        return blogPostRepository.create(blogPostRequest)
    }

}
