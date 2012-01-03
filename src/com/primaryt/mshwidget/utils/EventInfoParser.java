
package com.primaryt.mshwidget.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.primaryt.mshwidget.bo.Event;

public class EventInfoParser {
    String jsonString;
    ArrayList<Event> eventList;
    
    public EventInfoParser(String jsonString) {
       this.jsonString = jsonString;
       eventList = new ArrayList<Event>();
    }
    
    public ArrayList<Event> parse() throws JSONException{
    	jsonString = "["+jsonString+"]";
    	JSONArray nameArray = new JSONArray(jsonString);
          for(int i=0;i<nameArray.length();i++)
          {
        	  JSONObject object = nameArray.getJSONObject(i);
        	  Long schoolID = object.getLong("schoolid");
        	  String schoolName = object.getString("schoolname");
        	  String currentEvent = object.getString("currentevent");
        	  String nextEvent = object.getString("nextevent");
        	  String nextEventDate = object.getString("nexteventdate");
              Event event  = new Event();
              event.setSchoolID(schoolID);
              event.setSchoolName(schoolName);
              event.setCurrentEvent(currentEvent);
              event.setNextEvent(nextEvent);
              event.setNextEventDate(nextEventDate);
              
              eventList.add(event);
          }   
    	return eventList;
    }

}
