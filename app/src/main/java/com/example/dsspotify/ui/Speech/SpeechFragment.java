package com.example.dsspotify.ui.Speech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dsspotify.R;
import com.example.dsspotify.ui.Speech.Model.Message;
import com.example.dsspotify.ui.playlist.objects.Song;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.dsspotify.MainActivity.mediaPlayer;


/*
 *ce fragments représente l'echange de paroles entre le client et le serveur de reconnaissance de parole
 *ainsi l'analyseur de requete
 *
 * @author khalil-Elf441
 *
 */
public class SpeechFragment extends Fragment {


    public static MessagesListAdapter<Message> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_speech, container, false);
        ;





       // TextView txtview_record_idicator = root.findViewById(R.id.txtview_record_idicator);

        MessagesList messagesList = root.findViewById(R.id.messagesList);
        String senderId = "khalil";

        //examples d'echange des messages
        adapter = new MessagesListAdapter<>(senderId, null);
        adapter.addToStart(new Message("0","jouer piano", new Date(),senderId),true);
        adapter.addToStart(new Message("1","ok", new Date(),"Server"),true);
        adapter.addToStart(new Message("2","stopper piano", new Date(),senderId),true);
        adapter.addToStart(new Message("3","ok", new Date(),"Server"),true);
        adapter.addToStart(new Message("2","continuer piano", new Date(),senderId),true);
        adapter.addToStart(new Message("3","ok", new Date(),"Server"),true);

        messagesList.setAdapter(adapter);

        final Boolean[] recordFlag = {false};




        return root;
    }




    //Solution 1 : google speech pour record des audios
    // source : https://developer.android.com/reference/android/speech/RecognizerIntent
    public void SpeechToText() {
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


}



