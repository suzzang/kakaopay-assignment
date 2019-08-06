package suzzang.assignment.com.kakaopayassignment.mvp.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import suzzang.assignment.com.kakaopayassignment.api.ApplicationController
import suzzang.assignment.com.kakaopayassignment.api.NetworkService

class ImageSearchPresenter : ImageSearchContract.Presenter {


    private var networkService: NetworkService
    private var disposable : CompositeDisposable

    private lateinit var view : ImageSearchContract.View

    private val header = "KakaoAK c91b1fa1ad94218ca9364c964cd51c32"


    var page : Int
    var isFirstflag : Int


    init {
        networkService = ApplicationController.instance.networkService
        disposable =CompositeDisposable()
        page = 1
        isFirstflag = 0
    }





    override fun setView(view: ImageSearchContract.View) {
        this.view = view
    }

    override fun getSearchList(keyword: String,flag:Int,sort:String) {
        if(flag == 0){ //첫요청
            page = 1
            isFirstflag = 0

            disposable.add(networkService.getImgList(header, keyword, page,sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({imagesResponse -> view.showSearchList(imagesResponse.documents,imagesResponse.meta.is_end) },
                    {err->view.showError(err.message.toString())})
            )
        }else{ //그 후 요청
            page += 1
            disposable.add(networkService.getImgList(header, keyword, page,sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({imagesResponse ->
                    if(imagesResponse.meta.is_end && isFirstflag == 0){
                        view.showSearchList(imagesResponse.documents,imagesResponse.meta.is_end)
                        isFirstflag = 1

                    }else if(!(imagesResponse.meta.is_end) && isFirstflag == 0){
                        view.showSearchList(imagesResponse.documents,imagesResponse.meta.is_end)
                    }
                },{err-> view.showError(err.message.toString())})
            )

        }
    }


    override fun dropView() {
        disposable.clear()
    }






}