package idell.projects.blog.crud

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class BlogCrudRequestAdapterTest{
    @Test
    fun `adapt request`() {
        Assertions.assertThat(BlogCrudRequestAdapter().adapt(BlogPostCreateRequest(
                title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag")
        ))).isEqualTo(BlogPostDomainRequest(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag")))
    }
}