package idell.projects.blog.crud.retrieve.usecase

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*

class BlogPostSearchUseCaseTest{
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val underTest = BlogPostSearchUseCase(blogPostRepository)

    @Test
    fun `will return the result of the repository`() {
        Mockito.`when`(blogPostRepository.search(A_REQUEST)).thenReturn(A_RESULT)

        val actual = underTest.search(A_REQUEST)
        Mockito.verify(blogPostRepository).search(A_REQUEST)

        Assertions.assertThat(actual).isEqualTo(listOf(BlogPost("aTitle","aContent","anAuthor","anImage","aCategory", listOf("aTag", "anotherTag"))))

    }

    @Test
    fun `will return an empty response if the result of the repository is empty`() {
        Mockito.`when`(blogPostRepository.search(A_REQUEST)).thenReturn(emptyList())

        val actual = underTest.search(A_REQUEST)
        Mockito.verify(blogPostRepository).search(A_REQUEST)

        Assertions.assertThat(actual).isEqualTo(emptyList<BlogPosts>())

    }
        @Test
        fun `will return null if the result of the repository null`() {
        Mockito.`when`(blogPostRepository.retrieve(A_POST_ID)).thenReturn(null)

        val actual = underTest.retrieve(A_POST_ID)
        Mockito.verify(blogPostRepository).retrieve(A_POST_ID)

        Assertions.assertThat(actual).isEqualTo(null)
    }
    @Test
        fun `will return a blog post if the repository found a post`() {
        Mockito.`when`(blogPostRepository.retrieve(A_POST_ID)).thenReturn(A_BLOG_POST)

        val actual = underTest.retrieve(A_POST_ID)
        Mockito.verify(blogPostRepository).retrieve(A_POST_ID)

        Assertions.assertThat(actual).isEqualTo(A_BLOG_POST)
    }


    companion object{
        private val A_REQUEST = BlogPostKey("aTitle","aCategory", null)
        private val A_POST_ID = BlogPostId(1234)
        private val A_RESULT = listOf(BlogPost("aTitle","aContent","anAuthor","anImage","aCategory", listOf("aTag", "anotherTag")))
        private val A_BLOG_POST = BlogPost("aTitle","aContent","anAuthor","anImage","aCategory", listOf("aTag", "anotherTag"))
    }
}