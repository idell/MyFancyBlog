package idell.projects.blog.crud.update.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogUserAuthenticator
import idell.projects.blog.crud.common.ContentRestriction.Companion.CONTENT_MAX_LENGTH
import idell.projects.blog.crud.update.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class BlogPostUpdateController(private val authenticator: BlogUserAuthenticator, private val blogPostUpdateUseCase: BlogPostUpdateUseCase) {

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
            is PostUpdateError -> ResponseEntity.internalServerError().build()
        }

    }
    @PutMapping("/v1/posts/update/")
    fun partialUpdate(@RequestHeader("X-User") user: String,
                   @RequestParam(required = true) id: Int,
                   @RequestParam (required = false) title: String?,
                   @RequestParam (required = false) content: String?,
                   @RequestParam (required = false) author: String?,
                   @RequestParam (required = false) image: String?,
                   @RequestParam (required = false) category: String?,
                   @RequestParam (required = false) tags: List<String>?,
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
            is PostUpdateError -> ResponseEntity.internalServerError().build()
        }

    }
    @PutMapping("/v1/posts/update-category/")
    fun categoryUpdate(@RequestHeader("X-User") user: String,
                   @RequestParam (required = true) id: Int,
                   @RequestParam (required = true) category: String,
    ): ResponseEntity<PostUpdateResponse> {
        if (!authenticator.isAUser(user)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        val updateResult = blogPostUpdateUseCase.update(BlogPostCategoryUpdateRequest(BlogPostId(id), category))

        return when (updateResult){
            is PostUpdateSuccess ->  ResponseEntity.ok(PostUpdateSuccessResponse(updateResult.title,updateResult.content,updateResult.author,updateResult.image,updateResult.category,updateResult.tags))
            is PostUpdateError -> ResponseEntity.internalServerError().build()
        }

    }



    private fun isTooLong(content: String) =
            content.length > CONTENT_MAX_LENGTH


    data class BlogPostUpdateRequest(val title: String, val content: String, val author: String, val image: String, val category: String, val tags: List<String>)
}