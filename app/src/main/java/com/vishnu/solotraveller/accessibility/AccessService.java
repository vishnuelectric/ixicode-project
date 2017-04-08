package com.vishnu.solotraveller.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private boolean isViewShowing= false;
    private int lastx;
    private int lasty =100;

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
                if(event.getPackageName().toString().equals("com.yatra.base") && eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getClassName().toString().equalsIgnoreCase("com.yatra.flights.activity.FlightBookingActivity"))
                {
                   this.event = new YatraEvent();
                    BaseEvent.Event result = this.event.event(source);

                    if(result != null && result instanceof BaseEvent.FlightsEvent)
                    {
                        if(isViewShowing) {
                            hideFloatingButtonview();
                        }
                       showFloatingButton((BaseEvent.FlightsEvent) result);
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

    private void showFloatingButton(final BaseEvent.FlightsEvent flightEvent)
    {final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);
        mChatHeadView.setOnTouchListener(new View.OnTouchListener() {
        private int lastAction;
        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //remember the initial position.
                    initialX = params.x;
                    initialY = params.y;

                    //get the touch location
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();

                    lastAction = event.getAction();
                    return false;
                case MotionEvent.ACTION_UP:
                    //As we implemented on touch listener with ACTION_MOVE,
                    //we have to check if the previous action was ACTION_DOWN
                    //to identify if the user clicked the view or not.

                    if(lastAction == MotionEvent.ACTION_DOWN)
                    {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ixigo.com/search/result/flight/"+flightEvent.from+"/"+flightEvent.to+"/"+flightEvent.Date+"//1/0/0/e"));
                        i.setPackage("com.android.chrome");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        hideFloatingButtonview();
                    }
                    return true;
                case MotionEvent.ACTION_MOVE:
                    //Calculate the X and Y coordinates of the view.
                    params.x = initialX + (int) (event.getRawX() - initialTouchX);
                    params.y = initialY + (int) (event.getRawY() - initialTouchY);

                    //Update the layout with new X & Y coordinate
                    windowManager.updateViewLayout(mChatHeadView, params);
                    lastAction = event.getAction();
                    return false;
            }
            return false;
        }
    });

        //Add the view to the window.


        //Specify the chat head position
//Initially view will be added to top-left corner
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = lastx;
        params.y = lasty;

        //Add the view to the window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        windowManager.addView(mChatHeadView, params);
        isViewShowing =true;

    }
    private void hideFloatingButtonview()
    {
        if(mChatHeadView!= null ) {
            windowManager.removeView(mChatHeadView);
            isViewShowing =false;
        }
    }

    public interface OnEventListener {

         BaseEvent.Event event(AccessibilityNodeInfo source);
    }
}
