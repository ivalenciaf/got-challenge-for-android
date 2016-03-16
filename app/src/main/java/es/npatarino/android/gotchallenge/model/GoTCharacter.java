package es.npatarino.android.gotchallenge.model;

import org.parceler.Parcel;

/**
 * Created by Nicolás Patarino on 21/02/16.
 */
@Parcel
public class GoTCharacter {
    String name;
    String imageUrl;
    String description;
    GoTHouse house;

    public GoTCharacter() {
    }

    public GoTCharacter(String name, String description, String imageUrl) {
        this.description = description;
        this.house = house;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GoTHouse getHouse() {
        return house;
    }

    public void setHouse(GoTHouse house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "GoTCharacter{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", house=" + house +
                '}';
    }
}
