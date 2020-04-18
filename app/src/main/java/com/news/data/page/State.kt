package com.news.data.page

private interface State<T> {
    fun refresh ()
    fun loadNewPage()
    fun realise()
    fun newData(data: List<T>)
    fun fail(error: Throwable)
}