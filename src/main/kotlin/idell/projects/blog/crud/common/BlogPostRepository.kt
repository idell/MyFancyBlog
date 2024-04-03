package idell.projects.blog.crud.common

import idell.projects.blog.crud.create.usecase.BlogPostCreateResponse
import idell.projects.blog.crud.retrieve.usecase.BlogPost

interface BlogPostRepository {
    fun create(blogPost: BlogPost) : BlogPostCreateResponse
    fun search(blogPostKey: BlogPostKey) : List<BlogPost>
    fun retrieve(blogPostId: BlogPostId) : BlogPost?
    fun delete(blogPostId: BlogPostId): BlogPost?
    fun update(blogPostId: BlogPostId, blogPost: BlogPost) : BlogPost?
}
