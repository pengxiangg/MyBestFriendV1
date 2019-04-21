package app.company.bulba.com.mybestfriendv1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Zachary on 06/04/2019.
 */

public class SecondFragment extends Fragment implements ChatListAdapter.OnDeleteButtonClickListener {

    private ChatViewModel mChatViewModel;
    private boolean isLastItem = false;
    private final String ownerBF = "OWNER_BEST_FRIEND";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final ChatListAdapter adapter = new ChatListAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChatViewModel = ViewModelProviders.of(getActivity()).get(ChatViewModel.class);
        mChatViewModel.getAllChats().observe(getActivity(), new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable List<Chat> chats) {
                adapter.setChats(chats);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });

        final EditText editText = (EditText) view.findViewById(R.id.chat_box_second);

        Button button = (Button) view.findViewById(R.id.send_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                Log.d("Message: ", message);
                if(!message.equals("")) {
                    String user = ownerBF;
                    long unixTime = System.currentTimeMillis() / 1000L;
                    Chat chat = new Chat(message, user, unixTime);
                    mChatViewModel.insert(chat);
                    editText.getText().clear();
                }
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (adapter.getItemCount() >= 0) {
                    View test = recyclerView.getLayoutManager().findViewByPosition(adapter.getItemCount()-1);
                    if(test!=null) {
                        isLastItem = true;
                    }
                    else {
                        isLastItem = false;
                    }
                }
                return false;
            }
        });

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right,int bottom, int oldLeft, int oldTop,int oldRight, int oldBottom) {
                if ( bottom < oldBottom && isLastItem) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(adapter.getItemCount()-1);
                        }
                    }, 0);
                }
            }
        });

        return view;
    }

    @Override
    public void onDeleteButtonClicked(Chat chat) {
        mChatViewModel.deleteChat(chat);
    }
}
