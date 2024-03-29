package idell.projects.blog.crud

class InMemoryBlogPostRepository : BlogPostRepository {

    override fun create(blogPostDomainRequest: BlogPostDomainRequest): BlogPostCreateResponse {

        return if (STORAGE.containsKey(DataRepresentation(blogPostDomainRequest.title,blogPostDomainRequest.category,blogPostDomainRequest.tags))) {
            BlogPostCreationError("An error occured while adding post")
        } else {
            STORAGE[DataRepresentation(blogPostDomainRequest.title,blogPostDomainRequest.category,blogPostDomainRequest.tags)] = blogPostDomainRequest
            BlogPostCreated
        }


    }

    companion object {
        private val STORAGE: MutableMap<DataRepresentation,BlogPostDomainRequest> = mutableMapOf()
    }

    data class DataRepresentation(val title:String,val category:String,val tags:List<String>)
}