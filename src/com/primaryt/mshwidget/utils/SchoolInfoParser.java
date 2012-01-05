
package com.primaryt.mshwidget.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.primaryt.mshwidget.bo.School;

public class SchoolInfoParser {

    String jsonString;

    ArrayList<School> schoolList;

    public SchoolInfoParser(String jsonString) {
        this.jsonString = jsonString;
        schoolList = new ArrayList<School>();
    }

    public ArrayList<School> parse() throws JSONException {
    	jsonString = jsonString.trim();
        if (jsonString.indexOf("(") < 2) {
            int strLen = jsonString.length();
            jsonString = jsonString.substring(1, strLen - 1);
        }
        JSONArray nameArray = new JSONArray(jsonString);
        for (int i = 0; i < nameArray.length(); i++) {
            JSONObject object = nameArray.getJSONObject(i);
            Long schoolID = object.getLong("schoolid");
            String label = object.getString("label");
            School school = new School(schoolID, label);
            schoolList.add(school);
        }
        return schoolList;
    }
}
