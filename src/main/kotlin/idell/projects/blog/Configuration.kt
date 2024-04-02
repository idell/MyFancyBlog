package idell.projects.blog

import idell.projects.blog.crud.retrieve.controller.BlogCrudRequestAdapter
import idell.projects.blog.crud.create.usecase.BlogPostUseCase
import idell.projects.blog.crud.common.InMemoryBlogPostRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Configuration {
    @Bean
    open fun blogPostUseCase() : BlogPostUseCase = BlogPostUseCase(InMemoryBlogPostRepository(mutableMapOf()))

    @Bean
    open fun blogCrudRequestAdapter() = BlogCrudRequestAdapter()
}