package idell.projects.blog.crud.delete.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.delete.usecase.BlogDeleteUseCase
import idell.projects.blog.crud.delete.usecase.NoPostDeleted
import idell.projects.blog.crud.delete.usecase.PostDeleted
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MyFancyBlogDeleteController(private val authenticator: MyFancyBlogUserAuthenticator, private val blogDeleteUseCase: BlogDeleteUseCase) {

    @DeleteMapping("/v1/posts/")
    fun delete(@RequestHeader("X-User") user: String,
               @RequestParam(required = true) blogPostId: Int):ResponseEntity<Any>{

        if (!authenticator.isAnAdmin(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        return when(blogDeleteUseCase.delete(BlogPostId(blogPostId))){
            is NoPostDeleted -> ResponseEntity.notFound().build()
            is PostDeleted -> ResponseEntity.noContent().build()
        }
    }



}