package idell.projects.blog.crud.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.retrieve.controller.BlogPostResponse
import idell.projects.blog.crud.retrieve.controller.BlogPostRetrieveResponse
import idell.projects.blog.crud.retrieve.controller.BlogPostsResponse
import idell.projects.blog.crud.retrieve.controller.MyFancyBlogRetrieveController
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import idell.projects.blog.crud.retrieve.usecase.BlogPostSearchUseCase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MyFancyBlogRetrieveControllerTest {
    private val blogPostSearchUseCase = Mockito.mock(BlogPostSearchUseCase::class.java)

    private val underTest = MyFancyBlogRetrieveController(MyFancyBlogUserAuthenticator(listOf("aUser")),blogPostSearchUseCase)

    @Test
    fun `will return 400 if user is not enabled`() {
        val result = underTest.search("aNotEnabledUser", null, null, null)
        Mockito.verifyNoInteractions(blogPostSearchUseCase)
        Assertions.assertThat(result).isEqualTo(ResponseEntity<BlogPostRetrieveResponse>(HttpStatus.UNAUTHORIZED))
    }

    @Test
    fun `will return 404 when no post has been found`() {
        `when`(blogPostSearchUseCase.search(A_REQUEST))
                .thenReturn(emptyList())
        val retrieve: ResponseEntity<BlogPostRetrieveResponse> = underTest.search("aUser","aTitle", null, null)
        Assertions.assertThat(retrieve).isEqualTo(ResponseEntity.notFound().build<BlogPostRetrieveResponse>())
    }

    @Test
    fun `will return a result when a post has been found`() {
        `when`(blogPostSearchUseCase.search(A_REQUEST))
                .thenReturn(listOf(A_POST))
        val actual: ResponseEntity<BlogPostRetrieveResponse> = underTest.search("aUser","aTitle", null, null)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok().body(A_RESPONSE))
    }
    @Test
    fun `will return a result when a post has been found 1`() {
        `when`(blogPostSearchUseCase.retrieve(A_POST_ID))
                .thenReturn(A_POST)
        val actual: ResponseEntity<BlogPostRetrieveResponse> = underTest.retrieve("aUser", AN_ID)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok().body(A_RESPONSE))
    }@Test
    fun `will return 404 when a post by id could not be found`() {
        `when`(blogPostSearchUseCase.retrieve(A_POST_ID))
                .thenReturn(null)
        val actual: ResponseEntity<BlogPostRetrieveResponse> = underTest.retrieve("aUser", AN_ID)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.notFound().build<BlogPostRetrieveResponse>())
    }

    companion object {
        private val A_REQUEST = BlogPostKey("aTitle", null,null)
        private val A_POST_ID = BlogPostId(1234)
        private const val AN_ID = 1234
        private val A_POST = BlogPost("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))
        private val A_RESPONSE = BlogPostsResponse(listOf(BlogPostResponse("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))))
    }
}