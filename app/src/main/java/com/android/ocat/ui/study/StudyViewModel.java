package com.android.ocat.ui.study;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StudyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is study fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}