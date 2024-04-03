package idell.projects.blog.crud.create.controller

import idell.projects.blog.crud.common.BlogUserAuthenticator
import idell.projects.blog.crud.common.ContentRestriction.Companion.CONTENT_MAX_LENGTH
import idell.projects.blog.crud.create.usecase.BlogPostCreated
import idell.projects.blog.crud.create.usecase.BlogPostCreationError
import idell.projects.blog.crud.create.usecase.BlogPostUseCase
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class BlogPostCreateController(private val blogPostUseCase: BlogPostUseCase,
                               private val authenticator: BlogUserAuthenticator) {

    @PostMapping("/v1/posts/create/")
    fun createPost(@RequestHeader("X-User") user:String,
                   @RequestBody blogPostRequest: BlogPostCreateRequest): ResponseEntity<Any> {
        if (!authenticator.isAUser(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        if (blogPostRequest.content.length > CONTENT_MAX_LENGTH){
            return ResponseEntity.badRequest().build()
        }

        return when (val blogPost = blogPostUseCase.publish(BlogPost(title = blogPostRequest.title,
                content = blogPostRequest.content, author = blogPostRequest.author, image = blogPostRequest.image,
                category = blogPostRequest.category, tags = blogPostRequest.tags))) {
            is BlogPostCreated -> ResponseEntity.ok().build()
            is BlogPostCreationError -> ResponseEntity.internalServerError().body(blogPost.error)
        }
    }
}

data class BlogPostCreateRequest(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>)
