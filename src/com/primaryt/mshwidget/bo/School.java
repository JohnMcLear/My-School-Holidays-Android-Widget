package com.primaryt.mshwidget.bo;

import java.io.Serializable;

public class School implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3883608241255839082L;

    private Long schoolID;
    
    private String schoolLabel;
    
    private String country;
    
    private String fullUrl;
    
    private String town;
    
    private String postCode;
    
    private String street;
    
    public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public School(Long schoolID, String schoolLabel){
        this.schoolID = schoolID;
        this.schoolLabel = schoolLabel;
    }

	public School(){
		
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }
}
