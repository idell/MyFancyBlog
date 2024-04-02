package idell.projects.blog.crud.delete.usecase

sealed class DeleteResult
data object PostDeleted : DeleteResult()
data object NoPostDeleted : DeleteResult()
