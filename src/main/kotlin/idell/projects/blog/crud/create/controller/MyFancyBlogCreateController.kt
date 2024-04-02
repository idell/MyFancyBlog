package idell.projects.blog.crud.create.controller

import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.retrieve.controller.BlogCrudRequestAdapter
import idell.projects.blog.crud.create.usecase.BlogPostCreated
import idell.projects.blog.crud.create.usecase.BlogPostCreationError
import idell.projects.blog.crud.create.usecase.BlogPostUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class MyFancyBlogCreateController(private val blogPostUseCase: BlogPostUseCase,
                                  private val requestAdapter: BlogCrudRequestAdapter,
                                  private val authenticator: MyFancyBlogUserAuthenticator) {

    @PostMapping("/v1/create/")
    fun createPost(@RequestHeader("X-User") user:String,
                   @RequestBody blogPostRequest: BlogPostCreateRequest): ResponseEntity<Any> {
        if (!authenticator.isAUser(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        if (blogPostRequest.content.length > 1024){
            return ResponseEntity.badRequest().build()
        }

        return when (val blogPost = blogPostUseCase.publish(requestAdapter.adapt(blogPostRequest))) {
            is BlogPostCreated -> ResponseEntity.ok().build()
            is BlogPostCreationError -> ResponseEntity.internalServerError().body(blogPost.error)
        }
    }
}

data class BlogPostCreateRequest(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>)
