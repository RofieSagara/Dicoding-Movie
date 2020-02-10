package com.sagara.dicodingmovie.ui.search

sealed class SearchState {
    class Error(val message: String? = ""): SearchState()
}