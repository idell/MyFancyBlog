package idell.projects.blog.crud.retrieve.usecase

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostRetrieveUseCaseTest{
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val underTest = BlogPostRetrieveUseCase(blogPostRepository)

    @Test
    fun `will return the result of the repository`() {
        Mockito.`when`(blogPostRepository.retrieve(A_REQUEST)).thenReturn(A_RESULT)

        val actual = underTest.retrieve(A_REQUEST)
        Mockito.verify(blogPostRepository).retrieve(A_REQUEST)

        Assertions.assertThat(actual).isEqualTo(listOf(BlogPost("aTitle","aContent","anAuthor","anImage","aCategory", listOf("aTag", "anotherTag"))))

    }

    @Test
    fun `will an empty response if the result of the repository is empty`() {
        Mockito.`when`(blogPostRepository.retrieve(A_REQUEST)).thenReturn(emptyList())

        val actual = underTest.retrieve(A_REQUEST)
        Mockito.verify(blogPostRepository).retrieve(A_REQUEST)

        Assertions.assertThat(actual).isEqualTo(emptyList<BlogPosts>())

    }


    companion object{
        private val A_REQUEST = BlogPostKey("aTitle","aCategory", null)
        private val A_RESULT = listOf(BlogPost("aTitle","aContent","anAuthor","anImage","aCategory", listOf("aTag", "anotherTag")))
    }
}