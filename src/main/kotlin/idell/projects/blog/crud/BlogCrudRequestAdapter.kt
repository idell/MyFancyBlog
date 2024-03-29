package idell.projects.blog.crud

class BlogCrudRequestAdapter {

    fun adapt(crudRequest: BlogPostCreateRequest): BlogPostDomainRequest = BlogPostDomainRequest(title = crudRequest.title,
            content = crudRequest.content, author = crudRequest.author, image = crudRequest.image,
            category = crudRequest.category, tags = crudRequest.tags)

}
