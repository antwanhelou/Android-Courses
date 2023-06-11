package com.example.firebase.ui.shop;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {
    private final MutableLiveData <String> mtext;
    public ShopViewModel(){
        mtext=new MutableLiveData<>();
        mtext.setValue("This is contact fragment");
    }
    public MutableLiveData<String> getText(){
        return  mtext;
    }
}