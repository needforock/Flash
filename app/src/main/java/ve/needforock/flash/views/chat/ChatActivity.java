package ve.needforock.flash.views.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ve.needforock.flash.R;
import ve.needforock.flash.views.main.chats.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String key = getIntent().getStringExtra(ChatsFragment.CHAT_KEY);
        String title = getIntent().getStringExtra(ChatsFragment.CHAT_TITLE);

        getSupportActionBar().setTitle(title);
    }
}
