package ve.needforock.flash.views.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.ServerValue;

import ve.needforock.flash.R;
import ve.needforock.flash.data.CurrentUser;
import ve.needforock.flash.data.Nodes;
import ve.needforock.flash.models.Chat;
import ve.needforock.flash.views.main.chats.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Chat chat = (Chat) getIntent().getSerializableExtra(ChatsFragment.CHAT);
        key = chat.getKey();
        String title = getIntent().getStringExtra(ChatsFragment.CHAT);

        new Nodes().userChat(new CurrentUser().getUid()).child(key).child("timestamp").setValue(ServerValue.TIMESTAMP);

        getSupportActionBar().setTitle(chat.getReceiver());
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Nodes().userChat(new CurrentUser().getUid()).child(key).child("notification").setValue(false);
    }
}
