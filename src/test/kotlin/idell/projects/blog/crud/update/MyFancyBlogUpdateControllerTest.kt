package idell.projects.blog.crud.update

import idell.projects.blog.crud.common.BlogPostId
import idell.projects.blog.crud.common.MyFancyBlogUserAuthenticator
import idell.projects.blog.crud.update.controller.MyFancyBlogUpdateController
import idell.projects.blog.crud.update.controller.MyFancyBlogUpdateController.BlogPostUpdateRequest
import idell.projects.blog.crud.update.controller.PostUpdateSuccessResponse
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
    fun `full update - will return 401 if uses is unknown`() {
        val actual = underTest.fullUpdate("unknown-user", AN_EXISTING_POST_ID, A_POST_UPDATE)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
    }

    @Test
    fun `full update - will return 200 if post has been fully updated`() {
        Mockito.`when`(blogPostUpdateUseCase.update(AN_EXISTING_POST_FULL_UPDATE_REQUEST)).thenReturn(AN_EXISTING_POST_UPDATED)

        val actual = underTest.fullUpdate("user", AN_EXISTING_POST_ID, A_POST_UPDATE)

        Mockito.verify(blogPostUpdateUseCase).update(AN_EXISTING_POST_FULL_UPDATE_REQUEST)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok(AN_EXISTING_POST_UPDATE_RESPONSE))
    }

    @Test
    fun `full update - will return 400 if try to update full post with content too long`() {
        val actual = underTest.fullUpdate("user", AN_EXISTING_POST_ID, A_TOO_LONG_EXISTING_POST_UPDATE)

        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.badRequest().build<Any>())
    }

    @Test
    fun `full update - will return 500 if update goes in error`() {
        Mockito.`when`(blogPostUpdateUseCase.update(A_NOT_EXISTING_POST_FULL_UPDATE_REQUEST)).thenReturn(PostUpdateError)

        val actual = underTest.fullUpdate("user", A_NOT_EXISTING_POST_ID, A_POST_UPDATE)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity.internalServerError().build<Any>())
    }
    @Test
    fun `partial update - will return 401 if uses is unknown`() {
        val actual = underTest.partialUpdate("unknown-user", AN_EXISTING_POST_ID, null,null,null,null,null,null)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity<Any>(HttpStatus.UNAUTHORIZED))
        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
    }

    @Test
    fun `partial update - will return 200 if post has been updated`() {
        Mockito.`when`(blogPostUpdateUseCase.update(A_POST_PARTIAL_UPDATE_REQUEST)).thenReturn(AN_EXISTING_POST_PARTIALLY_UPDATED)

        val actual = underTest.partialUpdate("user", AN_EXISTING_POST_ID, "aNewTitle",null,null,null,null, null)

        Mockito.verify(blogPostUpdateUseCase).update(A_POST_PARTIAL_UPDATE_REQUEST)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok(AN_EXISTING_POST_UPDATE_RESPONSE))
    }

    @Test
    fun `partial update - will return 400 if try to update full post with content too long`() {
        val actual = underTest.partialUpdate("user", AN_EXISTING_POST_ID, null,A_TOO_LONG_CONTENT,null,null,null, null)

        Mockito.verifyNoInteractions(blogPostUpdateUseCase)
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.badRequest().build<Any>())
    }

    @Test
    fun `partial update - will return 500 if update goes in error`() {
        Mockito.`when`(blogPostUpdateUseCase.update(A_NOT_EXISTING_PARTIAL_UPDATE_REQUEST)).thenReturn(PostUpdateError)

        val actual = underTest.partialUpdate("user", A_NOT_EXISTING_POST_ID, null,null,null,null,null, null)

        Assertions.assertThat(actual).isEqualTo(ResponseEntity.internalServerError().build<Any>())
    }

    companion object {
        private const val A_TOO_LONG_CONTENT = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. " +
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
                "ut, mollis sed, nonummy id, met"
        private val A_POST_UPDATE = BlogPostUpdateRequest("aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val A_TOO_LONG_EXISTING_POST_UPDATE = BlogPostUpdateRequest("aTitle",
                A_TOO_LONG_CONTENT,
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private const val AN_EXISTING_POST_ID = 1234
        private const val A_NOT_EXISTING_POST_ID = 5678
        private val AN_EXISTING_POST_FULL_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(AN_EXISTING_POST_ID), "aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val AN_EXISTING_POST_UPDATED = PostUpdateSuccess("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val A_NOT_EXISTING_POST_FULL_UPDATE_REQUEST = BlogPostFullUpdateRequest(BlogPostId(A_NOT_EXISTING_POST_ID), "aTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))
        private val A_POST_PARTIAL_UPDATE_REQUEST = BlogPostPartialUpdateRequest(BlogPostId(AN_EXISTING_POST_ID),"aNewTitle",null,null,null,null,null)

        private val A_NOT_EXISTING_PARTIAL_UPDATE_REQUEST = BlogPostPartialUpdateRequest(BlogPostId(A_NOT_EXISTING_POST_ID),null,null,null,null,null,null)
        private val AN_EXISTING_POST_PARTIALLY_UPDATED = PostUpdateSuccess("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))

        private val AN_EXISTING_POST_UPDATE_RESPONSE = PostUpdateSuccessResponse("aNewTitle",
                "an amazong blog content updated",
                "anAuthor",
                "anImage",
                "aCategory",
                listOf("aTag", "anotherTag"))


    }
}