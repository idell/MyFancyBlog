package idell.projects.blog.crud

import com.nhaarman.mockitokotlin2.times
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.create.usecase.*
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostUseCaseTest {
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val blogPostUseCase = BlogPostUseCase(blogPostRepository)

    @Test
    fun `will invoke repository and answer Created if post has been created `() {
        Mockito.`when`(blogPostRepository.create(A_DOMAIN_REQUEST))
                .thenReturn(BlogPostCreated)

        val response: BlogPostCreateResponse = blogPostUseCase.publish(A_DOMAIN_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(A_DOMAIN_REQUEST)
        Assertions.assertThat(response).isEqualTo(BlogPostCreated)
    }

    @Test
    fun `will return an error if an exception occurs while calling repository `() {
        Mockito.`when`(blogPostRepository.create(A_DOMAIN_REQUEST))
                .thenThrow(RuntimeException("anError occurred"))

        val response: BlogPostCreateResponse = blogPostUseCase.publish(A_DOMAIN_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(A_DOMAIN_REQUEST)

        Assertions.assertThat(response).isEqualTo(BlogPostCreationError("anError occurred"))
    }

    companion object {
        private val A_DOMAIN_REQUEST = BlogPost("aTitle",
                "an amazing blog content",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
    }

}
