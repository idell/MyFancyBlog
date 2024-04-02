package idell.projects.blog.crud.retrieve.controller

import idell.projects.blog.crud.create.controller.BlogPostCreateRequest
import idell.projects.blog.crud.retrieve.usecase.BlogPost

class BlogCrudRequestAdapter {

    fun adapt(crudRequest: BlogPostCreateRequest): BlogPost = BlogPost(title = crudRequest.title,
            content = crudRequest.content, author = crudRequest.author, image = crudRequest.image,
            category = crudRequest.category, tags = crudRequest.tags)

}
