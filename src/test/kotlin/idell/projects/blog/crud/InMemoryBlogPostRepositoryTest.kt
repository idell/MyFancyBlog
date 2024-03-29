package idell.projects.blog.crud

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InMemoryBlogPostRepositoryTest {


    private val inMemoryBlogPostRepository = InMemoryBlogPostRepository()

    @Test
    fun `will return Created if the post has been saved`() {
        Assertions.assertThat(inMemoryBlogPostRepository.create(A_REQUEST)).isEqualTo(BlogPostCreated)
    }

    @Test
    fun `will return an error if the post hasn't been saved`() {
        inMemoryBlogPostRepository.create(ANOTHER_REQUEST)
        Assertions.assertThat(inMemoryBlogPostRepository.create(ANOTHER_REQUEST)).isEqualTo(BlogPostCreationError("An error occured while adding post"))
    }

    companion object {
        private val A_REQUEST = BlogPostDomainRequest(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))

        private val ANOTHER_REQUEST = BlogPostDomainRequest(title = "anotherTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))
    }
}