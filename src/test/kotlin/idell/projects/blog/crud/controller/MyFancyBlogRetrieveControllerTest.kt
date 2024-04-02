package idell.projects.blog.crud.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.retrieve.controller.BlogPostResponse
import idell.projects.blog.crud.retrieve.controller.BlogPostRetrieveResponse
import idell.projects.blog.crud.retrieve.controller.BlogPostsResponse
import idell.projects.blog.crud.retrieve.controller.MyFancyBlogRetrieveController
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import idell.projects.blog.crud.retrieve.usecase.BlogPostRetrieveUseCase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MyFancyBlogRetrieveControllerTest {
    private val blogPostRetrieveUseCase = Mockito.mock(BlogPostRetrieveUseCase::class.java)

    private val underTest = MyFancyBlogRetrieveController(MyFancyBlogUserAuthenticator(listOf("aUser")),blogPostRetrieveUseCase)

    @Test
    fun `will return 400 if user is not enabled`() {
        val result = underTest.retrieve("aNotEnabledUser", null, null, null)
        Mockito.verifyNoInteractions(blogPostRetrieveUseCase)
        Assertions.assertThat(result).isEqualTo(ResponseEntity<BlogPostRetrieveResponse>(HttpStatus.UNAUTHORIZED))
    }

    @Test
    fun `will return empty result when no post has been found`() {
        `when`(blogPostRetrieveUseCase.retrieve(A_REQUEST))
                .thenReturn(emptyList())
        val retrieve: ResponseEntity<BlogPostRetrieveResponse> = underTest.retrieve("aUser","aTitle", null, null)
        Assertions.assertThat(retrieve).isEqualTo(ResponseEntity.notFound().build<BlogPostRetrieveResponse>())
    }

    @Test
    fun `will return a result when a post has been found`() {
        `when`(blogPostRetrieveUseCase.retrieve(A_REQUEST))
                .thenReturn(listOf(A_POST))
        val actual: ResponseEntity<BlogPostRetrieveResponse> = underTest.retrieve("aUser","aTitle", null, null)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok().body(A_RESPONSE))
    }

    companion object {
        private val A_REQUEST = BlogPostKey("aTitle", null,null)
        private val A_POST = BlogPost("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))
        private val A_RESPONSE = BlogPostsResponse(listOf(BlogPostResponse("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))))
    }
}