package com.dawidredel.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
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


    private List<Figure> figureList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FigureAdapter mAdapter;
    Bundle bundle;
    String figuresCount, rangeFrom, rangeTo;
    Integer figuresCountInt, rangeFromInt, rangeToInt;
    Toolbar mToolbar;



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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new FigureAdapter(figureList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareFiguresData();

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(mToolbar);
    }

    public void prepareFiguresData() {

        // wykorzystanie pętli FOR do utworzenia tylu elementów ile określiliśmy w MainActivity
        for(int i=0; i<figuresCountInt;i++) {
            Figure figure = new Figure(rangeFromInt,rangeToInt); //przekazanie parametrów rangeFrom oraz rangeTo jako zakres liczbowy
            figureList.add(figure);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       switch (id) {
            case R.id.action_delete_sub1:
                figureList.clear();
                mAdapter.notifyDataSetChanged();

                break;
            case R.id.action_delete_sub2:


                //for (int i=0;i<figureList.size();i++) {

                //}
                //mAdapter.notifyDataSetChanged();

                break;
            case R.id.action_delete_sub3:

                break;
            case R.id.action_delete_sub4:

                break;
            case R.id.action_sort_sub1:
                Collections.sort(figureList, new Comparator() {
                   @Override
                    public int compare(Object o1, Object o2) {
                        Figure f1 = (Figure) o1;
                        Figure f2 = (Figure) o2;
                        return f1.getDimension1().compareTo(f2.getDimension1());
                   }
               });
                break;
            case R.id.action_sort_sub2:

                break;
            case R.id.action_filter:

               break;
        }
        return super.onOptionsItemSelected(item);
    }

}


