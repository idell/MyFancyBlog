package idell.projects.blog.create

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.ResponseEntity
import java.net.URI

class MyFancyBlogCrudControllerTest {
    private val underTest = MyFancyBlogCrudController()

    @Test
    fun `will answer 201 if the post has been created successfully`() {


        val actual: ResponseEntity<Any> = underTest.createPost(BlogPost("a title"))
        val expected = ResponseEntity.created(URI.create("")).build<Any>()

        Assertions.assertThat(actual).isEqualTo(expected)
    }
}