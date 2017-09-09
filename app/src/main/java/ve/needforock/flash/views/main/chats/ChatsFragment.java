package ve.needforock.flash.views.main.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ve.needforock.flash.R;
import ve.needforock.flash.views.chat.ChatActivity;
import ve.needforock.flash.views.main.chats.adapters.ChatListener;
import ve.needforock.flash.views.main.chats.adapters.ChatsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment implements ChatListener {

    public static final String CHAT_KEY = "ve.needforock.flash.views.main.chats.KEY.CHAT_KEY" ;
    public static final String CHAT_TITLE = "ve.needforock.flash.views.main.chats.KEY.CHAT_TITLE";
    private RecyclerView recyclerView;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.chatsRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.VERTICAL));

        ChatsAdapter chatsAdapter = new ChatsAdapter(this);
        recyclerView.setAdapter(chatsAdapter);

    }

    @Override
    public void clicked(String key, String title) {
        Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(CHAT_KEY, key);
        intent.putExtra(CHAT_TITLE, title);
        startActivity(intent);

    }
}
