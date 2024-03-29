package idell.projects.blog.create

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MyFancyBlogCrudController(private val blogPostUseCase: BlogPostUseCase) {

    @PostMapping("/v1/create/")
    fun createPost(@RequestBody blogPostRequest: BlogPostRequest): ResponseEntity<Any> {
        return when (val blogPost = blogPostUseCase.publish(blogPostRequest)) {
            is BlogPostCreated -> ResponseEntity.created(URI.create(blogPost.uri)).build()
            is BlogPostAlreadyPresent -> ResponseEntity.badRequest().build()
        }
    }
}

sealed class BlogPostRequest
data class BlogPostCreateRequest(val title: String) : BlogPostRequest()
