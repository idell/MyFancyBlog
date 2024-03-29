package idell.projects.blog.create

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MyFancyBlogCrudController(private val blogPostUseCase: BlogPostUseCase) {

    @PostMapping("/v1/create/")
    fun createPost(@RequestHeader("X-User") user:String, @RequestBody blogPostRequest: BlogPostCreateRequest): ResponseEntity<Any> {
        if (!ENABLED_USERS.contains(user)){
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        return when (val blogPost = blogPostUseCase.publish(blogPostRequest)) {
            is BlogPostCreated -> ResponseEntity.created(URI.create(blogPost.uri)).build()
            is BlogPostAlreadyPresent -> ResponseEntity.badRequest().build()
        }
    }

    companion object{
        private val ENABLED_USERS = listOf("user","admin")
    }
}

data class BlogPostCreateRequest(val title: String)
