package yao.ic.appexample.data.repository

import yao.ic.appexample.model.Show

data class ShowState(
    val showList: List<Show> = emptyList(),
    val searchedShows: List<Show> = emptyList(),
    val showDetail: Show? = null,
) : UIState
