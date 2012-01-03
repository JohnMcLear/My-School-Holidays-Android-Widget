package com.primaryt.mshwidget.bo;

import java.io.Serializable;

public class School implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3883608241255839082L;

    private Long schoolID;
    
    private String schoolLabel;
    
    public School(Long schoolID, String schoolLabel){
        this.schoolID = schoolID;
        this.schoolLabel = schoolLabel;
    }

    public Long getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Long schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolLabel() {
        return schoolLabel;
    }

    public void setSchoolLabel(String schoolLabel) {
        this.schoolLabel = schoolLabel;
    }
}
