package idell.projects.blog.create

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MyFancyBlogCrudController {

    @PostMapping("/v1/create/")
    fun createPost(@RequestBody blogPost: BlogPost): ResponseEntity<Any> {
        return ResponseEntity.created(URI.create("")).build()
    }
}

data class BlogPost(val title: String)
