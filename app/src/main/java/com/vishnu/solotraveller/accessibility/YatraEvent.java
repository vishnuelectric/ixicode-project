package com.vishnu.solotraveller.accessibility;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by VISHNUPRASAD on 08/04/17.
 */

public class YatraEvent implements AccessService.OnEventListener {
    @Override
    public BaseEvent.Event event(AccessibilityNodeInfo source) {

        iterate(source);
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
