package idell.projects.blog.crud

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.common.InMemoryBlogPostRepository
import idell.projects.blog.crud.create.usecase.BlogPostCreated
import idell.projects.blog.crud.create.usecase.BlogPostCreationError
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
    fun `will return an error if the post hasn't been saved`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf())
        inMemoryBlogPostRepository.create(ANOTHER_SAVE_REQUEST)
        Assertions.assertThat(inMemoryBlogPostRepository.create(ANOTHER_SAVE_REQUEST)).isEqualTo(BlogPostCreationError("An error occured while adding post"))
    }

    @Test
    fun `will retrieve a post by keys if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_KEY, A_SAVED_POST)))
        val actual = inMemoryBlogPostRepository.retrieve(A_PRESENT_POST_KEY)
        Assertions.assertThat(actual).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by title if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_KEY, A_SAVED_POST)))
        val actual = inMemoryBlogPostRepository.retrieve(A_PRESENT_POST_KEY_BY_TITLE)
        Assertions.assertThat(actual).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by category if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_KEY, A_SAVED_POST)))
        val actual = inMemoryBlogPostRepository.retrieve(A_PRESENT_POST_KEY_BY_CATEGORY)
        Assertions.assertThat(actual).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will retrieve a post by tags if is present`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_KEY, A_SAVED_POST)))
        val actual = inMemoryBlogPostRepository.retrieve(A_PRESENT_POST_KEY_BY_TAGS)
        Assertions.assertThat(actual).isEqualTo(listOf(A_SAVED_POST))
    }
    @Test
    fun `will return an empty result if nothing matches`() {
        val inMemoryBlogPostRepository = InMemoryBlogPostRepository(mutableMapOf(Pair(A_PRESENT_POST_KEY, A_SAVED_POST)))
        val actual = inMemoryBlogPostRepository.retrieve(A_NOT_PRESENT_POST_KEY)
        Assertions.assertThat(actual).isEqualTo(emptyList<BlogPost>())
    }

    companion object {
        private val A_SAVE_REQUEST = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))

        private val ANOTHER_SAVE_REQUEST = BlogPost(title = "anotherTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))

        private val A_PRESENT_POST_KEY = BlogPostKey("aTitle", "aCategory",listOf("aTag","anotherTag"))
        private val A_PRESENT_POST_KEY_BY_TITLE = BlogPostKey("aTitle", null, null)
        private val A_PRESENT_POST_KEY_BY_CATEGORY = BlogPostKey(null, "aCategory", null)
        private val A_PRESENT_POST_KEY_BY_TAGS = BlogPostKey(null, null, listOf("aTag","anotherTag"))
        private val A_NOT_PRESENT_POST_KEY = BlogPostKey("aNotPresentTitle", "aNotPresentCategory", listOf("aNotPresentTag","anotherNotPresentTag"))
        private val A_SAVED_POST = BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag"))


    }
}