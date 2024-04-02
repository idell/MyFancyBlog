package idell.projects.blog.crud.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.retrieve.controller.*
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import idell.projects.blog.crud.retrieve.usecase.BlogPostRetrieveUseCase
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MyFancyBlogRetrieveControllerTest {
    private val blogPostRetrieveUseCase = Mockito.mock(BlogPostRetrieveUseCase::class.java)

    private val underTest = MyFancyBlogRetrieveController(blogPostRetrieveUseCase)

    @Test
    fun `will return empty result when no post has been found`() {
        `when`(blogPostRetrieveUseCase.retrieve(A_REQUEST))
                .thenReturn(emptyList())
        val retrieve: BlogPostRetrieveResponse = underTest.retrieve("aTitle", null, null)
        Assertions.assertThat(retrieve).isEqualTo(EmptyBlogPostsResponse)
    }

    @Test
    fun `will return a result when a post has been found`() {
        `when`(blogPostRetrieveUseCase.retrieve(A_REQUEST))
                .thenReturn(listOf(A_POST))
        val actual: BlogPostRetrieveResponse = underTest.retrieve("aTitle", null, null)
        Assertions.assertThat(actual).isEqualTo(A_RESPONSE)
    }

    companion object {
        private val A_REQUEST = BlogPostKey("aTitle", null,null)
        private val A_POST = BlogPost("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))
        private val A_RESPONSE = BlogPostsResponse(listOf(BlogPostResponse("aTitle", "aContent", "anAuthor", "anImage", "aCategory", listOf("aTag"))))
    }
}