package app.company.bulba.com.mybestfriendv1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

@Dao
public interface ChatDao {

    @Insert
    void insert(Chat chat);

    @Query("DELETE FROM chat_table")
    void deleteAll();

    @Query("SELECT * from chat_table")
    LiveData<List<Chat>> getAllChats();

    @Delete
    void deleteChat(Chat chat);
}
