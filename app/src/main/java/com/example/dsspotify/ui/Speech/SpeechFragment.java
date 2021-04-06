package com.example.dsspotify.ui.Speech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/*
 *ce fragments représente l'echange de paroles entre le client et le serveur de reconnaissance de parole
 *ainsi l'analyseur de requete
 *
 * @author khalil-Elf441
 *
 */
public class SpeechFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_speech, container, false);

        ImageView imgView_microphone = root.findViewById(R.id.imgView_microphone);
        TextView txtview_record_idicator = root.findViewById(R.id.txtview_record_idicator);

        MessagesList messagesList = root.findViewById(R.id.messagesList);
        String senderId = "khalil";

        //examples d'echange des messages
        MessagesListAdapter<Message> adapter = new MessagesListAdapter<>(senderId, null);
        adapter.addToStart(new Message("0","Play Lensko on Titsepoken 2015", new Date(),senderId),true);
        adapter.addToStart(new Message("1","Playing Lensko on Titsepoken 2015", new Date(),"Server"),true);
        adapter.addToStart(new Message("2","Pause the music", new Date(),senderId),true);
        adapter.addToStart(new Message("3","Music paused", new Date(),"Server"),true);
        adapter.addToStart(new Message("4","Continue playing", new Date(),senderId),true);
        adapter.addToStart(new Message("5","Playing Lensko on Titsepoken 2015", new Date(),"Server"),true);

        messagesList.setAdapter(adapter);

        final Boolean[] recordFlag = {false};

        //Solutions 2 : simple Media recorder
        //https://developer.android.com/guide/topics/media/mediarecorder
        File dir = Environment.getExternalStorageDirectory();
        File audiofile = null;
        try {
            audiofile = File.createTempFile("sound", ".3gp", dir);
        } catch (IOException e) {
            Log.e("TAG", "external storage access error");
        }

        MediaRecorder mediaRecorder = new MediaRecorder();


        File finalAudiofile = audiofile;
        imgView_microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // SpeechToText();
                if(!recordFlag[0]){

                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.setOutputFile(finalAudiofile.getAbsolutePath());

                    try {
                        mediaRecorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaRecorder.start();

                    txtview_record_idicator.setText("Audio recording ... ");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgView_microphone.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_stop_speech_24, getContext().getTheme()));
                    } else {
                        imgView_microphone.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_stop_speech_24));
                    }
                    recordFlag[0] = true;
                }else{
                    mediaRecorder.stop();
                    txtview_record_idicator.setText("Recoding Stopped ... ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imgView_microphone.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_speech_24, getContext().getTheme()));
                    } else {
                        imgView_microphone.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_mic_speech_24));
                    }

                    adapter.addToStart(new Message("6","Play Titsepoken 2015", new Date(),"khalil"),true);
                    recordFlag[0] = false;
                }

            }
        });

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