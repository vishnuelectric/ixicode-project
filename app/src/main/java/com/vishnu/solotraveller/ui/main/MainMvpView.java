package com.vishnu.solotraveller.ui.main;

import com.vishnu.solotraveller.data.model.Example;
import com.vishnu.solotraveller.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRecommendedDestinations(Example example);

    void hideViewPager();
}
