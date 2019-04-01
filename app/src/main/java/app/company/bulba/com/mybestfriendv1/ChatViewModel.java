package app.company.bulba.com.mybestfriendv1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

public class ChatViewModel extends AndroidViewModel {

    private ChatRepository mRepository;

    private LiveData<List<Chat>> mAllChats;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ChatRepository(application);
        mAllChats = mRepository.getAllChats();
    }

    LiveData<List<Chat>> getAllChats() {return mAllChats;}
    public void insert(Chat chat) {mRepository.insert(chat);}
}
