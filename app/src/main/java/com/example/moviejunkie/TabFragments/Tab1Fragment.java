package com.example.moviejunkie.TabFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviejunkie.R;

public class Tab1Fragment extends Fragment {

    private TextView OverviewText;
    private String Overview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


      return inflater.inflate(R.layout.fragment_tab1, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //OverviewText=(TextView) getActivity().findViewById(R.id.MovieInfoTab1Overview);
        OverviewText= getView().findViewById(R.id.MovieInfoTab1Overview);

        if (getArguments()!=null)
            Overview=getArguments().getString("MOVIE_OVERVIEW");


        Log.d("MOVIE_OVERVIEW",Overview);

        if (OverviewText!=null)
            OverviewText.setText(Overview); //"TEST TEST"


    }
}
