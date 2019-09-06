package com.kotlin.module.java;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kotlin.demo.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JavaSimpleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_java_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        toastDemo(1);
        toastDemo(12);
        toastDemo(123);
    }

    private void toastDemo(int number) {
        Disposable disposable = Observable.create(emitter -> {
            Log.d("JavaSimple", "start " + number);
            Thread.sleep(3000);
            Log.d("JavaSimple", "end " + number);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Toast.makeText(getContext(), "number [ " + number + " ]", Toast.LENGTH_SHORT).show();
                });
    }
}
