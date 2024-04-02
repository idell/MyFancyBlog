package idell.projects.blog

import idell.projects.blog.crud.common.BlogPostRepository
import idell.projects.blog.crud.retrieve.controller.BlogCrudRequestAdapter
import idell.projects.blog.crud.create.usecase.BlogPostUseCase
import idell.projects.blog.crud.common.InMemoryBlogPostRepository
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.retrieve.usecase.BlogPostRetrieveUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Configuration {

    @Bean
    open fun blogUserAuthenticator(): MyFancyBlogUserAuthenticator =  MyFancyBlogUserAuthenticator(listOf("user","admin"))
    @Bean
    open fun blogPostRepository() : BlogPostRepository  =  InMemoryBlogPostRepository(mutableMapOf())
    @Bean
    open fun blogPostUseCase(blogPostRepository: BlogPostRepository) : BlogPostUseCase = BlogPostUseCase(blogPostRepository)

    @Bean
    open fun blogCrudRequestAdapter() = BlogCrudRequestAdapter()

    @Bean
    open fun blogPostRetrieveUseCase(blogPostRepository: BlogPostRepository) = BlogPostRetrieveUseCase(blogPostRepository)
}