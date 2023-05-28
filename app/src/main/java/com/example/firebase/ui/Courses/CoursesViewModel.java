package com.example.firebase.ui.Courses;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoursesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;


    public CoursesViewModel(MutableLiveData<String> mText, MutableLiveData<String> mText1) {
        this.mText = mText1;
        mText= new MutableLiveData<>();
        mText.setValue("This is Courses fragment");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}