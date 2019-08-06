package suzzang.assignment.com.kakaopayassignment.mvp.presenter

import suzzang.assignment.com.kakaopayassignment.base.BasePresenter
import suzzang.assignment.com.kakaopayassignment.base.BaseView
import suzzang.assignment.com.kakaopayassignment.mvp.model.Images

interface ImageSearchContract {

    interface  View : BaseView { //baseview 는 공통으로 사용할 것 만

        //이미지데이터들 리사이클러뷰 어댑터에 연결
        fun showSearchList(imageList : ArrayList<Images>,isEnd:Boolean)
    }
    interface Presenter : BasePresenter<View> {

        override fun setView(view: View)

        //api 통신을 통해 검색결과에 따른 이미지데이터들 가져오기
        fun getSearchList(keyword : String,flag:Int)



    }
}