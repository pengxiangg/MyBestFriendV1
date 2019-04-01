package app.company.bulba.com.mybestfriendv1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by Zachary on 30/03/2019.
 */

@Database(entities = {Chat.class}, version = 1)
public abstract class ChatRoomDatabase extends RoomDatabase {

    public abstract ChatDao chatDao();

    private static volatile ChatRoomDatabase INSTANCE;

    static ChatRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChatRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    ChatRoomDatabase.class, "chat_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };


}
