package idell.projects.blog.create

import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostUseCaseTest {
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val blogPostUseCase = BlogPostUseCase(blogPostRepository)

    @Test
    fun `will invoke repository and answer with the uri if post has been created `() {
        Mockito.`when`(blogPostRepository.create(A_REQUEST))
                .thenReturn(BlogPostCreated("anUri"))

        val response: BlogPostCreateResponse = blogPostUseCase.publish(A_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(A_REQUEST)
        Assertions.assertThat(response).isEqualTo(BlogPostCreated("anUri"))
    }

    @Test
    fun `will invoke repository and answer already present if post is already present `() {
        Mockito.`when`(blogPostRepository.create(AN_ALREADY_PRESENT_REQUEST))
                .thenReturn(BlogPostAlreadyPresent)

        val response: BlogPostCreateResponse = blogPostUseCase.publish(AN_ALREADY_PRESENT_REQUEST)

        Mockito.verify(blogPostRepository, times(1)).create(AN_ALREADY_PRESENT_REQUEST)
        Assertions.assertThat(response).isEqualTo(BlogPostAlreadyPresent)
    }

    companion object {
        private val A_REQUEST = BlogPostCreateRequest("aTitle",
                "an amazong blog contente",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
    }

    private val AN_ALREADY_PRESENT_REQUEST = BlogPostCreateRequest("anAlreadyPresentPost",
            "some content",
            "an author",
            "anImage",
            "a category",
            listOf("a tag"))
}
