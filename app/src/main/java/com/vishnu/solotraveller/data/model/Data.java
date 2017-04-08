
package com.vishnu.solotraveller.data.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("flight")
    @Expose
    public List<Flight> flight = null;
    @SerializedName("budget_flight")
    @Expose
    public List<BudgetFlight> budgetFlight = null;

}
