package kom.hikeside.Models.Atom;

import kom.hikeside.Models.MapView;

/**
 * Created by Koma on 13.08.2017.
 */

public class Place extends WebData{

   // String id;//primary key needed for Progress\StatusText online table

    String uid;//user id
    String name;//name
    String description;//описание

    double latitude;
    double longtitude;

    MapView type;

    public Place(){}

    public Place(String id, String uid, String name, String description, double latitude, double longtitude, MapView type) {
        this.webKey = id;
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.type = type;
    }

    public String getId() {
        return webKey;
    }

    public void setId(String id) {
        this.webKey = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setLongtitude(float longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public MapView getType() {
        return type;
    }

}