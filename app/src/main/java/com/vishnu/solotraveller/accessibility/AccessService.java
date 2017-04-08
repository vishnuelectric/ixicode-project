package com.vishnu.solotraveller.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.vishnu.solotraveller.R;

/**
 * Created by VISHNUPRASAD on 08/04/17.
 */

public class AccessService extends AccessibilityService {
    private final AccessibilityServiceInfo info = new AccessibilityServiceInfo();
    private WindowManager windowManager;
    private View mChatHeadView;
    private OnEventListener event;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        windowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
        } else {
            info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS | AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.notificationTimeout = 100;

        this.setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                AccessibilityNodeInfo source = event.getSource();

                if (source == null || event.getPackageName() == null || source.getClassName() == null || source.getPackageName() == null) {
                    return;
                }
                System.out.println(AccessibilityEvent.eventTypeToString(eventType) + " " + event.getPackageName() + " " + " " + event.getContentDescription() + " " + event.getClassName());
                System.out.println("source " + source.getClassName() + source.getText() + " " + source.getViewIdResourceName() + " " + source.getContentDescription() + " " + source.describeContents());
                //iterate(source);
                if(event.getPackageName().toString().equals("com.yatra.base") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
                {
                   this.event = new YatraEvent();
                    BaseEvent.Event result = this.event.event(source);

                    if(result != null && result instanceof BaseEvent.FlightsEvent)
                    {

                    }
                    else  if(result != null && result instanceof BaseEvent.HotelsEvent)
                    {

                    }

                }
    }}catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {

    }


    private void checkYatraEvent(AccessibilityNodeInfo source)
    {
        iterate(source);
    }
    private void iterate(AccessibilityNodeInfo source)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if(source != null && source.getChildCount() >0) {
                for (int i = 0; i < source.getChildCount(); i++) {
                    if(source.getChild(i) != null)
                        System.out.println(source.getContentDescription()+" "+source.getChild(i).getContentDescription()+" "+source.getChild(i).getText() + " " + source.getChild(i).getViewIdResourceName() + " " + source.getClassName());
                    iterate(source.getChild(i));
                }
            }
        }
    }

    private void showFloatingButton()
    {
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);


        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the chat head position
//Initially view will be added to top-left corner
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(mChatHeadView, params);
    }
    private void hideFloatingButtonview()
    {
        if(mChatHeadView!= null )
        windowManager.removeView(mChatHeadView);
    }

    public interface OnEventListener {

         BaseEvent.Event event(AccessibilityNodeInfo source);
    }
}
