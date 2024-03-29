package idell.projects.blog.crud

data class BlogPostDomainRequest(val title: String, val content:String,val author:String, val image:String, val category:String,val tags:List<String>)
