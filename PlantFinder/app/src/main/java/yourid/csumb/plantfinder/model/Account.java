package yourid.csumb.plantfinder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "accounts")
public class Account {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String userName;

    @ColumnInfo(name = "password")
    private String userPassword;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Account(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

}
