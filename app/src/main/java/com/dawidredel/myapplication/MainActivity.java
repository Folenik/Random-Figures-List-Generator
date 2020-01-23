package com.dawidredel.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // inicjalizacja zmiennych
    ImageView logoImageView;
    Button primaryActivityButton, presentationActivityButton, informationActivityButton;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); // wywołanie funkcji inicjalizacyjnej
    }

    //funkcja inicjalizująca wszystkie widoki
    public void init() {
        logoImageView = findViewById(R.id.logoImageView);
        primaryActivityButton = findViewById(R.id.primaryActivityButton);
        presentationActivityButton = findViewById(R.id.presentationActivityButton);
        informationActivityButton = findViewById(R.id.informationActivityButton);

        //metoda pozwalajaca przyciskom "nasluchiwac" czy zostaly wcisniete
        primaryActivityButton.setOnClickListener(this);
        presentationActivityButton.setOnClickListener(this);
        informationActivityButton.setOnClickListener(this);
    }

    //funkcja onClick obslugujaca klikniecia
    @Override
    public void onClick(View view) {

        //switch wywolujacy inne klasy w zaleznosci od wcisnietego przycisku
        switch (view.getId()) {
            case R.id.primaryActivityButton: //odwolanie się do buttonu o nazwie primaryActivityButton
                askForParameters(); // wywolanie funkcji pytajacej o parametry
                break;
            case R.id.presentationActivityButton: //odwolanie się do buttonu o nazwie presentationActivityButton
                openPresentationActivity(); // wywolanie funkcji przechodzacej do klasy PresentationActivity
                break;
            case R.id.informationActivityButton: //odwolanie się do buttonu o nazwie informationActivityButton
                openInformationActivity(); // wywolanie funkcji przechodzacej do klasy InformationActivity
                break;
        }
    }

    //funkcja pytajaca o parametry
    public void askForParameters() {

        buildDialogLayout(); // wywolanie funkcji ktora ustawia layout dla dialogboxa
        builder.show(); // pokazanie dialogboxa
    }

    public void buildDialogLayout() {

        LinearLayout layout = new LinearLayout(this); // stworzenie nowego layouta dla naszego buildera
        layout.setOrientation(LinearLayout.VERTICAL); //orientacja layouta

        final EditText inputFigures = new EditText(this); // stworzenie nowego EditTexta
        inputFigures.setInputType(InputType.TYPE_CLASS_NUMBER); // tryb wpisywania liczb
        inputFigures.setHint("Liczba figur do wygenerowania"); // ustawienie hinta

        final EditText rangeFrom = new EditText(this); // to samo
        rangeFrom.setInputType(InputType.TYPE_CLASS_NUMBER); // tryb wpisywania liczb
        rangeFrom.setHint("Najmniejsza wartość wymiaru");

        final EditText rangeTo = new EditText(this); // to samo
        rangeTo.setInputType(InputType.TYPE_CLASS_NUMBER); // tryb wpisywania liczb
        rangeTo.setHint("Największa wartość wymiaru");

        layout.addView(inputFigures); //dodanie widoku powyzszego edittexta do layouta
        layout.addView(rangeFrom);
        layout.addView(rangeTo);

        builder = new AlertDialog.Builder(this); // deklaracja nowego buildera

        builder.setTitle("Opcje"); // nazwa buildera
        builder.setView(layout); // widok layouta


        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String figuresNameText = inputFigures.getText().toString(); //pobranie wpisanego tekstu
                String rangeFromText = rangeFrom.getText().toString();
                String rangeToText = rangeTo.getText().toString();


                //zabezpieczenie żeby nie wpisać dolnej granicy przedziału większej od górnej granicy przedziału
                if (Integer.parseInt(rangeFromText) <= Integer.parseInt(rangeToText)) {
                    Intent intent = new Intent(MainActivity.this, PrimaryActivity.class); // przejście do klasy PrimaryActivity
                    intent.putExtra("figureCount", figuresNameText); //przekazanie zmiennej figuresNameText do nastepnej klasy
                    intent.putExtra("rangeFrom", rangeFromText);
                    intent.putExtra("rangeTo", rangeToText);
                    startActivity(intent);
                }
                //jeśli tak się stało to wyświetla się informacja i dialog się zamyka
                else {
                    Toast.makeText(MainActivity.this, "Zła wartość przedziału!", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

    }

    public void openPresentationActivity() {
        Intent intent = new Intent(MainActivity.this, PresentationActivity.class);
        startActivity(intent);
    }

    public void openInformationActivity() {
        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
        startActivity(intent);
    }

}
