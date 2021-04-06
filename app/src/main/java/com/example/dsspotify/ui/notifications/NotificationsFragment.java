package com.example.dsspotify.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dsspotify.R;


/*
 *Ce fragment est réservé pour les notifications entre les clients et les autres serveurs / logs ...
 *
 * @author khalil-Elf441
 *
 */
public class NotificationsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_communication, container, false);

        return root;
    }
}