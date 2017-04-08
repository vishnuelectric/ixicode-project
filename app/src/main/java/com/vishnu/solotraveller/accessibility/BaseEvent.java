package com.vishnu.solotraveller.accessibility;

/**
 * Created by VISHNUPRASAD on 08/04/17.
 */

public  class BaseEvent {
    public  interface Event{

    }
    public static class FlightsEvent implements Event{
        public String from;
        public String to;
        public String Date;
        public int persons;
    }

    public static class HotelsEvent implements Event{
        public String place;
        public String checkIn;
        public String checkOut;
        public int persons;
    }
}
