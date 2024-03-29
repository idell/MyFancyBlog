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
    fun `will answer 400 if the post is longer than 1024 chars`() {


        val actual: ResponseEntity<Any> = underTest.createPost("user", A_TOO_LONG_POST)

        Mockito.verifyNoInteractions(blogPostUseCase)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.badRequest().build<Any>())
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
        private val A_TOO_LONG_POST = BlogPostCreateRequest("anAlreadyPresentPost",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel",
                "an author",
                "anImage",
                "a category",
                listOf("a tag"))
    }
}