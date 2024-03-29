package idell.projects.blog.create

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.ResponseEntity
import java.net.URI

class MyFancyBlogCrudControllerTest {

    private val blogPostUseCase: BlogPostUseCase = Mockito.mock(BlogPostUseCase::class.java)
    private val underTest = MyFancyBlogCrudController(blogPostUseCase)

    @Test
    fun `will answer 201 if the post has been created successfully`() {

        val actual: ResponseEntity<Any> = underTest.createPost(BlogPost("a title"))
        val expected = ResponseEntity.created(URI.create("")).build<Any>()


        Mockito.verify(blogPostUseCase, times(1)).publish(any())
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}