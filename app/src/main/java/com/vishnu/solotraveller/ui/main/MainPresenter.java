package com.vishnu.solotraveller.ui.main;

import com.vishnu.solotraveller.data.DataManager;
import com.vishnu.solotraveller.data.model.Example;
import com.vishnu.solotraveller.injection.ConfigPersistent;
import com.vishnu.solotraveller.ui.base.BasePresenter;
import com.vishnu.solotraveller.util.RxUtil;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadRecommendedDestinations() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getService().getRecommendedDestinations()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the ribots.");
                        getMvpView().hideViewPager();
                    }

                    @Override
                    public void onNext(Example ribots) {
                        if (ribots.data.flight.size() == 0 && ribots.data.budgetFlight.size() == 0) {
                            getMvpView().hideViewPager();
                        } else {
                            getMvpView().showRecommendedDestinations(ribots);
                        }
                    }
                });
    }

}
