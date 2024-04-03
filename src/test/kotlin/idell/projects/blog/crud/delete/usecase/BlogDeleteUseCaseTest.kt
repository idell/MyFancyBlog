package idell.projects.blog.crud.delete.usecase

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogDeleteUseCaseTest {
    private val repository: BlogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val underTest = BlogDeleteUseCase(repository)

    @Test
    fun `delete a found post`() {
        Mockito.`when`(repository.delete(A_POST_ID)).thenReturn(A_POST_TO_DELETE)
        val result = underTest.delete(A_POST_ID)

        Assertions.assertThat(result).isEqualTo(PostDeleted)

        Mockito.verify(repository).delete(A_POST_ID)
    }

    @Test
    fun `no post to delete found`() {
        Mockito.`when`(repository.retrieve(A_POST_ID)).thenReturn(null)

        val result = underTest.delete(A_POST_ID)

        Assertions.assertThat(result).isEqualTo(NoPostDeleted)
    }

    companion object {
        private val A_POST_ID = BlogPostId(1234)
        private val A_POST_TO_DELETE = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag", "anotherTag"))
    }


}