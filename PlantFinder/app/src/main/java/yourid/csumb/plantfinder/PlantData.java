package yourid.csumb.plantfinder;

import androidx.room.PrimaryKey;

public class PlantData {

//    @PrimaryKey(autoGenerate = true)
//    private int plantID;

    private String name;
    private String latinName;
    private String difficulty;
    private String light;

    public PlantData(String name, String latinName, String difficulty, String light) {
        this.name = name;
        this.latinName = latinName;
        this.difficulty = difficulty;
        this.light = light;
    }

//    public int getPlantID() {
//        return plantID;
//    }
//
//    public void setPlantID(int plantID) {
//        this.plantID = plantID;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }


}
