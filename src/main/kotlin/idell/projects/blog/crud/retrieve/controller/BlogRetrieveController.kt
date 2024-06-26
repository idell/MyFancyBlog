package idell.projects.blog.crud.retrieve.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogUserAuthenticator
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import idell.projects.blog.crud.retrieve.usecase.BlogPostSearchUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class BlogRetrieveController(private val blogUserAuthenticator: BlogUserAuthenticator, private val blogPostSearchUseCase: BlogPostSearchUseCase) {

    @GetMapping("/v1/posts/search/")
    fun search(@RequestHeader("X-User") user: String,
               @RequestParam(required = false) title: String?,
               @RequestParam(required = false) category: String?,
               @RequestParam(required = false) tags: List<String>?): ResponseEntity<BlogPostRetrieveResponse> {
        if (!blogUserAuthenticator.isAUser(user)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        if (title == null && category == null && tags == null) {
            return ResponseEntity.badRequest().build()
        }
        val retrieve = blogPostSearchUseCase.search(BlogPostKey(title, category, tags))
        return when {
            retrieve.isEmpty() -> {
                ResponseEntity.notFound().build()
            }

            else -> ResponseEntity.ok().body(BlogPostsResponse(retrieve.stream().map { BlogPostResponse(it.title, it.content, it.author, it.image, it.category, it.tags) }.toList()))
        }
    }

    @GetMapping("/v1/posts/{id}")
    fun retrieve(@RequestHeader("X-User") user: String,
                 @PathVariable(required = false) id: Int): ResponseEntity<BlogPostRetrieveResponse> {
        if (!blogUserAuthenticator.isAUser(user)) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        val result: BlogPost? = blogPostSearchUseCase.retrieve(BlogPostId(id))
        return if (result != null) {
            ResponseEntity.ok(BlogPostsResponse(listOf(BlogPostResponse(result.title, result.content, result.author, result.image, result.category, result.tags))))
        } else ResponseEntity.notFound().build()

    }

}