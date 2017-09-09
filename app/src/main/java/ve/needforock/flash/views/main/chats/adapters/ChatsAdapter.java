package ve.needforock.flash.views.main.chats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import ve.needforock.flash.R;
import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.models.Chat;

/**
 * Created by Soporte on 09-Sep-17.
 */

public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ChatHolder>{

    private ChatListener chatListener;


    public ChatsAdapter(ChatListener listener) {
        super(Chat.class, R.layout.list_item_chat, ChatHolder.class, new Nodes().userChat(new CurrentUser().getUid()));
        this.chatListener = listener;

    }

    @Override
    protected void populateViewHolder(final ChatHolder viewHolder, Chat model, int position) {

        viewHolder.email.setText(model.getReceiver());
        Picasso.with(viewHolder.photo.getContext()).load(model.getPhoto()).into(viewHolder.photo);
        if(model.isNotification()){
            viewHolder.notification.setVisibility(View.VISIBLE);
        }else{
            viewHolder.notification.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat auxChat = getItem(viewHolder.getAdapterPosition());
                chatListener.clicked(auxChat.getKey(), auxChat.getReceiver());
            }
        });

    }

    public static class ChatHolder extends RecyclerView.ViewHolder {
        private BubbleImageView photo;
        private TextView email;
        private View notification;

        public ChatHolder(View itemView) {
            super(itemView);
            photo = (BubbleImageView) itemView.findViewById(R.id.photoBiv);
            email = (TextView) itemView.findViewById(R.id.emailTv);
            notification = itemView.findViewById(R.id.notificationV);

        }


    }
}
