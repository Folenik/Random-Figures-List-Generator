package com.dawidredel.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class InformationActivity extends AppCompatActivity {

    TextView value1, value2, value3, value4;
    SharedPreferences pref; // do przechowywania danych aplikacji

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        init(); // inicjalizacja widoków
    }

    public void init() {

        value1  = findViewById(R.id.value1);
        value2  = findViewById(R.id.value2);
        value3  = findViewById(R.id.value3);
        value4  = findViewById(R.id.value4);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // pobranie kontekstu, wybór MyPref jako preferencji

        value1.setText(String.valueOf(pref.getInt("pole",0))); //pobranie wartości "pole" z SharedPreferences i wstawienie ich do value1
        value2.setText(String.valueOf(pref.getInt("prostokaty",0))); // jak wyzej
        value3.setText(String.valueOf(pref.getInt("trojkaty",0))); // jak wyzej
        value4.setText(String.valueOf(pref.getInt("kola",0))); // jak wyzej




    }
}
