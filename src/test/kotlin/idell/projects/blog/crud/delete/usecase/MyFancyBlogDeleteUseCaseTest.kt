package idell.projects.blog.crud.delete.usecase

import com.nhaarman.mockitokotlin2.times
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class MyFancyBlogDeleteUseCaseTest {
    private val repository: BlogPostRepository = Mockito.mock(BlogPostRepository::class.java)

    private val underTest = MyFancyBlogDeleteUseCase(repository)

    @Test
    fun `delete a found post`() {
        Mockito.`when`(repository.retrieve(A_POST_KEY)).thenReturn(listOf(A_POST_TO_DELETE))
        val result = underTest.delete(A_POST_KEY)

        Assertions.assertThat(result).isEqualTo(PostDeleted)

        Mockito.verify(repository).delete(A_POST_TO_DELETE)
    }

    @Test
    fun `no delete because multiple posts found`() {
        Mockito.`when`(repository.retrieve(A_POST_KEY)).thenReturn(listOf(A_POST_TO_DELETE, ANOTHER_POST))

        val result = underTest.delete(A_POST_KEY)

        Assertions.assertThat(result).isEqualTo(NoPostDeleted)

        Mockito.verify(repository, times(0)).delete(A_POST_TO_DELETE)
        Mockito.verify(repository, times(0)).delete(ANOTHER_POST)
    }

    @Test
    fun `no post to delete found`() {
        Mockito.`when`(repository.retrieve(A_POST_KEY)).thenReturn(emptyList())

        val result = underTest.delete(A_POST_KEY)

        Assertions.assertThat(result).isEqualTo(NoPostDeleted)
    }

    companion object {
        private val A_POST_KEY = BlogPostKey("aTitle", null, null)
        private val A_POST_TO_DELETE = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag", "anotherTag"))
        private val ANOTHER_POST = BlogPost(title = "anotherTitle",
                content = "anotherContent",
                author = "anotherAuthor",
                image = "anotherImage",
                category = "anotherCategory",
                tags = listOf("anotherTag", "anotherOneTag"))
    }


}