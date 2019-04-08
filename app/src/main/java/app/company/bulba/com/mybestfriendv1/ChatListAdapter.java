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

public class ChatListAdapter extends RecyclerView.Adapter {


    private final LayoutInflater mInflater;
    private List<Chat> mChats;
    private final String ownerMe = "OWNER_ME";
    private static final int VIEW_TYPE_MESSAGE_ME = 1;
    private static final int VIEW_TYPE_MESSAGE_BF = 2;

    ChatListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public int getItemViewType(int position) {
        Chat chat = mChats.get(position);

        if(chat.getUser().equals(ownerMe)) {
            return VIEW_TYPE_MESSAGE_ME;
        } else {
            return VIEW_TYPE_MESSAGE_BF;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_ME) {
            view = mInflater.inflate(R.layout.recyclerview_item_left, parent, false);
            return new MeMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BF) {
            view = mInflater.inflate(R.layout.recyclerview_item_right, parent, false);
            return new BfMessageHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mChats != null) {
            Chat current = mChats.get(position);
            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_ME:
                    ((MeMessageHolder) holder).bind(current);
                    break;
                case VIEW_TYPE_MESSAGE_BF:
                    ((BfMessageHolder) holder).bind(current);
            }
        }
    }

    class MeMessageHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;

        private MeMessageHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
        }

        void bind(Chat chat) {
            chatItemView.setText(chat.getMessage());
        }
    }

    class BfMessageHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;

        private BfMessageHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
        }

        void bind(Chat chat) {
            chatItemView.setText(chat.getMessage());
        }
    }

    void setChats(List<Chat> chats) {
        mChats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mChats!=null)
            return mChats.size();
        else return 0;
    }
}
