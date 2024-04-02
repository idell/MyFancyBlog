package idell.projects.blog.crud.delete.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.delete.usecase.MyFancyBlogDeleteUseCase
import idell.projects.blog.crud.delete.usecase.PostDeleted
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MyFancyBlogDeleteControllerTest {
    private val myFancyBlogDeleteUseCase: MyFancyBlogDeleteUseCase = Mockito.mock(MyFancyBlogDeleteUseCase::class.java)
    private val underTest = MyFancyBlogDeleteController(MyFancyBlogUserAuthenticator(listOf("user", "admin")), myFancyBlogDeleteUseCase)

    @Test
    fun `not authorized user`() {
        val actual = underTest.delete("user", "aTitle", null, null)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(myFancyBlogDeleteUseCase)
    }
    @Test
    fun `unknown user`() {
        val actual = underTest.delete("unknown-user", "aTitle", null, null)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(myFancyBlogDeleteUseCase)
    }

    @Test
    fun `delete an existing post`() {
        Mockito.`when`(myFancyBlogDeleteUseCase.delete(BlogPostKey("aTitle", null, null))).thenReturn(PostDeleted)

        val actual = underTest.delete("admin", "aTitle", null, null)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity.noContent().build<Any>())
        Mockito.verify(myFancyBlogDeleteUseCase).delete(BlogPostKey("aTitle", null, null))
    }
}