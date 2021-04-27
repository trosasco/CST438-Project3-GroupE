package yourid.csumb.plantfinder.model;


import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = PlantListDatabase.PL_TABLE)
public class PlantList implements Serializable {
    private int userID;

    private String plantName;

    public PlantList(int userID, String plantName){
        this.userID = userID;
        this.plantName = plantName;
    }

    public int getUserId() {
        return userID;
    }

    public void setUserId(int userID) {
        this.userID = userID;
    }

    public String getPlantName(){ return plantName; }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
}
