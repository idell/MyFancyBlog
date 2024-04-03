package idell.projects.blog.crud

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.InMemoryBlogPostRepository
import idell.projects.blog.crud.create.usecase.BlogPostCreated
import idell.projects.blog.crud.retrieve.usecase.BlogPost
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InMemoryBlogPostRepositoryTest {

    @Test
    fun `will return Created if the post has been saved`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf())
        Assertions.assertThat(inMemoryBlogPostRepository.create(A_SAVE_REQUEST)).isEqualTo(BlogPostCreated)
    }

    @Test
    fun `will retrieve a post by keys if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.search(A_PRESENT_POST_KEY)).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by title if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.search(A_PRESENT_POST_KEY_BY_TITLE)).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by category if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.search(A_PRESENT_POST_KEY_BY_CATEGORY)).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by tags if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.search(A_PRESENT_POST_KEY_BY_TAGS)).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will return an empty result if nothing matches`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.search(A_NOT_PRESENT_POST_KEY)).isEqualTo(emptyList<BlogPost>())
    }

    @Test
    fun `will retrieve a post by id if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.retrieve(A_PRESENT_POST_ID)).isEqualTo(A_SAVED_POST)
    }
    @Test
    fun `will return null if a postId is not present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))
        Assertions.assertThat(inMemoryBlogPostRepository.retrieve(A_NOT_PRESENT_POST_ID)).isNull()
    }

    @Test
    fun `will delete a post`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))

        Assertions.assertThat(inMemoryBlogPostRepository.delete(A_PRESENT_POST_ID)).isEqualTo(A_SAVED_POST)
    }
    @Test
    fun `will return null if post to delete has not been found`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))

        Assertions.assertThat(inMemoryBlogPostRepository.delete(A_NOT_PRESENT_POST_ID)).isNull()
    }
    @Test
    fun `will fully update a post`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_ID, A_SAVED_POST)))

        Assertions.assertThat(inMemoryBlogPostRepository.update(A_PRESENT_POST_ID, AN_UPDATED_POST)).isEqualTo(AN_UPDATED_POST)
    }



    companion object {
        private val A_SAVE_REQUEST = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))

        private val A_PRESENT_POST_KEY = BlogPostKey("aTitle", "aCategory",listOf("aTag","anotherTag"))
        private val A_PRESENT_POST_ID = BlogPostId(1234)
        private val A_PRESENT_POST_KEY_BY_TITLE = BlogPostKey("aTitle", null, null)
        private val A_PRESENT_POST_KEY_BY_CATEGORY = BlogPostKey(null, "aCategory", null)
        private val A_PRESENT_POST_KEY_BY_TAGS = BlogPostKey(null, null, listOf("aTag","anotherTag"))
        private val A_NOT_PRESENT_POST_KEY = BlogPostKey("aNotPresentTitle", "aNotPresentCategory", listOf("aNotPresentTag","anotherNotPresentTag"))
        private val A_NOT_PRESENT_POST_ID = BlogPostId(9876)
        private val A_SAVED_POST = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))
        private val AN_UPDATED_POST = BlogPost(title = "aNewTitle",
                content = "aNewContent",
                author = "aNewAuthor",
                image = "aNewImage",
                category = "aNewCategory",
                tags = listOf("aNewTag","anotherNewTag"))
    }
}