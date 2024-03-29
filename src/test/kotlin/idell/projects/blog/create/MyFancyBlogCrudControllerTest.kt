package idell.projects.blog.create

import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

class MyFancyBlogCrudControllerTest {

    private val blogPostUseCase: BlogPostUseCase = Mockito.mock(BlogPostUseCase::class.java)
    private val underTest = MyFancyBlogCrudController(blogPostUseCase)

    @Test
    fun `will answer 201 if the post has been created successfully`() {

        Mockito.`when`(blogPostUseCase.publish(BlogPostCreateRequest("aTitle")))
                .thenReturn(BlogPostCreated("aUri"))

        val actual: ResponseEntity<Any> = underTest.createPost("user", BlogPostCreateRequest("aTitle"))
        val expected = ResponseEntity.created(URI.create("aUri")).build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(BlogPostCreateRequest("aTitle"))
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 400 if the post is already present`() {

        Mockito.`when`(blogPostUseCase.publish(BlogPostCreateRequest("anAlreadyPresentPost")))
                .thenReturn(BlogPostAlreadyPresent)

        val actual: ResponseEntity<Any> = underTest.createPost("user", BlogPostCreateRequest("anAlreadyPresentPost"))
        val expected = ResponseEntity.badRequest().build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(BlogPostCreateRequest("anAlreadyPresentPost"))
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 401 if the user is not recognized`() {

        val actual: ResponseEntity<Any> = underTest.createPost("aNotAutorizedUser", BlogPostCreateRequest("aTitle"))
        val expected = ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED)

        Mockito.verifyNoInteractions(blogPostUseCase)
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}