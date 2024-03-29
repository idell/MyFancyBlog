package idell.projects.blog.crud

import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostUseCaseTest {
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val blogPostUseCase = BlogPostUseCase(blogPostRepository)

    @Test
    fun `will invoke repository and answer with the uri if post has been created `() {
        Mockito.`when`(blogPostRepository.create(A_DOMAIN_REQUEST))
                .thenReturn(BlogPostCreated("anUri"))

        val response: BlogPostCreateResponse = blogPostUseCase.publish(A_DOMAIN_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(A_DOMAIN_REQUEST)
        Assertions.assertThat(response).isEqualTo(BlogPostCreated("anUri"))
    }

    @Test
    fun `will invoke repository and answer already present if post is already present `() {
        Mockito.`when`(blogPostRepository.create(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST))
                .thenReturn(BlogPostAlreadyPresent("anUri"))

        val response: BlogPostCreateResponse = blogPostUseCase.publish(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST)
        Assertions.assertThat(response).isEqualTo(BlogPostAlreadyPresent("anUri"))
    }

    companion object {
        private val A_DOMAIN_REQUEST = BlogPostDomainRequest("aTitle",
                "an amazong blog contente",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val AN_ALREADY_PRESENT_POST_DOMAIN_REQUEST = BlogPostDomainRequest("anAlreadyPresentPost",
                "some content",
                "an author",
                "anImage",
                "a category",
                listOf("a tag"))
    }

}
