
package com.vishnu.solotraveller.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BudgetFlight {

    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("countryName")
    @Expose
    public String countryName;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("data")
    @Expose
    public String data;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("cityName")
    @Expose
    public String cityName;
    @SerializedName("stateName")
    @Expose
    public String stateName;
    @SerializedName("price")
    @Expose
    public Integer price;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("cityId")
    @Expose
    public String cityId;
    @SerializedName("destinationCategories")
    @Expose
    public List<String> destinationCategories = null;

}
