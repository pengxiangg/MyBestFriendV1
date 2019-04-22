package app.company.bulba.com.mybestfriendv1;


import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

public class ChatListAdapter extends RecyclerView.Adapter {


    private final LayoutInflater mInflater;
    private List<Chat> mChats;
    private boolean multiSelect = false;
    private List<Chat> selectedChats = new ArrayList<Chat>();
    private final String ownerMe = "OWNER_ME";
    private static final int VIEW_TYPE_MESSAGE_ME = 1;
    private static final int VIEW_TYPE_MESSAGE_ME_CORNER = 2;
    private static final int VIEW_TYPE_MESSAGE_BF = 3;
    private static final int VIEW_TYPE_MESSAGE_BF_CORNER = 4;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    ChatListAdapter(Context context, OnDeleteButtonClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.onDeleteButtonClickListener = listener;
    }

    public interface OnDeleteButtonClickListener{
        void onDeleteButtonClicked(Chat chat);
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            menu.add("Delete");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            for(Chat chat : selectedChats){
                if(onDeleteButtonClickListener!=null){
                    onDeleteButtonClickListener.onDeleteButtonClicked(chat);
                }
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedChats.clear();
            notifyDataSetChanged();
        }
    };

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
        private final ImageView cornerRightImageView;
        private final ConstraintLayout constraintLayout;
        private final ConstraintLayout fullRow;
        private final TextView timeItemView;



        private MeMessageHolder(final View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
            cornerRightImageView = itemView.findViewById(R.id.corner_view_right);
            constraintLayout = itemView.findViewById(R.id.chat_bubble_id);
            timeItemView = itemView.findViewById(R.id.text_message_time);
            fullRow = itemView.findViewById(R.id.right_full);

        }

        void bind(final Chat chat, boolean isCorner) {
            chatItemView.setText(chat.getMessage());
            timeItemView.setText(chat.convertUnixTimeToString("h:mm a"));

            if(isCorner) {
                constraintLayout.setBackgroundResource(R.drawable.chat_bubble_v2);
            } else {
                cornerRightImageView.setVisibility(View.INVISIBLE);
            }

            if(selectedChats.contains(chat)) {
                fullRow.setBackgroundColor(Color.LTGRAY);
            } else {
                fullRow.setBackgroundColor(Color.TRANSPARENT);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectChat(chat);
                    return true;
                }
            });
        }

        void selectChat(Chat chat) {
            if(multiSelect) {
                if(selectedChats.contains(chat)){
                    selectedChats.remove(chat);
                    fullRow.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selectedChats.add(chat);
                    fullRow.setBackgroundColor(Color.LTGRAY);
                }
            }
        }


    }

    class BfMessageHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;
        private final ImageView cornerLeftImageView;
        private final ConstraintLayout constraintLayout;
        private final TextView timeItemView;
        private final ConstraintLayout fullRow;

        private BfMessageHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
            cornerLeftImageView = itemView.findViewById(R.id.corner_view_left);
            constraintLayout = itemView.findViewById(R.id.chat_bubble_id);
            timeItemView = itemView.findViewById(R.id.text_message_time);
            fullRow = itemView.findViewById(R.id.left_full);
        }

        void bind(final Chat chat, boolean isCorner) {
            chatItemView.setText(chat.getMessage());
            timeItemView.setText(chat.convertUnixTimeToString("h:mm a"));

            if(isCorner) {
                constraintLayout.setBackgroundResource(R.drawable.chat_bubble_v3);
            } else {
                cornerLeftImageView.setVisibility(View.INVISIBLE);
            }

            if(selectedChats.contains(chat)) {
                fullRow.setBackgroundColor(Color.LTGRAY);
            } else {
                fullRow.setBackgroundColor(Color.TRANSPARENT);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                    selectChat(chat);
                    return true;
                }
            });
        }

        void selectChat(Chat chat) {
            if(multiSelect) {
                if(selectedChats.contains(chat)){
                    selectedChats.remove(chat);
                    fullRow.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selectedChats.add(chat);
                    fullRow.setBackgroundColor(Color.LTGRAY);
                }
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

    public Chat getChatAtPosition (int position) {
        return mChats.get(position);
    }
}
