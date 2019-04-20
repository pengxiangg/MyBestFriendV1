package app.company.bulba.com.mybestfriendv1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

public class ChatRepository {

    private ChatDao mChatDao;
    private LiveData<List<Chat>> mAllChats;

    ChatRepository (Application application) {
        ChatRoomDatabase db = ChatRoomDatabase.getDatabase(application);
        mChatDao = db.chatDao();
        mAllChats = mChatDao.getAllChats();
    }

    LiveData<List<Chat>> getAllChats(){
        return mAllChats;
    }

    public void insert (Chat chat) {
        new insertAsyncTask(mChatDao).execute(chat);
    }

    public void deleteChat(Chat chat) {new deleteAsyncTask(mChatDao).execute(chat);}

    private static class insertAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao mAsyncTaskDao;

        insertAsyncTask(ChatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Chat... chats) {
            mAsyncTaskDao.insert(chats[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Chat, Void, Void> {
        private ChatDao mAsyncTaskDao;

        deleteAsyncTask(ChatDao dao) { mAsyncTaskDao = dao; }

        @Override
        protected Void doInBackground(Chat... chats) {
            mAsyncTaskDao.deleteChat(chats[0]);
            return null;
        }
    }
}
