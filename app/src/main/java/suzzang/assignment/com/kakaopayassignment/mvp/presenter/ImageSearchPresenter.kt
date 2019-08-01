package suzzang.assignment.com.kakaopayassignment.mvp.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import suzzang.assignment.com.kakaopayassignment.api.ApplicationController
import suzzang.assignment.com.kakaopayassignment.api.NetworkService
import suzzang.assignment.com.kakaopayassignment.mvp.model.ImagesResponse


class ImageSearchPresenter : ImageSearchContract.Presenter {


    private var networkService: NetworkService

    private var disposable : CompositeDisposable

    private lateinit var view : ImageSearchContract.View

    init {
        networkService = ApplicationController.instance.networkService
        disposable =CompositeDisposable()
    }

    val header = "KakaoAK c91b1fa1ad94218ca9364c964cd51c32"

    var page : Int = 1


    override fun setView(view: ImageSearchContract.View) {
        this.view = view
    }

    override fun getSearchList(keyword: String,flag:Int) {
        if(flag == 0){
            disposable.add(networkService.getImgList(header, keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { imagesResponse -> view.showSearchList(imagesResponse.documents,imagesResponse.meta.is_end) }
            )

        }else{
            page += 1
            disposable.add(networkService.getImgList(header, keyword, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: ImagesResponse ->
                    if(t.meta.is_end){

                    }else{
                        view.showSearchList(t.documents,t.meta.is_end)
                    }
                }
            )

        }


    }

    override fun takeView(view: ImageSearchContract.View) {
    }

    override fun dropView() {
    }



}