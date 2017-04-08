package com.vishnu.solotraveller.accessibility;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.vishnu.solotraveller.BoilerplateApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VISHNUPRASAD on 08/04/17.
 */

public class YatraEvent implements AccessService.OnEventListener {

BaseEvent.FlightsEvent flightsEvent;

    @Override
    public BaseEvent.Event event(AccessibilityNodeInfo source) {
        Toast.makeText(BoilerplateApplication.get(),"yatra event",Toast.LENGTH_SHORT).show();
        flightsEvent =new BaseEvent.FlightsEvent();
        if(source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/org_citycode_textview").size() > 0 && source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/org_citycode_textview").get(0).getText().toString() != null)
        {
            flightsEvent.from = source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/org_citycode_textview").get(0).getText().toString();
        }
        if(source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/dest_citycode_textview").size()>0 && source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/dest_citycode_textview").get(0).getText().toString() != null)
        {
            flightsEvent.to=source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/dest_citycode_textview").get(0).getText().toString();
        }

        if(source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/depart_date_textview").size() >0 && source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/depart_date_textview").get(0).getText() != null)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("ddMMyyyy");
            Date date = null;
            try {
                date = simpleDateFormat.parse(source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/depart_date_textview").get(0).getText().toString()+source.findAccessibilityNodeInfosByViewId("com.yatra.base:id/depart_month_textview").get(0).getText().toString()+"2017");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            flightsEvent.Date =simpleDateFormat1.format(date);
        }
        //iterate(source);

        if(flightsEvent.from != null)
        {
            return flightsEvent;
        }
        return null;
    }


    private void iterate(AccessibilityNodeInfo source)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if(source != null && source.getChildCount() >0) {
                for (int i = 0; i < source.getChildCount(); i++) {
                    if(source.getChild(i) != null) {


                        System.out.println(source.getContentDescription() + " " + source.getChild(i).getContentDescription() + " " + source.getChild(i).getText() + " " + source.getChild(i).getViewIdResourceName() + " " + source.getClassName());

                    }
                        iterate(source.getChild(i));
                }
            }
        }
    }
}
