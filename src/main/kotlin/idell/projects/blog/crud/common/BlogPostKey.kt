package idell.projects.blog.crud.common

data class BlogPostKey(val title:String?, val category:String?, val tags:List<String>?)

data class BlogPostId(val id:Int)