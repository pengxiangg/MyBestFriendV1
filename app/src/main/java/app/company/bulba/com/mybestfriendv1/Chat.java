package app.company.bulba.com.mybestfriendv1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Zachary on 30/03/2019.
 */

@Entity(tableName = "chat_table")
public class Chat {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String mMessage;

    private String mUser;

    private long mUnixTime;

    public Chat(String message, String user, long unixTime) {
        this.mMessage = message;
        this.mUser = user;
        this.mUnixTime = unixTime;
    }

    public int getId() { return id; }
    public void setId(int id) {this.id = id;}

    public String getMessage() {return mMessage;}
    public void setMessage(String message) {this.mMessage = message;}

    public String getUser() {return mUser;}
    public void setUser(String user) {this.mUser = user;}

    public long getUnixTime() {return mUnixTime;}
    public void setUnixTime(long unixTime) {this.mUnixTime = unixTime;}

}
