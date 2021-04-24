package yourid.csumb.plantfinder.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = AccountDatabase.USER_TABLE)
public class Account implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUserName;
    private String mUserPassword;
    private boolean isAdmin;

    public Account(String mUserName, String mUserPassword, boolean isAdmin) {
        this.mUserName = mUserName;
        this.mUserPassword = mUserPassword;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() { return isAdmin; }

    public void setAdmin(boolean admin) { isAdmin = admin; }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        this.mUserPassword = userPassword;
    }

    @NonNull
    @Override
    public String toString() {
        String output;

        output = mUserName + "'s password is: ";
        output += "\n" + mUserPassword;

        return output;
    }

}
