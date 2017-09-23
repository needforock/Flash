package ve.needforock.flash.views.chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ve.needforock.flash.R;
import ve.needforock.flash.models.Chat;
import ve.needforock.flash.views.main.chats.ChatsFragment;
import ve.needforock.flash.views.main.chats.adapters.MessageAdapter;
import ve.needforock.flash.views.main.chats.adapters.MessageCallBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements MessageCallBack{

    private MessageAdapter adapter;
    private RecyclerView recyclerView;


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void update() {

        recyclerView.scrollToPosition(adapter.getItemCount() -1 );

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Chat chat = (Chat) getActivity().getIntent().getSerializableExtra(ChatsFragment.CHAT);
        String key = chat.getKey();

        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new MessageAdapter(key, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.cleanup();
    }
}
