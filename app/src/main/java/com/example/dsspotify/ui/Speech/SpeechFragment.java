package com.example.dsspotify.ui.Speech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dsspotify.R;
import com.example.dsspotify.ui.Speech.Model.Message;
import com.example.dsspotify.ui.playlist.objects.Song;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SpeechFragment extends Fragment {

    private SpeechViewModel speechViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View root = inflater.inflate(R.layout.fragment_speech, container, false);

        ImageView imgView_microphone = root.findViewById(R.id.imgView_microphone);

        MessagesList messagesList = root.findViewById(R.id.messagesList);
      //  List<Message> MessageList = new ArrayList<>();
        String senderId = "khalil";
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, null);
        adapter.addToStart(new Message("1234","Play Lensko on Titsepoken 2015", new Date()),true);
        adapter.addToStart(new Message("1234","Pause the music", new Date()),true);
        adapter.addToStart(new Message("1234","Continue playing", new Date()),true);

        messagesList.setAdapter(adapter);

        //google speech
        imgView_microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // source : https://developer.android.com/reference/android/speech/RecognizerIntent
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Speak something...");
                try {
                    startActivityForResult(intent, 666);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(),
                            "Sorry! Speech recognition is not supported in this device.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

         /*
         speechViewModel =
                new ViewModelProvider(this).get(SpeechViewModel.class);
         final TextView textView = root.findViewById(R.id.text_home);
        speechViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

        return root;
    }
}