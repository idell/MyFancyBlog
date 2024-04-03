package idell.projects.blog.crud.delete.controller

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogUserAuthenticator
import idell.projects.blog.crud.delete.usecase.BlogDeleteUseCase
import idell.projects.blog.crud.delete.usecase.PostDeleted
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BlogPostDeleteControllerTest {
    private val blogDeleteUseCase: BlogDeleteUseCase = Mockito.mock(BlogDeleteUseCase::class.java)
    private val underTest = BlogPostDeleteController(BlogUserAuthenticator(listOf("user", "admin")), blogDeleteUseCase)

    @Test
    fun `not authorized user`() {
        val actual = underTest.delete("user", 1234)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(blogDeleteUseCase)
    }
    @Test
    fun `unknown user`() {
        val actual = underTest.delete("unknown-user", 1234)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(blogDeleteUseCase)
    }

    @Test
    fun `delete an existing post`() {
        Mockito.`when`(blogDeleteUseCase.delete(BlogPostId(1234))).thenReturn(PostDeleted)

        val actual = underTest.delete("admin", 1234)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity.noContent().build<Any>())
        Mockito.verify(blogDeleteUseCase).delete(BlogPostId(1234))
    }
}