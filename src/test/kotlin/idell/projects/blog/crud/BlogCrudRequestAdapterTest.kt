package idell.projects.blog.crud

import idell.projects.blog.crud.create.controller.BlogPostCreateRequest
import idell.projects.blog.crud.retrieve.controller.BlogCrudRequestAdapter
import idell.projects.blog.crud.retrieve.usecase.BlogPost
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
        ))).isEqualTo(BlogPost(title = "aTitle",
                content = "aContent",
                author = "anAuthor",
                image = "anImage",
                category = "aCategory",
                tags = listOf("aTag","anotherTag")))
    }
}