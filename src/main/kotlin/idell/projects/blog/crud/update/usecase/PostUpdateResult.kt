package idell.projects.blog.crud.update.usecase

sealed class PostUpdateResult
data object PostUpdateSuccess : PostUpdateResult()
data object PostNotFound : PostUpdateResult()
data object PostUpdateError : PostUpdateResult()