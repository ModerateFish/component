package com.thornbirds.repodemo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.thornbirds.framework.fragment.ComponentFragment;

/**
 * Created by yangli on 2017/9/7.
 */
public class TestFragment extends ComponentFragment {

    public static final String EXTRA_FRAGMENT_ID = "extra_fragment_id";

    private int mId = 0;

    @Override
    protected final String getTAG() {
        return "TestFragment";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            mId = getArguments().getInt(EXTRA_FRAGMENT_ID, hashCode());
        } else {
            Log.w(TAG, "onAttach arguments not found, fragment " + mId + ", hashCode=" + hashCode());
        }
        Log.d(TAG, "onAttach fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(EXTRA_FRAGMENT_ID, hashCode());
        } else {
            Log.w(TAG, "onCreate arguments not found, fragment " + mId + ", hashCode=" + hashCode());
        }
        Log.d(TAG, "onCreate fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView fragment " + mId + ", hashCode=" + hashCode());
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy fragment " + mId + ", hashCode=" + hashCode());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach fragment " + mId + ", hashCode=" + hashCode());
    }
}
