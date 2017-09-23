package ve.needforock.flash.views.main.finder;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.EmailProcessor;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.data.PhotoPreference;
import ve.needforock.flash.models.Chat;
import ve.needforock.flash.models.LocalUser;

/**
 * Created by Soporte on 05-Sep-17.
 */

public class UserValidation {

    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email){
        if(email.trim().length()>0){
            if(email.contains("@")){
                String currentUser = new CurrentUser().userEmail();
                if(!currentUser.equals(email)){
                    findUser(email);

                }else{
                    callback.error("Chat contigo mismo?");
                }

            }else{
                callback.error("Email mal escrito");

            }

        }else{
            callback.error("Se necesita un emal");
        }

    }

    private void findUser(String email){
        new Nodes().user(new EmailProcessor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LocalUser otherUser = dataSnapshot.getValue(LocalUser.class);

                if (otherUser != null){
                    createChats(otherUser);

                }else{
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createChats(LocalUser otherUser){
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();
        String currentUid = currentUser.getUid();
        String otherUid = otherUser.getUid();

        String key = new EmailProcessor().keyEmails(otherUser.getEmail());

        Chat currentChat = new Chat();
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setReceiver(otherUser.getEmail());
        currentChat.setKey(key);
        currentChat.setNotification(true);
        currentChat.setUid(otherUid);

        Chat otherChat = new Chat();
        otherChat.setNotification(true);
        otherChat.setKey(key);
        otherChat.setReceiver(currentUser.getEmail());
        otherChat.setPhoto(photo);
        otherChat.setUid(currentUid);

        new Nodes().userChat(currentUid).child(key).setValue(currentChat);
        new Nodes().userChat(otherUid).child(key).setValue(otherChat);

        callback.success();
    }
}
