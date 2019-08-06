package suzzang.assignment.com.kakaopayassignment.base

interface BasePresenter<T> {
    fun dropView()
    fun setView(view:T)

}