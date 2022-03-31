package com.zjx.hencoderstudy;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaTest {
    public void run () {
        //       Single.just(1)
//           .map(object : Function<Int, String>{
//            override fun apply(t: Int?): String {
//                return t.toString()
//            }
//        })
//           .subscribe(object : SingleObserver<String> {
//                override fun onSubscribe(d: Disposable?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onSuccess(t: String?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Throwable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })

//        Observable.interval(1, TimeUnit.SECONDS)
//            .subscribe(object : Observer<Long>{
//                override fun onSubscribe(d: Disposable?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onNext(t: Long?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Throwable?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onComplete() {
//                    TODO("Not yet implemented")
//                }
//
//            })

//        Single.just(1)
//            .delay(5, TimeUnit.SECONDS)

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS);
    }
}
