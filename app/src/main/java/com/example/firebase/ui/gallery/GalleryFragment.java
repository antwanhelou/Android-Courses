package com.example.firebase.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebase.R;
import com.example.firebase.ui.gallery.GalleryViewModel;


public class GalleryFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        final int[] index = {0};
        int[] arrpic = new int[]{R.drawable.ort, R.drawable.ort2, R.drawable.ort3};
        Button next = (Button) root.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView mypic = root.findViewById(R.id.imageview);
                mypic.setImageResource(arrpic[index[0]]);
                index[0]++;
                if (index[0] > arrpic.length-1)
                    index[0] = 0;

            }

        } );
        return root;
    }
}
