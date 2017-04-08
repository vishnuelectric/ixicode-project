package com.vishnu.solotraveller.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vishnu.solotraveller.R;
import com.vishnu.solotraveller.accessibility.AccessService;
import com.vishnu.solotraveller.data.model.BudgetFlight;
import com.vishnu.solotraveller.data.model.Example;
import com.vishnu.solotraveller.data.model.Flight;
import com.vishnu.solotraveller.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView {



    @Inject
    com.vishnu.solotraveller.ui.main.MainPresenter mMainPresenter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.recommended_destinations)ViewPager mRecommendedDestViewPager;
RecommendedDestAdapter recommendedDestAdapter;
    List<Flight> flights = new ArrayList<>();
    List<BudgetFlight> budgetFlights = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainPresenter.attachView(this);
        mMainPresenter.loadRecommendedDestinations();


        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }
        recommendedDestAdapter = new RecommendedDestAdapter(this);
        mRecommendedDestViewPager.setAdapter(recommendedDestAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    @Override
    public void showRecommendedDestinations(Example example) {
       flights = example.data.flight;
        budgetFlights= example.data.budgetFlight;
    }

    @Override
    public void hideViewPager() {

    }

    /***** MVP View methods implementation *****/


    private static class RecommendedDestAdapter extends PagerAdapter{

        private int count;

        private Context mContext;
        private LayoutInflater mLayoutInflater;
        public RecommendedDestAdapter(Context context)
        {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return mLayoutInflater.inflate(R.layout.recommended_destination_layout,container,false);
        }

        private void setCount(int count)
        {
            this.count= count;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private boolean isAccessibilitySettingsOn(Context mContext) {
        int accessibilityEnabled = 0;
        final String service = getPackageName() + "/" + AccessService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Timber.d( "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Timber.d( "Error finding setting, default accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            Timber.d( "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    Timber.d( "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Timber.d( "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Timber.d( "***ACCESSIBILITY IS DISABLED***");
        }

        return false;
    }


}
