package idell.projects.blog.crud.common

import idell.projects.blog.crud.create.usecase.*
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import kotlin.streams.toList

class InMemoryBlogPostRepository(private val storage : MutableMap<BlogPostKey, BlogPost>) : BlogPostRepository {

    override fun create(blogPost: BlogPost): BlogPostCreateResponse {

        return if (storage.containsKey(BlogPostKey(blogPost.title,blogPost.category,blogPost.tags))) {
            BlogPostCreationError("An error occured while adding post")
        } else {
            storage[BlogPostKey(blogPost.title,blogPost.category,blogPost.tags)] = blogPost
            BlogPostCreated
        }

    }

    override fun retrieve(blogPostKey: BlogPostKey): List<BlogPost> {
        val keys = storage.keys
        val allParametersMatch = keys.stream().filter{
            it.title == blogPostKey.title && it.category == blogPostKey.category && it.tags == blogPostKey.tags }.map { storage[it] }.toList().filterNotNull()
        val titleParameterMatch = keys.stream().filter { it.title == blogPostKey.title}.map { storage[it] }.toList().filterNotNull()
        val categoryParameterMatch = keys.stream().filter { it.category == blogPostKey.category}.map { storage[it] }.toList().filterNotNull()
        val tagsParameterMatch = keys.stream().filter { it.tags == blogPostKey.tags}.map { storage[it] }.toList().filterNotNull()
        val blogPosts = allParametersMatch + titleParameterMatch + categoryParameterMatch + tagsParameterMatch
        return blogPosts.distinct()


    }

    override fun delete(blogPost: BlogPost) {

    }

}


