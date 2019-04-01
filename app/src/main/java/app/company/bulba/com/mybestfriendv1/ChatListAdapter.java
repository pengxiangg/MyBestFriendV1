package app.company.bulba.com.mybestfriendv1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;

        private ChatViewHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Chat> mChats;

    ChatListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        if (mChats != null) {
            Chat current = mChats.get(position);
            holder.chatItemView.setText(current.getMessage());
            if(position>0) {
                Chat previousChat = mChats.get(position-1);
                String previousUser = previousChat.getUser();
                String currentUser = current.getUser();
                if(previousUser.equals(currentUser)){
                    holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble);
                } else {
                    holder.chatItemView.setBackgroundResource(R.drawable.rounded_edittext);
                }
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.chatItemView.setText("No Word");
        }
    }

    void setChats(List<Chat> words){
        mChats = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mChats != null)
            return mChats.size();
        else return 0;
    }

}
