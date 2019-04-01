package app.company.bulba.com.mybestfriendv1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ChatViewModel mChatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ChatListAdapter adapter = new ChatListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mChatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        mChatViewModel.getAllChats().observe(this, new Observer<List<Chat>>() {
            @Override
            public void onChanged(@Nullable List<Chat> chats) {

                adapter.setChats(chats);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });

        final EditText editText = (EditText) findViewById(R.id.chat_box);

        Button button = (Button) findViewById(R.id.send_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                String user = "me";
                long unixTime = System.currentTimeMillis()/1000L;
                if(message != null) {
                    Chat chat = new Chat(message, user, unixTime);
                    mChatViewModel.insert(chat);
                    editText.getText().clear();
                }
            }
        });
    }
}
