package idell.projects.blog.crud.retrieve.usecase

sealed class BlogPostRetrieveRequestClass
data class ByTitleRetrieveRequest(val title:String) : BlogPostRetrieveRequestClass()
data class ByCategoryRetrieveRequest(val category: String) : BlogPostRetrieveRequestClass()
data class ByTagsRetrieveRequest(val tags: List<String>) : BlogPostRetrieveRequestClass()
data class MultiParameterRequest(val parameters:List<BlogPostRetrieveRequestClass>)