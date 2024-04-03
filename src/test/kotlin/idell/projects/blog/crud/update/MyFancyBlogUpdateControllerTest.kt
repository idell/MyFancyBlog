package idell.projects.blog.crud.update

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.update.controller.MyFancyBlogUpdateController
import idell.projects.blog.crud.update.controller.MyFancyBlogUpdateController.BlogPostUpdateRequest
import idell.projects.blog.crud.update.usecase.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class MyFancyBlogUpdateControllerTest {
    private val authenticator = MyFancyBlogUserAuthenticator(listOf("user"))
    private val blogPostUpdateUseCase = Mockito.mock(BlogPostUpdateUseCase::class.java)
    private val underTest = MyFancyBlogUpdateController(authenticator, blogPostUpdateUseCase)

    @Test
    fun `will return 401 if uses is unknown`() {
        val actual = underTest.update("unknown-user", AN_EXISTING_POST_ID, A_POST_UPDATE)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
    }

    @Test
    fun `will return 200 if post has been fully updated`() {
        Mockito.`when`(blogPostUpdateUseCase.update(AN_EXISTING_POST_UPDATE_REQUEST)).thenReturn(PostUpdateSuccess)

        val actual = underTest.update("user", AN_EXISTING_POST_ID, A_POST_UPDATE)

        Mockito.verify(blogPostUpdateUseCase).update(AN_EXISTING_POST_UPDATE_REQUEST)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok().build<Any>())
    }

    @Test
    fun `will return 400 if try to update full post with content too long`() {
        val actual = underTest.update("user", AN_EXISTING_POST_ID, A_TOO_LONG_EXISTING_POST_UPDATE)

        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.badRequest().build<Any>())
    }
    @Test
    fun `will return 404 if try to update a not existing post`() {
        Mockito.`when`(blogPostUpdateUseCase.update(A_NOT_EXISTING_POST_UPDATE_REQUEST)).thenReturn(PostNotFound)

        val actual = underTest.update("user", A_NOT_EXISTING_POST_ID, A_POST_UPDATE)

        Mockito.verify(blogPostUpdateUseCase).update(A_NOT_EXISTING_POST_UPDATE_REQUEST)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.notFound().build<Any>())
    }
    @Test
    fun `will return 500 if update goes in error`() {
        Mockito.`when`(blogPostUpdateUseCase.update(A_NOT_EXISTING_POST_UPDATE_REQUEST)).thenReturn(PostUpdateError)

        val actual = underTest.update("user", A_NOT_EXISTING_POST_ID, A_POST_UPDATE)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity.internalServerError().build<Any>())
    }

    companion object {
        private val A_POST_UPDATE = BlogPostUpdateRequest("aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val A_TOO_LONG_EXISTING_POST_UPDATE = BlogPostUpdateRequest("aTitle",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. " +
                        "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies " +
                        "nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet " +
                        "nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis " +
                        "eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend " +
                        "tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra " +
                        "quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies" +
                        " nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, " +
                        "tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. " +
                        "Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. " +
                        "Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. " +
                        "Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, " +
                        "augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque " +
                        "ut, mollis sed, nonummy id, met",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private const val AN_EXISTING_POST_ID = 1234
        private const val A_NOT_EXISTING_POST_ID = 5678
        private val AN_EXISTING_POST_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(AN_EXISTING_POST_ID), "aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val A_NOT_EXISTING_POST_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(A_NOT_EXISTING_POST_ID), "aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

    }
}