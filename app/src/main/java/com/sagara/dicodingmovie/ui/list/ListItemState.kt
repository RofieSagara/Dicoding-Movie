package com.sagara.dicodingmovie.ui.list

sealed class ListItemState {
    object ShowLoading: ListItemState()
    object HideLoading: ListItemState()
    class Error(val message: String): ListItemState()
}