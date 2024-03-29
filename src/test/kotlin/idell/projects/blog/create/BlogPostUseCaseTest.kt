package idell.projects.blog.create

import com.nhaarman.mockitokotlin2.times
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostUseCaseTest{
    private val blogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val blogPostUseCase = BlogPostUseCase(blogPostRepository)

    @Test
    fun `will invoke repository and answer with the uri if post has been created `() {
        Mockito.`when`(blogPostRepository.create(BlogPostCreateRequest("a title")))
                .thenReturn(BlogPostCreated("anUri"))

        val response: BlogPostCreateResponse = blogPostUseCase.publish(BlogPostCreateRequest("a title"))

        Mockito.verify(blogPostRepository, times(1)).create(BlogPostCreateRequest("a title"))
        Assertions.assertThat(response).isEqualTo(BlogPostCreated("anUri"))
    }
    @Test
    fun `will invoke repository and answer already present if post is already present `() {
        Mockito.`when`(blogPostRepository.create(BlogPostCreateRequest("anAlreadyPresentPost")))
                .thenReturn(BlogPostAlreadyPresent)

        val response: BlogPostCreateResponse = blogPostUseCase.publish(BlogPostCreateRequest("anAlreadyPresentPost"))

        Mockito.verify(blogPostRepository, times(1)).create(BlogPostCreateRequest("anAlreadyPresentPost"))
        Assertions.assertThat(response).isEqualTo(BlogPostAlreadyPresent)
    }
}