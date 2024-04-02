package idell.projects.blog.crud.common

import idell.projects.blog.crud.create.usecase.BlogPostCreateResponse
import idell.projects.blog.crud.retrieve.usecase.BlogPost

interface BlogPostRepository {
    fun create(blogPost: BlogPost) : BlogPostCreateResponse

    fun retrieve(blogPostKey: BlogPostKey) : List<BlogPost>

    fun delete(blogPost: BlogPost) : BlogPost?


}
