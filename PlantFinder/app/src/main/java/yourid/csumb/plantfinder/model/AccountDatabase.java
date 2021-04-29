package yourid.csumb.plantfinder.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {

    public static final String DB_NAME = "USER_DATABASE";
    public static final String USER_TABLE = "USER_TABLE";

    public abstract AccountDao getAccountDAO();

    private static AccountDatabase DBInstance;

    public static synchronized AccountDatabase getInstance(Context context){
        if(DBInstance == null){
            DBInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AccountDatabase.class,
                    "account.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return DBInstance;
    }

}