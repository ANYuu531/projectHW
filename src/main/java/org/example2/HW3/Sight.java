package org.example2.HW3;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sights")
public class Sight {
    private String id; // MongoDB 的 _id 字段
    private String sightName;
    private String zone;
    private String category;
    private String photoURL;
    private String description;
    private String address;

    public Sight() {
    }
    public Sight(String name,String category,String photoURL,String description,String address) {
        setSightName(name);
        setCategory(category);
        setPhotoURL(photoURL);
        setDescription(description);
        setAddress(address);
    }

    public String getSightName() {
        return sightName;
    }
    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoURL() {
        return photoURL;
    }
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String descrtiption) {
        this.description = descrtiption;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return  "SightName: " + sightName + '\n' +
                "Zone: " + zone + '\n' +
                "Category: " + category + '\n' +
                "PhotoURL: " + photoURL + '\n' +
                "Description: " + description + '\n' +
                "Address: " + address + '\n' ;
    }
}
