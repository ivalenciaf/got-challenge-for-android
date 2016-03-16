package es.npatarino.android.gotchallenge.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * GSon custom TypeAdapter for GoTCharacter class
 */
public class GoTCharacterTypeAdapter extends TypeAdapter<GoTCharacter> {
    @Override
    public void write(JsonWriter jsonWriter, GoTCharacter character) throws IOException {
    }

    @Override
    public GoTCharacter read(JsonReader in) throws IOException {
        GoTCharacter character = new GoTCharacter();
        GoTHouse house = new GoTHouse();

        in.beginObject();

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    character.setName(in.nextString());
                    break;
                case "description":
                    character.setDescription(in.nextString());
                    break;
                case "imageUrl":
                    character.setImageUrl(in.nextString());
                    break;
                case "houseId":
                    house.setId(in.nextString());
                    break;
                case "houseName":
                    house.setName(in.nextString());
                    break;
                case "houseImageUrl":
                    house.setImageUrl(in.nextString());
                    break;
            }
        }

        if (!house.getId().isEmpty() && !house.getName().isEmpty() && !house.getImageUrl().isEmpty()) {
            character.setHouse(house);
        }

        in.endObject();

        return character;
    }
}
