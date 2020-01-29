package com.dawidredel.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawidredel.myapplication.Models.Figure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PrimaryActivity extends AppCompatActivity {

    private List<Figure> figureList = new ArrayList<>(); //stworzenie listy figureList; List<Figure> oznacza ze to lista korzystajaca z klasy Figure
    private RecyclerView recyclerView; // do obslugi listy
    private FigureAdapter mAdapter; // adapter zarzadzajacy recyclerView

    Bundle bundle; // bundle do odebrania informacji z MainActivity
    String figuresCount, rangeFrom, rangeTo;
    Integer figuresCountInt, rangeFromInt, rangeToInt;
    Toolbar mToolbar;
    TextView tv1,tv2,tv3,tv4;
    SharedPreferences pref, currentPref; // do przechowywania danych aplikacji
    SharedPreferences.Editor editor, currentEditor; // do edycji tych danych
    Integer getPrefArea, getPrefRectangle, getPrefTriangle, getPrefCircle;
    Integer getPrefArea2, getPrefRectangle2, getPrefTriangle2, getPrefCircle2;
    Integer helpVar1 = 0;
    Integer helpVar2 = 0;
    Integer helpVar3 = 0;
    Integer helpVar4 = 0;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        retrieveBundle(); //odzyskanie przekazywanych stringow z klasy MainActivity
        init(); // pelna inicjalizacja
    }


    public void retrieveBundle() {
        bundle = getIntent().getExtras(); // odwolanie sie do bundla
        figuresCount = bundle.getString("figureCount"); //pobranie figuresCount ktore zostalo tu poprzednio umieszczone
        rangeFrom = bundle.getString("rangeFrom");
        rangeTo = bundle.getString("rangeTo");


        //przekonwertowanie tych stringow na integery
        figuresCountInt = Integer.parseInt(figuresCount);
        rangeFromInt = Integer.parseInt(rangeFrom);
        rangeToInt = Integer.parseInt(rangeTo);
    }


    public void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view); //inicjalizacja widoku recyclerView

        mAdapter = new FigureAdapter(figureList); // utworzenie nowego adapteru dla listy figureList
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // ustawienia dla recyclerView
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // pobranie kontekstu, wybór MyPref jako preferencji
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        currentPref = getApplicationContext().getSharedPreferences("CurrentPref", 0);
        currentEditor = currentPref.edit();

        currentEditor.clear();
        currentEditor.commit();


        prepareFiguresData(); // utworzenie listy losowych figur

        // ustawienia dla toolbara
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(mToolbar);



        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        tv1 = new TextView(this);
        tv2 = new TextView(this);
        tv3 = new TextView(this);
        tv4 = new TextView(this);

        tv1.setPadding(0, 5, 0, 5);
        tv2.setPadding(0, 0, 0, 5);
        tv3.setPadding(0, 0, 0, 5);
        tv4.setPadding(0, 0, 0, 5);

        tv1.setTextSize(18);
        tv2.setTextSize(18);
        tv3.setTextSize(18);
        tv4.setTextSize(18);

        tv1.setGravity(Gravity.CENTER);
        tv2.setGravity(Gravity.CENTER);
        tv3.setGravity(Gravity.CENTER);
        tv4.setGravity(Gravity.CENTER);

        layout.addView(tv1);
        layout.addView(tv2);
        layout.addView(tv3);
        layout.addView(tv4);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Statystyki");
        alertDialog = builder.create();
        alertDialog.setView(layout);
    }

    public void prepareFiguresData() {

        //pobranie wartości z SharedPreferences
        getPrefArea = pref.getInt("pole", 0);
        getPrefRectangle = pref.getInt("prostokaty", 0);
        getPrefTriangle = pref.getInt("trojkaty", 0);
        getPrefCircle = pref.getInt("kola", 0);

        getPrefArea2 = currentPref.getInt("pole", 0);
        getPrefRectangle2 = currentPref.getInt("prostokaty", 0);
        getPrefTriangle2 = currentPref.getInt("trojkaty", 0);
        getPrefCircle2 = currentPref.getInt("kola", 0);

        //currentEditor.putInt("pole", 0);
        //currentEditor.putInt("prostokaty", 0);
        //currentEditor.putInt("trojkaty", 0);
        //currentEditor.putInt("kola", 0);

        // wykorzystanie pętli FOR do utworzenia tylu elementów ile określiliśmy w MainActivity
        for (int i = 0; i < figuresCountInt; i++) {
            Figure figure = new Figure(rangeFromInt, rangeToInt); //przekazanie parametrów rangeFrom oraz rangeTo jako zakres liczbowy
            figureList.add(figure); //dodanie figure do listy figureList

            if (figure.getTypeOfFigure() == 1) { //jeśli figura1 ma typ 1 (określone w klasie Figure) to wtedy do SharedPreferences zapisuje się jej pole oraz do "prostokąt" dodawane jest 1
                editor.putInt("pole", getPrefArea + figure.getRectangleArea());
                editor.putInt("prostokaty", getPrefRectangle + 1);

                helpVar4 = helpVar4 + figure.getRectangleArea();
                helpVar1 = helpVar1 + 1;
                currentEditor.putInt("pole", helpVar4);
                currentEditor.putInt("prostokaty", helpVar1);
            } else if (figure.getTypeOfFigure() == 2) { // jak wyzzej
                editor.putInt("pole", getPrefArea + figure.getTriangleArea());
                editor.putInt("trojkaty", getPrefTriangle + 1);

                helpVar4 = helpVar4 + figure.getTriangleArea();
                helpVar2 = helpVar2 + 1;

                currentEditor.putInt("pole", helpVar4);
                currentEditor.putInt("trojkaty", helpVar2);
            } else if (figure.getTypeOfFigure() == 3) { // jak wyzej
                editor.putInt("pole", getPrefArea + Integer.valueOf(figure.getCircleArea().intValue()));
                editor.putInt("kola", getPrefCircle + 1);

                helpVar4 = helpVar4 + Integer.valueOf(figure.getCircleArea().intValue());
                helpVar3 = helpVar3 + 1;

                currentEditor.putInt("pole", helpVar4);
                currentEditor.putInt("kola", helpVar3);
            }
            editor.commit(); //zatwierdzenie zmian w editorze
            currentEditor.commit();
        }
        mAdapter.notifyDataSetChanged(); //odswiezenie adaptera
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu); //dodanie itemków do toolbara
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); //pobranie ID danego itemu

        switch (id) {
            case R.id.action_delete_sub1: //jesli wcisniety jest delete opcja 1 to:
                figureList.clear(); //czysci liste
                mAdapter.notifyDataSetChanged(); //odswieza liste


                break;
            case R.id.action_delete_sub2:
                for (int i = 0; i < figureList.size(); i++) { // iteracja przez całąlistę
                    if (figureList.get(i).getTypeOfFigure() == 1) {  //jeśli dana figura ma typ 1 (czyli jest prostokątem)
                        figureList.remove(i); // usuwa ją
                        mAdapter.notifyDataSetChanged(); //odswieża liste
                        i = i - 1; //cofa iteracje o 1 (ponieważ pozbyliśmy się jednego elementu i następny z listy otrzymuje jego numer)
                    }
                }
                break;
            case R.id.action_delete_sub3: //analogicznie jak wyzej
                for (int i = 0; i < figureList.size(); i++) {
                    if (figureList.get(i).getTypeOfFigure() == 2) {
                        figureList.remove(i);
                        mAdapter.notifyDataSetChanged();
                        i = i - 1;
                    }
                }
                break;
            case R.id.action_delete_sub4: //analogicznie jak wyzej
                for (int i = 0; i < figureList.size(); i++) {
                    if (figureList.get(i).getTypeOfFigure() == 3) {
                        figureList.remove(i);
                        mAdapter.notifyDataSetChanged();
                        i = i - 1;
                    }
                }
                break;
            case R.id.action_sort_sub1:
                Collections.sort(figureList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) { //korzystanie z klasy Collections w celu posortowania listy figureList, porownwywanie kazdej wartosci po kolei
                        Figure f1 = (Figure) o1;
                        Figure f2 = (Figure) o2;
                        return f1.getDimension1().compareTo(f2.getDimension1());
                    }
                });
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_sort_sub2:
                Collections.sort(figureList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Figure f1 = (Figure) o1;
                        Figure f2 = (Figure) o2;
                        return f1.getResult().compareTo(f2.getResult());
                    }
                });
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_filter_sub1:
                for (int i = 0; i < figureList.size(); i++) { //iteracja przez całą listę
                    if (figureList.get(i).getTypeOfFigure() != 1) { //jeśli typ figury != 1 (czyli nie jest prostokątem)
                        figureList.get(i).setVisibility(0); //ustawienie jej widoczności na 0
                        mAdapter.notifyDataSetChanged();
                    } else {
                        figureList.get(i).setVisibility(1); //jeśli jest prostokątem to widoczność 1
                        mAdapter.notifyDataSetChanged();
                    }
                    figureList.get(i).setChanged(1);
                }
                break;
            case R.id.action_filter_sub2:
                for (int i = 0; i < figureList.size(); i++) { //tak samo jak wyzej
                    if (figureList.get(i).getTypeOfFigure() != 2) {
                        figureList.get(i).setVisibility(0);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        figureList.get(i).setVisibility(1);
                        mAdapter.notifyDataSetChanged();
                    }
                    figureList.get(i).setChanged(1);
                }
                break;
            case R.id.action_filter_sub3:
                for (int i = 0; i < figureList.size(); i++) { // tak samo jak wyżej
                    if (figureList.get(i).getTypeOfFigure() != 3) {
                        figureList.get(i).setVisibility(0);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        figureList.get(i).setVisibility(1);
                        mAdapter.notifyDataSetChanged();
                    }
                    figureList.get(i).setChanged(1);
                }
                break;
            case R.id.action_filter_sub4:
                for (int i = 0; i < figureList.size(); i++) {
                    figureList.get(i).setVisibility(1); //pokazanie wszystkich elementów na liście
                    figureList.get(i).setChanged(1);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.action_add:
                Figure figure = new Figure(rangeFromInt, rangeToInt); //tworzy się nowa figura dla której przekazujemy parametry rangeFromInt, rangeToInt określone wcześniej (czyli dolna granica i górna granica przedziału)
                figureList.add(figure); //dodanie nowej figury do listy
                mAdapter.notifyDataSetChanged(); //odswiezenie adapatera

                if (figure.getTypeOfFigure() == 1) {
                    editor.putInt("pole", getPrefArea + figure.getRectangleArea()); //dodanie do SharedPref pola tej listy
                    editor.putInt("prostokaty", getPrefRectangle + 1); //dodanie do ilości prostokątów wartość 1
                    editor.commit();


                    helpVar4 = helpVar4 + figure.getRectangleArea();
                    currentEditor.putInt("pole", helpVar4); //dodanie do SharedPref pola tej listy
                    currentEditor.putInt("prostokaty", helpVar1++); //dodanie do ilości prostokątów wartość 1
                    currentEditor.commit();


                } else if (figure.getTypeOfFigure() == 2) {
                    editor.putInt("pole", getPrefArea + figure.getTriangleArea()); //dodanie do SharedPref pola tej listy
                    editor.putInt("trojkaty", getPrefTriangle + 1); //dodanie do ilości prostokątów wartość 1
                    editor.commit();

                    helpVar4 = helpVar4 + figure.getTriangleArea();
                    currentEditor.putInt("pole", helpVar4); //dodanie do SharedPref pola tej listy
                    currentEditor.putInt("trojkaty", helpVar2++); //dodanie do ilości prostokątów wartość 1
                    currentEditor.commit();
                } else if (figure.getTypeOfFigure() == 3) {
                    //Integer.valueOf(figure.getCircleArea().intValue()) <- wytlumaczenie: najpierw konwertujemy getCircleArea ktory jest DOUBLE na inta, a potem dla pewnosci robimy jeszcze Integer.valueOf zeby nic sie po drodze nie popsulo
                    editor.putInt("pole", getPrefArea + Integer.valueOf(figure.getCircleArea().intValue()));
                    editor.putInt("kola", getPrefCircle + 1); //dodanie do ilości prostokątów wartość 1
                    editor.commit();


                    helpVar4 = helpVar4 + Integer.valueOf(figure.getCircleArea().intValue());
                    currentEditor.putInt("pole", helpVar4); //dodanie do SharedPref pola tej listy
                    currentEditor.putInt("kola", helpVar3++); //dodanie do ilości prostokątów wartość 1
                    currentEditor.commit();
                }
                break;
            case R.id.action_stats:

                tv1.setText("Ilość wylosowanych prostokątów: " + String.valueOf(currentPref.getInt("prostokaty", 0)));
                tv2.setText("Ilość wylosowanych trójkątów: " + String.valueOf(currentPref.getInt("trojkaty", 0)));
                tv3.setText("Ilość wylosowanych kół: " + String.valueOf(currentPref.getInt("kola", 0)));
                tv4.setText("Sumaryczne pole: " + String.valueOf(currentPref.getInt("pole", 0)));

                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


