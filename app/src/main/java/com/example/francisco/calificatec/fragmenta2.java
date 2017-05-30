package com.example.francisco.calificatec;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FRANCISCO on 06/04/2017.
 */

public class fragmenta2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View ver;
        ver = inflater.inflate(R.layout.fragmento2,container);
        return ver;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
