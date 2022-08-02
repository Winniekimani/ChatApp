package com.winnie.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.winnie.chatapp.Adapters.MessagesAdapter;
import com.winnie.chatapp.Models.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ConversationActivity extends AppCompatActivity {

    ArrayList<Message> messageArrayList;
    RecyclerView messages_rc;
    FloatingActionButton btn_send;
    EditText edt_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        messages_rc = findViewById(R.id.messages_rc);
        btn_send = findViewById(R.id.btn_send);
        edt_message = findViewById(R.id.edt_message);
        messageArrayList = new ArrayList<>();

        FirebaseFirestore.getInstance().collection("Chats")
                .orderBy("time", Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                if (error != null){
                                    Log.d("iujyhtbgvrf", error.getMessage());
                                }

                                //GET CHANGES
                                if (value != null){
                                    for (DocumentChange documentChange : value.getDocumentChanges()){
                                        if (documentChange.getType() == DocumentChange.Type.ADDED){ //CHECK ALL ADDED
                                            Message message = documentChange.getDocument().toObject(Message.class);
                                            messageArrayList.add(message);


                                        }
                                    }
                                    MessagesAdapter messagesAdapter = new MessagesAdapter( messageArrayList,ConversationActivity.this);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConversationActivity.this, LinearLayoutManager.VERTICAL, false);
                                    messages_rc.setAdapter(messagesAdapter);
                                    messages_rc.setLayoutManager(linearLayoutManager);

                                    messages_rc.smoothScrollToPosition(messageArrayList.size());


                                }

                            }
                        });



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_send.setEnabled(false);

                Message message = new Message();
                message.setMessage(edt_message.getText().toString());
                message.setSender_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                message.setTime(new Date().getTime());

                FirebaseFirestore.getInstance().collection("Chats")
                                .add(message)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                edt_message.setText("");
                                                btn_send.setEnabled(true);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                btn_send.setEnabled(true);
                                Toast.makeText(ConversationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });






            }
        });
    }
}