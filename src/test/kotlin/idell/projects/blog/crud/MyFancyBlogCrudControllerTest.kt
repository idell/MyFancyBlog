package idell.projects.blog.crud

import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

class MyFancyBlogCrudControllerTest {

    private val blogPostUseCase: BlogPostUseCase = Mockito.mock(BlogPostUseCase::class.java)
    private val blogCrudRequestAdapter: BlogCrudRequestAdapter = Mockito.mock(BlogCrudRequestAdapter::class.java)
    private val underTest = MyFancyBlogCrudController(blogPostUseCase,blogCrudRequestAdapter)

    @Test
    fun `will answer 201 if the post has been created successfully`() {

        Mockito.`when`(blogCrudRequestAdapter.adapt(A_REQUEST))
                .thenReturn(A_DOMAIN_REQUEST)

        Mockito.`when`(blogPostUseCase.publish(A_DOMAIN_REQUEST))
                .thenReturn(BlogPostCreated("anUri"))

        val actual = underTest.createPost("user", A_REQUEST)
        val expected = ResponseEntity.created(URI.create("anUri")).build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(A_DOMAIN_REQUEST)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 400 if the post is already present`() {

        Mockito.`when`(blogCrudRequestAdapter.adapt(AN_ALREADY_PRESENT_POST_REQUEST))
                .thenReturn(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST)

        Mockito.`when`(blogPostUseCase.publish(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST))
                .thenReturn(BlogPostAlreadyPresent("anUri"))

        val actual: ResponseEntity<Any> = underTest.createPost("user", AN_ALREADY_PRESENT_POST_REQUEST)
        val expected = ResponseEntity.badRequest().build<Any>()

        Mockito.verify(blogPostUseCase, times(1)).publish(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST)
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `will answer 401 if the user is not recognized`() {

        val actual: ResponseEntity<Any> = underTest.createPost("aNotAuthorizedUser", A_REQUEST)

        Mockito.verifyNoInteractions(blogPostUseCase)
        Mockito.verifyNoInteractions(blogCrudRequestAdapter)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED))
    }

    @Test
    fun `will answer 500 if the usecase return an error`() {

        Mockito.`when`(blogCrudRequestAdapter.adapt(A_REQUEST))
                .thenReturn(A_DOMAIN_REQUEST)

        Mockito.`when`(blogPostUseCase.publish(A_DOMAIN_REQUEST))
                .thenReturn(BlogPostCreationError("anErrorOccured"))

        val actual: ResponseEntity<Any> = underTest.createPost("user", A_REQUEST)

        Mockito.verify(blogPostUseCase, times(1)).publish(A_DOMAIN_REQUEST)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.internalServerError().body("anErrorOccured"))
    }

    companion object {
        private val A_REQUEST = BlogPostCreateRequest("aTitle",
                "an amazong blog contente",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val A_DOMAIN_REQUEST = BlogPostDomainRequest("aTitle",
                "an amazong blog contente",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val AN_ALREADY_PRESENT_POST_REQUEST = BlogPostCreateRequest("anAlreadyPresentPost",
                "some content",
                "an author",
                "anImage",
                "a category",
                listOf("a tag"))
        private val AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST = BlogPostDomainRequest("anAlreadyPresentPost",
                "some content",
                "an author",
                "anImage",
                "a category",
                listOf("a tag"))
    }
}