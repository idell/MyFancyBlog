package idell.projects.blog.crud.delete.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogUserAuthenticator
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
class BlogPostDeleteController(private val authenticator: BlogUserAuthenticator, private val blogDeleteUseCase: BlogDeleteUseCase) {

    @DeleteMapping("/v1/posts/")
    fun delete(@RequestHeader("X-User") user: String,
               @RequestParam(required = true) id: Int):ResponseEntity<Any>{

        if (!authenticator.isAnAdmin(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        return when(blogDeleteUseCase.delete(BlogPostId(id))){
            is NoPostDeleted -> ResponseEntity.notFound().build()
            is PostDeleted -> ResponseEntity.noContent().build()
        }
    }



}