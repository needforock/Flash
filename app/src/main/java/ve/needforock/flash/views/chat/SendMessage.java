package ve.needforock.flash.views.chat;

import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.models.Chat;
import ve.needforock.flash.models.Message;

/**
 * Created by Soporte on 11-Sep-17.
 */

public class SendMessage {

    public void validateMessage(String message, Chat chat){
        if(message.trim().length()>0){
            String email = new CurrentUser().userEmail();
            Message msg = new Message();
            msg.setContent(message);
            msg.setOwner(email);
            String key = chat.getKey();

            new Nodes().messages(key).push().setValue(msg);
            new Nodes().userChat(chat.getUid()).child(key).child("notification").setValue(true);
        }
    }
}
