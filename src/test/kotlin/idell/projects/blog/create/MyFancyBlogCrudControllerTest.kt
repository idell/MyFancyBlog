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

        Mockito.`when`(blogPostUseCase.publish(A_REQUEST))
                .thenReturn(BlogPostCreated("aUri"))

        val actual: ResponseEntity<Any> = underTest.createPost("user", A_REQUEST)
        val expected = ResponseEntity.created(URI.create("aUri")).build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(A_REQUEST)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 400 if the post is already present`() {

        Mockito.`when`(blogPostUseCase.publish(AN_ALREADY_PRESENT_REQUEST))
                .thenReturn(BlogPostAlreadyPresent)

        val actual: ResponseEntity<Any> = underTest.createPost("user", AN_ALREADY_PRESENT_REQUEST)
        val expected = ResponseEntity.badRequest().build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(AN_ALREADY_PRESENT_REQUEST)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 401 if the user is not recognized`() {

        val actual: ResponseEntity<Any> = underTest.createPost("aNotAutorizedUser", A_REQUEST)
        val expected = ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED)

        Mockito.verifyNoInteractions(blogPostUseCase)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    companion object{
     private val A_REQUEST = BlogPostCreateRequest("aTitle",
             "an amazong blog contente",
             "anAuthor",
             "anImage",
             "aCategory",
             listOf("aTag", "anotherTag"))
    }
    private val AN_ALREADY_PRESENT_REQUEST = BlogPostCreateRequest("anAlreadyPresentPost",
            "some content",
            "an author",
            "anImage",
            "a category",
            listOf("a tag"))
}