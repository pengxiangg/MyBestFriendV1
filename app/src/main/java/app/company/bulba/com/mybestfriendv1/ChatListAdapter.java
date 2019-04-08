package app.company.bulba.com.mybestfriendv1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zachary on 30/03/2019.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView chatItemView;
        private final ImageView cornerRightImageView;
        private final ImageView cornerLeftImageView;
        private final LinearLayout fullItemView;

        private ChatViewHolder(View itemView) {
            super(itemView);
            chatItemView = itemView.findViewById(R.id.textView);
            cornerRightImageView = itemView.findViewById(R.id.corner_view_right);
            cornerLeftImageView = itemView.findViewById(R.id.corner_view_left);
            fullItemView = itemView.findViewById(R.id.full);
        }
    }

    //TODO: getView method??

    private final LayoutInflater mInflater;
    private List<Chat> mChats;
    private final String ownerMe = "OWNER_ME";
    private final String ownerBF = "OWNER_BEST_FRIEND";

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
            String owner = current.getUser();

            if (owner.equals(ownerMe)) {
                RelativeLayout.LayoutParams paramsRight = (RelativeLayout.LayoutParams) holder.fullItemView.getLayoutParams();
                paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                holder.fullItemView.setLayoutParams(paramsRight);

                if (position == mChats.size() - 1) {
                    holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble_v2);
                    holder.cornerRightImageView.setVisibility(View.VISIBLE);
                    holder.cornerLeftImageView.setVisibility(View.GONE);
                } else if (position >= 0 && position < mChats.size() - 1) {
                    String nextUser = mChats.get(position + 1).getUser();
                    String currentUser = current.getUser();

                    if (currentUser.equals(nextUser)) {
                        holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble);
                        holder.cornerRightImageView.setVisibility(View.INVISIBLE);
                        holder.cornerLeftImageView.setVisibility(View.GONE);
                    } else {
                        holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble_v2);
                        holder.cornerRightImageView.setVisibility(View.VISIBLE);
                        holder.cornerLeftImageView.setVisibility(View.GONE);
                    }
                }

            } else if (owner.equals(ownerBF)) {
                RelativeLayout.LayoutParams paramsLeft = (RelativeLayout.LayoutParams) holder.fullItemView.getLayoutParams();
                paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                holder.fullItemView.setLayoutParams(paramsLeft);

                if (position == mChats.size() - 1) {
                    holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble_v3);
                    holder.cornerRightImageView.setVisibility(View.GONE);
                    holder.cornerLeftImageView.setVisibility(View.VISIBLE);
                } else if (position >= 0 && position < mChats.size() - 1) {
                    String nextUser = mChats.get(position + 1).getUser();
                    String currentUser = current.getUser();

                    if (currentUser.equals(nextUser)) {
                        holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble);
                        holder.cornerRightImageView.setVisibility(View.GONE);
                        holder.cornerLeftImageView.setVisibility(View.INVISIBLE);
                    } else {
                        holder.chatItemView.setBackgroundResource(R.drawable.chat_bubble_v3);
                        holder.cornerRightImageView.setVisibility(View.GONE);
                        holder.cornerLeftImageView.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                // Covers the case of data not being ready yet.
                holder.chatItemView.setText("No Word");
            }
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
