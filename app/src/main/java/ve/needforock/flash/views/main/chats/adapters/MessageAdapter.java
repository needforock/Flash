package ve.needforock.flash.views.main.chats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import ve.needforock.flash.R;
import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.models.Message;

/**
 * Created by Soporte on 11-Sep-17.
 */

public class MessageAdapter extends FirebaseRecyclerAdapter <Message, MessageAdapter.MessageHolder> {

    private MessageCallBack messageCallBack;
    private final String CURRENT_EMAIL = new CurrentUser().userEmail();

    public MessageAdapter(String chatId, MessageCallBack callBack) {
        super(Message.class, R.layout.list_item_message, MessageHolder.class, new Nodes().messages(chatId));
        this.messageCallBack = callBack;
    }

    @Override
    protected void populateViewHolder(MessageHolder viewHolder, Message model, int position) {
        TextView textView = (TextView) viewHolder.itemView;
        if (CURRENT_EMAIL.equals(model.getOwner())){
            textView.setGravity(Gravity.RIGHT);
        }else{
            textView.setGravity(Gravity.LEFT);
        }
        textView.setText(model.getContent());

    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        messageCallBack.update();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{

        public MessageHolder(View itemView) {
            super(itemView);
        }
    }
}
