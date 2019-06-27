package com.example.shoppinghelper;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {



    private final String SEARCH_BY_STRING = "Search_by_String";
    private final String SEARCH_BY_PICTURE = "Search_by_Picture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        setUpUi();
    }

    private void setUpUi() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flPrimary,new SearchByStringFragment(),this.SEARCH_BY_STRING);
        fragmentTransaction.add(R.id.flSecondary,new SearchByPictureFragment(),this.SEARCH_BY_PICTURE);
        fragmentTransaction.commit();
    }
}
