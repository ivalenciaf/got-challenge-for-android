package es.npatarino.android.gotchallenge.model;

import org.parceler.Parcel;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Model for GoT House
 */
@Parcel
public class GoTHouse implements Comparable<GoTHouse> {
    String id = "";
    String name = "";
    String imageUrl = "";

    public GoTHouse() {

    }

    public GoTHouse(String id, String name, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public static Collection<GoTHouse> housesFromCharacters(Collection<GoTCharacter> characters) {
        Set<GoTHouse> hs = new TreeSet<>();
        for (GoTCharacter character : characters) {
            if (character.getHouse() != null) {
                hs.add(character.getHouse());
            }
        }

        return hs;
    }

    @Override
    public int compareTo(GoTHouse another) {
        int c = -1;

        if (another != null && id != null) {
            c = id.compareTo(another.getId());
        }

        return c;
    }

    @Override
    public boolean equals(Object o) {
        boolean eq = false;

        if (o != null && o instanceof GoTHouse && id != null) {
            eq = id.equals(((GoTHouse) o).getId());
        }

        return eq;
    }

    @Override
    public String toString() {
        return "GoTHouse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
