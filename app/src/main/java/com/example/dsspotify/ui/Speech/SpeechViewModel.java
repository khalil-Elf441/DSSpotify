package com.example.dsspotify.ui.Speech;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpeechViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SpeechViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Speech fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}