
package com.primaryt.mshwidget.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.primaryt.mshwidget.R;
import com.primaryt.mshwidget.bo.School;

public class SchoolAdapter extends BaseAdapter {
    private ArrayList<School> schoolList;

    private Context context;

    public SchoolAdapter(Context context) {
        this.context = context;
        schoolList = new ArrayList<School>();
    }

    @Override
    public int getCount() {
        return schoolList.size();
    }

    @Override
    public Object getItem(int index) {
        return schoolList.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        School school = (School) getItem(index);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.school_item, null);
        }

        TextView textViewSchool = (TextView) convertView.findViewById(R.id.textViewSchool);
        textViewSchool.setText(school.getSchoolLabel());

        TextView textViewCountry = (TextView) convertView.findViewById(R.id.textViewCountry);
        textViewCountry
                .setText(context.getString(R.string.lab_country) + " " + school.getCountry().toUpperCase());
        convertView.setTag(school);
        return convertView;
    }

    public void setSchoolList(ArrayList<School> newSchoolList) {
        schoolList.clear();
        schoolList.addAll(newSchoolList);
    }

    public void clear() {
        schoolList.clear();
    }
}
