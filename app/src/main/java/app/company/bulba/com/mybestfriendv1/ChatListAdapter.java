package app.company.bulba.com.mybestfriendv1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private static final int VIEW_TYPE_MESSAGE_ME_CORNER = 2;
    private static final int VIEW_TYPE_MESSAGE_BF = 3;
    private static final int VIEW_TYPE_MESSAGE_BF_CORNER = 4;

    ChatListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public int getItemViewType(int position) {
        Chat chat = mChats.get(position);

        if(chat.getUser().equals(ownerMe)) {
            if(position == mChats.size()-1) {
                return VIEW_TYPE_MESSAGE_ME_CORNER;
            }
            if(chat.getUser().equals(mChats.get(position+1).getUser())) {
                return VIEW_TYPE_MESSAGE_ME;
            } else {
                return VIEW_TYPE_MESSAGE_ME_CORNER;
            }
        } else {
            if(position == mChats.size()-1) {
                return VIEW_TYPE_MESSAGE_BF_CORNER;
            }
            if(chat.getUser().equals(mChats.get(position+1).getUser())) {
                return VIEW_TYPE_MESSAGE_BF;
            } else {
                return VIEW_TYPE_MESSAGE_BF_CORNER;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == VIEW_TYPE_MESSAGE_ME || viewType == VIEW_TYPE_MESSAGE_ME_CORNER) {
            view = mInflater.inflate(R.layout.recyclerview_item_right, parent, false);
            return new MeMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_BF || viewType == VIEW_TYPE_MESSAGE_BF_CORNER) {
            view = mInflater.inflate(R.layout.recyclerview_item_left, parent, false);
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
                    ((MeMessageHolder) holder).bind(current, false);
                    break;
                case VIEW_TYPE_MESSAGE_ME_CORNER:
                    ((MeMessageHolder) holder).bind(current, true);
                    break;
                case VIEW_TYPE_MESSAGE_BF:
                    ((BfMessageHolder) holder).bind(current, false);
                    break;
                case VIEW_TYPE_MESSAGE_BF_CORNER:
                    ((BfMessageHolder) holder).bind(current, true);
                    break;
            }
        }
    }

    class MeMessageHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;
        private final ImageView cornerRightIImageView;

        private MeMessageHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
            cornerRightIImageView = itemView.findViewById(R.id.corner_view_right);
        }

        void bind(Chat chat, boolean isCorner) {
            chatItemView.setText(chat.getMessage());
            if(isCorner) {
                chatItemView.setBackgroundResource(R.drawable.chat_bubble_v2);
            } else {
                cornerRightIImageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    class BfMessageHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;
        private final ImageView cornerLeftImageView;

        private BfMessageHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
            cornerLeftImageView = itemView.findViewById(R.id.corner_view_left);
        }

        void bind(Chat chat, boolean isCorner) {
            chatItemView.setText(chat.getMessage());
            if(isCorner) {
                chatItemView.setBackgroundResource(R.drawable.chat_bubble_v3);
            } else {
                cornerLeftImageView.setVisibility(View.INVISIBLE);
            }
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
