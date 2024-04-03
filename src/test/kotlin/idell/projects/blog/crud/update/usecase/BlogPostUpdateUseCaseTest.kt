package idell.projects.blog.crud.update.usecase

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class BlogPostUpdateUseCaseTest {
    private val repository = Mockito.mock(BlogPostRepository::class.java)
    private val underTest = BlogPostUpdateUseCase(repository)

    @Test
    fun `full update - success`() {
        Mockito.`when`(repository.update(BlogPostId(AN_EXISTING_POST_ID), A_POST)).thenReturn(A_POST)
        val result = underTest.update(AN_EXISTING_POST_FULL_UPDATE_REQUEST)

        Assertions.assertThat(result).isEqualTo(AN_EXISTING_POST_UPDATED)

        Mockito.verify(repository).update(BlogPostId(AN_EXISTING_POST_ID), A_POST)
    }
    @Test
    fun `full update - post not found`() {
        Mockito.`when`(repository.update(BlogPostId(A_NOT_EXISTING_POST_ID), A_POST)).thenReturn(null)
        val result = underTest.update(A_NOT_EXISTING_POST_FULL_UPDATE_REQUEST)

        Assertions.assertThat(result).isEqualTo(PostUpdateError)

        Mockito.verify(repository).update(BlogPostId(A_NOT_EXISTING_POST_ID), A_POST)
    }

    @Test
    fun `partial update - error`() {
        Mockito.`when`(repository.retrieve(BlogPostId(A_NOT_EXISTING_POST_ID))).thenReturn(null)
        Mockito.verifyNoMoreInteractions(repository)
        val result = underTest.update(AN_EXISTING_POST_PARTIAL_UPDATE)

        Assertions.assertThat(result).isEqualTo(PostUpdateError)
    }
    @Test
    fun `category update - success`() {
        Mockito.`when`(repository.retrieve(BlogPostId(AN_EXISTING_POST_ID))).thenReturn(A_POST)
        Mockito.`when`(repository.update(BlogPostId(AN_EXISTING_POST_ID), A_POST_WITH_NEW_CATEGORY)).thenReturn(A_POST_WITH_NEW_CATEGORY)
        val result = underTest.update(AN_EXISTING_POST_CATEGORY_UPDATE_REQUEST)

        Assertions.assertThat(result).isEqualTo(AN_EXISTING_POST_WITH_CATEGORY_UPDATED)

        Mockito.verify(repository).update(BlogPostId(AN_EXISTING_POST_ID), A_POST_WITH_NEW_CATEGORY)
    }
    @Test
    fun `category update - error`() {
        Mockito.`when`(repository.retrieve(BlogPostId(A_NOT_EXISTING_POST_ID))).thenReturn(null)
        Mockito.verifyNoMoreInteractions(repository)

        val result = underTest.update(A_NOT_EXISTING_POST_CATEGORY_UPDATE_REQUEST)

        Assertions.assertThat(result).isEqualTo(PostUpdateError)
    }


    companion object {
        private const val AN_EXISTING_POST_ID = 1234
        private const val A_NOT_EXISTING_POST_ID = 5678
        private val AN_EXISTING_POST_FULL_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(AN_EXISTING_POST_ID), "aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
    private val A_NOT_EXISTING_POST_FULL_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(A_NOT_EXISTING_POST_ID), "aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val AN_EXISTING_POST_CATEGORY_UPDATE_REQUEST = BlogPostCategoryUpdateRequest(BlogPostId(AN_EXISTING_POST_ID),"aNewCategory")
        private val A_NOT_EXISTING_POST_CATEGORY_UPDATE_REQUEST = BlogPostCategoryUpdateRequest(BlogPostId(A_NOT_EXISTING_POST_ID),"aNewCategory")
        private val AN_EXISTING_POST_PARTIAL_UPDATE = BlogPostPartialUpdateRequest(BlogPostId(AN_EXISTING_POST_ID),"aNewTitle", null,null,null,null,null)

        private val AN_EXISTING_POST_UPDATED = PostUpdateSuccess("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val AN_EXISTING_POST_WITH_CATEGORY_UPDATED = PostUpdateSuccess("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aNewCategory",
                listOf("aTag", "anotherTag"))
        private val A_POST = BlogPost("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val A_POST_WITH_NEW_CATEGORY = BlogPost("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aNewCategory",
                listOf("aTag", "anotherTag"))
    }
}