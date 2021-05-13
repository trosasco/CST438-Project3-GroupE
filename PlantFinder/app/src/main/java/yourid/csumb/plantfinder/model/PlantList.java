package yourid.csumb.plantfinder.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = PlantListDatabase.PL_TABLE)
public class PlantList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int plantListId;

    private int userID;

    private String plantListName;
    //private List<String> Plants; //List of Plants in the List (Need to use a type converter)
    private String Plants;

    public PlantList(int userID, String plantListName, String Plants){//, List<String> Plants){
        this.userID = userID;
        this.plantListName = plantListName;
        this.Plants = Plants;
    }

    public int getPlantListId(){ return plantListId; }

    public String getPlants(){ return Plants;}

    public int getUserID() {
        return userID;
    }

    public String getPlantListName(){ return plantListName; }

    public void setPlantListId(int plantListId){ this.plantListId = plantListId; }

    public void setPlants(String plants){ this.Plants = plants; }

    public void setUserId(int userID) {
        this.userID = userID;
    }

    public void setPlantName(String plantListName) {
        this.plantListName = plantListName;
    }
}
