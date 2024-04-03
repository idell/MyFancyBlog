package idell.projects.blog.crud.update.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.update.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController



@RestController
class MyFancyBlogUpdateController(private val authenticator: MyFancyBlogUserAuthenticator, private val blogPostUpdateUseCase: BlogPostUpdateUseCase) {

    @PutMapping("/v1/posts/update/full")
    fun fullUpdate(@RequestHeader("X-User") user: String,
                   @RequestParam(required = true) id: Int,
                   @RequestBody (required = true) blogPostRequest: BlogPostUpdateRequest): ResponseEntity<PostUpdateResponse> {
        if (!authenticator.isAUser(user)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        if (isTooLong(blogPostRequest.content)){
            return ResponseEntity.badRequest().build()
        }
        val updateResult = blogPostUpdateUseCase.update(BlogPostFullUpdateRequest(BlogPostId(id), blogPostRequest.title, blogPostRequest.content,
                blogPostRequest.author, blogPostRequest.image, blogPostRequest.category, blogPostRequest.tags))

        return when (updateResult){
            is PostUpdateSuccess ->  ResponseEntity.ok(PostUpdateSuccessResponse(updateResult.title,updateResult.content,updateResult.author,updateResult.image,updateResult.category,updateResult.tags))
            is PostNotFound -> ResponseEntity.notFound().build()
            is PostUpdateError -> ResponseEntity.internalServerError().build()
        }

    }
    @PutMapping("/v1/posts/update/")
    fun partialUpdate(@RequestHeader("X-User") user: String,
                   @RequestParam(required = true) id: Int,
                   @RequestBody (required = false) title: String?,
                   @RequestBody (required = false) content: String?,
                   @RequestBody (required = false) author: String?,
                   @RequestBody (required = false) image: String?,
                   @RequestBody (required = false) category: String?,
                   @RequestBody (required = false) tags: List<String>?,
    ): ResponseEntity<PostUpdateResponse> {
        if (!authenticator.isAUser(user)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        if (isTooLong(content?:"")){
            return ResponseEntity.badRequest().build()
        }
        val updateResult = blogPostUpdateUseCase.update(BlogPostPartialUpdateRequest(BlogPostId(id), title, content, author, image, category, tags))

        return when (updateResult){
            is PostUpdateSuccess ->  ResponseEntity.ok(PostUpdateSuccessResponse(updateResult.title,updateResult.content,updateResult.author,updateResult.image,updateResult.category,updateResult.tags))
            is PostNotFound -> ResponseEntity.notFound().build()
            is PostUpdateError -> ResponseEntity.internalServerError().build()
        }

    }

    companion object{
        private const val CONTENT_MAX_LENGTH = 1024
    }

    private fun isTooLong(content: String) =
            content.length > CONTENT_MAX_LENGTH


    data class BlogPostUpdateRequest(val title: String, val content: String, val author: String, val image: String, val category: String, val tags: List<String>)
}