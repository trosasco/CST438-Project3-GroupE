package yourid.csumb.plantfinder.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlantListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PlantList... plantlist);

    @Update
    void update(PlantList... plantlist);

    @Delete
    void delete(PlantList... plantlist);

    @Query(" SELECT * FROM " + PlantListDatabase.PL_TABLE)
    List<PlantList> getAll();

    @Query("SELECT * FROM " + PlantListDatabase.PL_TABLE + " WHERE plantListName=:listName")
    Account getListByListname(String listName);
}
