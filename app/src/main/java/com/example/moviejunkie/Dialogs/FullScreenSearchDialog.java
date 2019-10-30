package com.example.moviejunkie.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviejunkie.Helper.InputFilterMinMax;
import com.example.moviejunkie.Dialogs.SearchDialogInterface;
import com.example.moviejunkie.R;
import com.example.moviejunkie.SqlLite.OfflineDataFilter;

import java.util.ArrayList;
import java.util.List;

public class FullScreenSearchDialog extends DialogFragment {

    private SearchDialogInterface mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback=(SearchDialogInterface) context;
        }catch (ClassCastException e){
            Toast.makeText(context, "Error:CUSTOM", Toast.LENGTH_SHORT).show();
        }
    }

    final String UrlBuildernoGenres = "https://api.themoviedb.org/" +
            "3/discover/movie?" +
            "api_key=77da696d93b5f500c3f18a0aba2691f9&language" +
            "=en-US&sort_by=popularity.desc&include_ad" +
            "ult=false&include_video=false" +
            "&primary_release_date.gte=1900-01-01" +
            "&primary_release_date.lte=2020-01-01" +
            "&vote_averag.gte=0&vote_average.lte=10";
    private String genreAddon="&with_genres=";

    private String ReturnString;


    private List<Integer> GenreValues=new ArrayList<Integer>();


                //Id-jevi u apiju su u komentaru sa strane
    private CheckBox checkBoxAction; //28
    private CheckBox checkBoxAdventure; //12
    private CheckBox checkBoxAnimation;//16
    private CheckBox checkBoxComedy; //35
    private CheckBox checkBoxCrime; //80
    private CheckBox checkBoxDocumentary; //99
    private CheckBox checkBoxDrama; //18
    private CheckBox checkBoxFamily; //10751
    private CheckBox checkBoxFantasy; //14
    private CheckBox checkBoxHistory; //36
    private CheckBox checkBoxHorror; //27
    private CheckBox checkBoxMusic; //10402
    private CheckBox checkBoxMystery;//9648
    private CheckBox checkBoxRomance;//10749
    private CheckBox checkBoxScienceFiction;//878
    private CheckBox checkBoxThriller;//53
    private CheckBox checkBoxWar;//10752
    private CheckBox checkBoxWestern;//37


    private EditText MinRating;
    private EditText MaxRating;
    private EditText MinYear;
    private EditText MaxYear;

    private Button Reset;
    private Button Find;

    public static String TAG = "FullScreenSearchDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.search_full_screen_dialog, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
      //  toolbar.setNavigationIcon(R.drawable.search);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        toolbar.setTitle("Search");

        checkBoxAction=view.findViewById(R.id.checkBoxAction);
        checkBoxAdventure=view.findViewById(R.id.checkBoxAdventure);
        checkBoxAnimation=view.findViewById(R.id.checkBoxAnimation);
        checkBoxComedy=view.findViewById(R.id.checkBoxComedy);
        checkBoxCrime=view.findViewById(R.id.checkBoxCrime);
        checkBoxDocumentary=view.findViewById(R.id.checkBoxDocumentary);
        checkBoxDrama=view.findViewById(R.id.checkBoxDrama);
        checkBoxFamily=view.findViewById(R.id.checkBoxFamily);
        checkBoxFantasy=view.findViewById(R.id.checkBoxFantasy);
        checkBoxHistory=view.findViewById(R.id.checkBoxHistory);
        checkBoxHorror=view.findViewById(R.id.checkBoxHorror);
        checkBoxMusic=view.findViewById(R.id.checkBoxMusic);
        checkBoxMystery=view.findViewById(R.id.checkBoxMystery);
        checkBoxRomance=view.findViewById(R.id.checkBoxRomance);
        checkBoxScienceFiction=view.findViewById(R.id.checkBoxScienceFiction);
        checkBoxThriller=view.findViewById(R.id.checkBoxThriller);
        checkBoxWar=view.findViewById(R.id.checkBoxWar);
        checkBoxWestern=view.findViewById(R.id.checkBoxWestern);


        MinYear=view.findViewById(R.id.StartYear);
        MaxYear=view.findViewById(R.id.EndYear);



        MaxRating=view.findViewById(R.id.MaxRating);
        MaxRating.setFilters(new InputFilterMinMax[]{new InputFilterMinMax("0","10")});

        MinRating=view.findViewById(R.id.minRating);
        MinRating.setFilters(new InputFilterMinMax[]{new InputFilterMinMax("0","10")});

        Find=view.findViewById(R.id.Search);
        Reset=view.findViewById(R.id.Reset);

        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_ResetSearch();
            }
        });


        return view;
    }

    private void do_ResetSearch() {

        mCallback.onUrlCreation("RESET");
        getDialog().dismiss();

    }

    private void Filter() {


        if(isAdded()) {

            Log.d(TAG, "Simple Name: "+getActivity().getClass().getSimpleName()); //MainActivity
        }



        if (checkBoxAction.isChecked())
            GenreValues.add(28);

        if (checkBoxAdventure.isChecked())
            GenreValues.add(12);

        if (checkBoxAnimation.isChecked())
            GenreValues.add(16);

        if (checkBoxComedy.isChecked())
            GenreValues.add(35);

        if (checkBoxCrime.isChecked())
            GenreValues.add(80);

        if (checkBoxDocumentary.isChecked())
            GenreValues.add(99);

        if (checkBoxDrama.isChecked())
            GenreValues.add(18);

        if (checkBoxFamily.isChecked())
            GenreValues.add(10751);

        if (checkBoxFantasy.isChecked())
            GenreValues.add(14);

        if (checkBoxHistory.isChecked())
            GenreValues.add(36);

        if (checkBoxHorror.isChecked())
            GenreValues.add(27);

        if (checkBoxMusic.isChecked())
            GenreValues.add(10402);

        if (checkBoxMystery.isChecked())
            GenreValues.add(9648);

        if (checkBoxRomance.isChecked())
            GenreValues.add(10749);

        if (checkBoxScienceFiction.isChecked())
            GenreValues.add(878);

        if (checkBoxThriller.isChecked())
            GenreValues.add(53);

        if (checkBoxWar.isChecked())
            GenreValues.add(10752);

        if (checkBoxWestern.isChecked())
            GenreValues.add(37);



        if (getActivity().getClass().getSimpleName()=="MainActivity"){

            String YearBegin="-01-01";
            String YearEnd="-12-30";

            ReturnString = UrlBuildernoGenres;

            if (!MinRating.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Trigger", Toast.LENGTH_SHORT).show();
                ReturnString=ReturnString.replace("vote_average.gte=0", "vote_average.gte=" + MinRating.getText().toString());
            }
            if (!MaxRating.getText().toString().isEmpty()) {
                ReturnString=ReturnString.replace("vote_average.lte=10", "vote_average.lte=" + MaxRating.getText().toString());
            }

            if(!MinYear.getText().toString().isEmpty())
                ReturnString=ReturnString.replace("primary_release_date.gte=1900-01-01","primary_release_date.gte="+MinYear.getText().toString()+YearBegin);

            if(!MaxYear.getText().toString().isEmpty())
                ReturnString=ReturnString.replace("primary_release_date.lte=2020-01-01","primary_release_date.lte="+MaxYear.getText().toString()+YearEnd);



            if (GenreValues.size()>0) {
                String Genres = genreAddon;

                for (Integer i: GenreValues) {

                    Genres=Genres+i.toString()+",";

                }

                if (Genres.charAt(Genres.length() - 1) == ',')
                    Genres = Genres.substring(0, Genres.length() - 1);

                ReturnString+=Genres;
                GenreValues.clear();
            }



            Toast.makeText(getActivity(), "RETURN STRING:"+ReturnString, Toast.LENGTH_LONG).show();
            mCallback.onUrlCreation(ReturnString);
            getDialog().dismiss();


        }else{

            OfflineDataFilter dataFilter=new OfflineDataFilter();

            dataFilter.setGenre_ids(new ArrayList<>(GenreValues));
            GenreValues.clear();


            if (!MinRating.getText().toString().isEmpty()) {
                dataFilter.setVote_min( Integer.valueOf(MinRating.getText().toString()));
            }
            if (!MaxRating.getText().toString().isEmpty()) {
                dataFilter.setVote_max( Integer.valueOf(MaxRating.getText().toString()));

            }

            if(!MinYear.getText().toString().isEmpty())
                dataFilter.setRelease_date_min((MinYear.getText().toString()));

            if(!MaxYear.getText().toString().isEmpty())
                dataFilter.setRelease_date_max((MaxYear.getText().toString()));

            mCallback.onBookmarkFilter(dataFilter);
            getDialog().dismiss();



        }





    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);

        mCallback.ResetNavbar();
        dialog.dismiss();
    }
}