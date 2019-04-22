package app.company.bulba.com.mybestfriendv1;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String convertUnixTimeToString(String format) {
        if(format == null) {
            format = "h:mm a";
        }

        Date time = new java.util.Date(mUnixTime*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(time);
    }

    public String convertUnixTimeToDate(String format) {
        if(format == null) {
            format = "dd MMMM yyyy";
        }

        Date date = new java.util.Date(mUnixTime*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
        return sdf.format(date);
    }



}
