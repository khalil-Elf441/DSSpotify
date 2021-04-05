package com.example.dsspotify.ui.playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<RecyclerView> recycleView;

    public PlaylistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}