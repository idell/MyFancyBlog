package idell.projects.blog.crud.common

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlogUserAuthenticatorTest{
    private val underTest = BlogUserAuthenticator(listOf("user", "admin"))

    @Test
    fun `is a user`() {
        val actual = underTest.isAUser("user")
        Assertions.assertThat(actual).isTrue()

    }
    @Test
    fun `is not a user`() {
        val actual = underTest.isAUser("a not user")
        Assertions.assertThat(actual).isFalse()

    }

    @Test
    fun `is not an admin`() {
        val actual = underTest.isAnAdmin("user")
        Assertions.assertThat(actual).isFalse()

    }
    @Test
    fun `is an admin`() {
        val actual = underTest.isAnAdmin("admin")
        Assertions.assertThat(actual).isTrue()

    }
}