package com.vishnu.solotraveller.data;

import com.vishnu.solotraveller.data.remote.IxigoService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final IxigoService mIxigoService;


    @Inject
    public DataManager(IxigoService ixigoService) {
        mIxigoService = ixigoService;

    }


    public IxigoService getService()
    {
        return mIxigoService;
    }








}
