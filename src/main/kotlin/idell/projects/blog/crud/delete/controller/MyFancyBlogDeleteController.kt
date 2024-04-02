package idell.projects.blog.crud.delete.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.delete.usecase.MyFancyBlogDeleteUseCase
import idell.projects.blog.crud.delete.usecase.NoPostDeleted
import idell.projects.blog.crud.delete.usecase.PostDeleted
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MyFancyBlogDeleteController(private val authenticator: MyFancyBlogUserAuthenticator, private val myFancyBlogDeleteUseCase: MyFancyBlogDeleteUseCase) {

    @DeleteMapping("/v1/posts/")
    fun delete(@RequestHeader("X-User") user:String,
               @RequestParam(required = false) title: String?,
               @RequestParam(required = false) category: String?,
               @RequestParam(required = false) tags: List<String>?):ResponseEntity<Any>{

        if (!authenticator.isAnAdmin(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        return when(myFancyBlogDeleteUseCase.delete(BlogPostKey(title, category, tags))){
            is NoPostDeleted -> ResponseEntity.notFound().build()
            is PostDeleted -> ResponseEntity.noContent().build()
        }
    }



}