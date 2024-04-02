package idell.projects.blog.crud.retrieve.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.retrieve.usecase.BlogPostRetrieveUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MyFancyBlogRetrieveController(private val blogPostRetrieveUseCase: BlogPostRetrieveUseCase) {

    @GetMapping("/v1/posts/")
    fun retrieve(@RequestParam(required = false) title: String?,
                 @RequestParam(required = false) category: String?,
                 @RequestParam(required = false) tags: List<String>?): ResponseEntity<BlogPostRetrieveResponse> {

        if (title == null && category == null && tags == null) {
            return ResponseEntity.badRequest().build()
        }
        val retrieve = blogPostRetrieveUseCase.retrieve(BlogPostKey(title, category, tags))
        return when {
            retrieve.isEmpty() -> {
                ResponseEntity.notFound().build()
            }

            else -> ResponseEntity.ok().body(BlogPostsResponse(retrieve.stream().map { BlogPostResponse(it.title, it.content, it.author, it.image, it.category, it.tags) }.toList()))
        }
    }

}