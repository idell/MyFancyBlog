package idell.projects.blog.crud.common

import idell.projects.blog.crud.create.usecase.*
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class InMemoryBlogPostRepository(private val storage: MutableMap<BlogPostId, BlogPost>) : BlogPostRepository {

    override fun create(blogPost: BlogPost): BlogPostCreateResponse {
            val lastId = storage.keys.stream().mapToInt { it.id }.max()
            storage[BlogPostId(lastId.orElse(1))] = blogPost
            return BlogPostCreated

    }

    override fun search(blogPostKey: BlogPostKey): List<BlogPost> {
        val mapValues: Map<BlogPostId, BlogPostKey> = storage.mapValues { BlogPostKey(it.value.title, it.value.category, it.value.tags) }
        val allParamtersMatch2: List<BlogPostId> = mapValues.filter { it.value.title == blogPostKey.title || it.value.category == blogPostKey.category || it.value.tags == blogPostKey.tags }.keys.toList().distinct()
        return allParamtersMatch2.mapNotNull { storage[it] }
    }

    override fun retrieve(blogPostId: BlogPostId): BlogPost? = storage[blogPostId]

    override fun delete(blogPostId: BlogPostId) : BlogPost? =
            storage.remove(blogPostId)

    override fun update(blogPostId: BlogPostId,blogPost: BlogPost): BlogPost? {
        TODO("Not yet implemented")
    }

    override fun update(blogPostId: BlogPostId, title: String?, content: String?, author: String?, image: String?, category: String?, tags: List<String>?): BlogPost? {
        TODO("Not yet implemented")
    }

}


