package yourid.csumb.plantfinder.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account... account);

    @Update
    void update(Account... account);

    @Delete
    void delete(Account... account);

    @Query(" SELECT * FROM " + AccountDatabase.USER_TABLE)
    List<Account> getAll();

    @Query("SELECT * FROM " + AccountDatabase.USER_TABLE + " WHERE mUserName=:username")
    Account getUserByUsername(String username);

    @Query("SELECT * FROM " + AccountDatabase.USER_TABLE + " WHERE mUserId=:userId")
    Account getUsersByUserId(int userId);

}