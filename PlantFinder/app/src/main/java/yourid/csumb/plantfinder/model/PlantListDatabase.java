package yourid.csumb.plantfinder.model;

import androidx.room.Database;

@Database(entities = {PlantList.class}, version = 1, exportSchema = false)
public class PlantListDatabase {
    public static final String DB_NAME = "PL_DATABASE";
    public static final String PL_TABLE = "PL_TABLE";
}
