package yourid.csumb.plantfinder.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PlantList.class}, version = 1, exportSchema = false)
public abstract class PlantListDatabase extends RoomDatabase {

    public static final String DB_PL_NAME = "PL_DATABASE";
    public static final String PL_TABLE = "PL_TABLE";

    public abstract PlantListDao getPlantListDAO();

    private static PlantListDatabase DBInstance;

    public static synchronized PlantListDatabase getInstance(Context context){
        if(DBInstance == null){
            DBInstance = Room.databaseBuilder(context.getApplicationContext(),
                    PlantListDatabase.class,
                    "account.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return DBInstance;
    }
}
