package com.example.moviejunkie.Dialogs;


import com.example.moviejunkie.SqlLite.OfflineDataFilter;

public interface SearchDialogInterface {

    void onUrlCreation(String url);

    void onBookmarkFilter(OfflineDataFilter offlineDataFilter);

    void ResetNavbar();
}
