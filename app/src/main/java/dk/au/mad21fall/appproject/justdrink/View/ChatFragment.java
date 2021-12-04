package dk.au.mad21fall.appproject.justdrink.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.appproject.justdrink.HelperClasses.JustDrinkViewModelFactory;
import dk.au.mad21fall.appproject.justdrink.Model.ListLogic.MessageAdapter;
import dk.au.mad21fall.appproject.justdrink.Model.FirebaseChatMessage;
import dk.au.mad21fall.appproject.justdrink.Model.MemberData;
import dk.au.mad21fall.appproject.justdrink.Model.Message;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;
import dk.au.mad21fall.appproject.justdrink.R;
import dk.au.mad21fall.appproject.justdrink.ViewModel.ChatFragmentViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    RecyclerView chatView;
    EditText chatText;
    ImageButton sendBtn;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    boolean me;
    int messageCount;
    ChatFragmentViewModel vm;

    String UUID;
    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me=true;
        messageCount = 0;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        vm = new JustDrinkViewModelFactory(getContext()).create(ChatFragmentViewModel.class);
        vm.setContext(getContext());

        chatView = v.findViewById(R.id.chatList);
        chatText = v.findViewById(R.id.messageText);
        sendBtn = v.findViewById(R.id.sendMsg);

        UUID = "";


        //Initialize adapter
        MessageAdapter adapter = new MessageAdapter(getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        chatView.setLayoutManager(layoutManager);
        chatView.setItemAnimator(new DefaultItemAnimator());
        chatView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();

        DatabaseReference chatRef = database.getReference("Chat");


        vm.getUUID().observe(this.getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                UUID=s;

                chatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> snapshots = snapshot.getChildren();
                        if(messageCount>0) {
                            for(int i=0;i<messageCount;i++) {
                                snapshots.iterator().next();
                            }
                        }
                        while(snapshots.iterator().hasNext()) {
                            FirebaseChatMessage fireMsg = snapshots.iterator().next().getValue(FirebaseChatMessage.class);
                            MemberData memberData = new MemberData("Bruger","Blue");
                            boolean isYou;

                            if(fireMsg.userUUID.equals(UUID)) {
                                isYou = true;
                            } else {
                                isYou = false;
                            }
                            Message msg = new Message(fireMsg.textBody,memberData,isYou);
                            adapter.addMessage(msg);
                            messageCount++;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemberData mem = new MemberData("Martin","Blue");
                Message msg = new Message(chatText.getText().toString(),mem,me);
                //adapter.addMessage(msg);

                FirebaseChatMessage fMsg = new FirebaseChatMessage();
                fMsg.chatRoom = "Abar";
                fMsg.textBody = chatText.getText().toString();
                fMsg.timestamp = System.currentTimeMillis();
                fMsg.userUUID = FirebaseAuth.getInstance().getUid();
                String msgId = database.getReference().push().getKey();
                DatabaseReference msgRef = database.getReference("Chat/"+msgId);
                msgRef.setValue(fMsg);

                chatText.setText("");

            }
        });

        return v;
    }
}