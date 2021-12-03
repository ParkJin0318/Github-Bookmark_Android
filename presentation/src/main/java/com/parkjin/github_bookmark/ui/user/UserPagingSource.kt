package com.parkjin.github_bookmark.ui.user

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.parkjin.github_bookmark.model.User
import com.parkjin.github_bookmark.model.UserType
import com.parkjin.github_bookmark.model.firstName
import com.parkjin.github_bookmark.usecase.GetUsersUseCase
import io.reactivex.rxjava3.core.Single
import java.util.*

class UserPagingSource(
    private val useCase: GetUsersUseCase,
    private val input: Input
) : RxPagingSource<Int, UserListUIModel>() {

    companion object {
        private const val START_PAGE = 1
    }

    data class Input(
        val userName: String,
        val userType: UserType
    )

    override fun getRefreshKey(state: PagingState<Int, UserListUIModel>): Int? {
        return state.anchorPosition?.let {
            val page = state.closestPageToPosition(it)
            page?.prevKey?.inc() ?: page?.nextKey?.dec()
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, UserListUIModel>> {
        val page = params.key ?: START_PAGE

        return useCase.execute(input.userName, input.userType, page)
            .map { it.toLoadResult(page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun List<User>.toLoadResult(page: Int): LoadResult<Int, UserListUIModel> {
        val userListUIModels: MutableList<UserListUIModel> = mutableListOf()

        val users = LinkedList(this.sortedBy { it.name.uppercase() })

        while (!users.isEmpty()) {
            val user = users.remove()

            userListUIModels.add(UserListUIModel.UserHeader(user.firstName))
            userListUIModels.add(UserListUIModel.UserItem(user))

            while (users.peek() != null && user.firstName == users.peek()?.firstName) {
                userListUIModels.add(UserListUIModel.UserItem(users.remove()))
            }
        }

        return LoadResult.Page(
            data = userListUIModels,
            prevKey = if (page == START_PAGE) null else page.dec(),
            nextKey = if (this.isEmpty()) null else page.inc()
        )
    }
}
