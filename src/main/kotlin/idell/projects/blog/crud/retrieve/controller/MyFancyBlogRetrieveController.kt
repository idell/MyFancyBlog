package idell.projects.blog.crud.retrieve.controller

import idell.projects.blog.crud.common.BlogPostKey
import idell.projects.blog.crud.retrieve.usecase.BlogPostRetrieveUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

class MyFancyBlogRetrieveController(private val blogPostRetrieveUseCase: BlogPostRetrieveUseCase) {

    @GetMapping("/v1/blog/posts/")
    fun retrieve(@RequestParam(required = false) title: String?,
                 @RequestParam(required = false) category: String?,
                 @RequestParam(required = false) tags: List<String>?): BlogPostRetrieveResponse {

        if (title == null && category == null && tags == null) {
            return EmptyBlogPostsResponse
        }
        val retrieve = blogPostRetrieveUseCase.retrieve(BlogPostKey(title, category, tags))
        return when {
            retrieve.isEmpty() -> {
                EmptyBlogPostsResponse
            }

            else -> BlogPostsResponse(retrieve.stream().map { BlogPostResponse(it.title, it.content, it.author, it.image, it.category, it.tags) }.toList())
        }
    }

}