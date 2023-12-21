package yao.ic.appexample.data.state

import yao.ic.appexample.data.model.Show

data class ShowState(
    val showList: List<Show> = emptyList(),
    val searchedShows: List<Show> = emptyList(),
    val showDetail: Show? = null,
)
