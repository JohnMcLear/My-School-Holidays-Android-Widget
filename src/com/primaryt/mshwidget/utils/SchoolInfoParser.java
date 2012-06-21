
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
        JSONObject json = new JSONObject(jsonString);
        JSONArray nameArray = json.getJSONArray("results");
        for (int i = 0; i < nameArray.length(); i++) {
            JSONObject object = nameArray.getJSONObject(i);
            if (object.getString("sourceType").equals("school")) {
                Long schoolID = object.getLong("id");
                String label = object.getString("name");
                String country = object.getString("countryCode");
                String fullUrl = object.getString("fullUrl");
                School school = new School(schoolID, label);
                school.setFullUrl(fullUrl);
                school.setCountry(country);
                schoolList.add(school);
            }
        }
        return schoolList;
    }
}
