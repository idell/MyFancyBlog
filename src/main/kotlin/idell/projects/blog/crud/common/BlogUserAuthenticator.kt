package idell.projects.blog.crud.common

class BlogUserAuthenticator(private val enabledUsers:List<String>) {

    fun isAUser(user:String):Boolean = enabledUsers.contains(user)
    fun isAnAdmin(user: String): Boolean = ADMIN == user

    companion object{
        private const val ADMIN = "admin"
    }

}