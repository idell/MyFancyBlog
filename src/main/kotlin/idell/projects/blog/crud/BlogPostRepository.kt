package idell.projects.blog.crud

interface BlogPostRepository {
    fun create(blogPostDomainRequest: BlogPostDomainRequest) : BlogPostCreateResponse


}
