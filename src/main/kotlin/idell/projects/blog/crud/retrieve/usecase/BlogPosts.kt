package idell.projects.blog.crud.retrieve.usecase

typealias BlogPosts = List<BlogPost>
data class BlogPost(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>)
