package com.example.firebase.ui.shop;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firebase.R;

public class shopFragment extends Fragment {

    private ShopViewModel mViewModel;

    public static shopFragment newInstance() {
        return new shopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        final TextView textView=root.findViewById(R.id.txtshop);
        textView.setText("Hello to shop page");
        return root;
    }




}