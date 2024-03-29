package idell.projects.blog

import idell.projects.blog.crud.BlogPostRepository
import idell.projects.blog.crud.BlogPostUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class Configuration {
    @Bean
    open fun blogPostUseCase() : BlogPostUseCase = BlogPostUseCase(BlogPostRepository())
}