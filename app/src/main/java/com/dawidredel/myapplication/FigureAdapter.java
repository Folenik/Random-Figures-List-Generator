package com.dawidredel.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dawidredel.myapplication.Models.Figure;

import java.util.List;

public class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.MyViewHolder> {

    private List<Figure> figuresList; // nowa lista figureList wg. klasy Figure
    CharSequence[] values = {"Usuń element", "Wyświetl szczegóły", "Edytuj dane", "Duplikuj wpis"}; // tablica przechowujaca stringi
    AlertDialog alertDialog1, alertDialog2, alertDialog3; // deklaracja 3 potrzebnych alertdialogow
    ViewGroup.LayoutParams params, params2; // parametry dla layouta
    SharedPreferences pref, currentPref; // opisane w innych klasach
    SharedPreferences.Editor editor, currentEditor; // j.w
    Integer getPrefArea, getPrefRectangle, getPrefTriangle, getPrefCircle, switchInteger;

    //stworzenie ViewHoldera (przechowuje widok dla RecyclerView, trzeba tutaj opisac wszystkie elementy majace sie wyswietlic na pojedynczym elemencie listy)
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ConstraintLayout constraintLayout;
        public TextView firstDimension, secondDimension, area, firstDimension2, secondDimension2, area2;
        public ImageView image;

        public MyViewHolder(View view) { //konstruktor domyslny
            super(view);
            image = (ImageView) view.findViewById(R.id.figure_logo);
            firstDimension = (TextView) view.findViewById(R.id.figure_firstDimension);
            secondDimension = (TextView) view.findViewById(R.id.figure_secondDimension);
            area = (TextView) view.findViewById(R.id.figure_area);

            firstDimension2 = (TextView) view.findViewById(R.id.figure_firstDimension2);
            secondDimension2 = (TextView) view.findViewById(R.id.figure_secondDimension2);
            area2 = (TextView) view.findViewById(R.id.figure_area2);

            constraintLayout = view.findViewById(R.id.constraint_layout);
            cardView = view.findViewById(R.id.card_view_layout);

            //dzieki wykorzystaniu MyViewHolder mozemy utworzyc widok dla pojedynczego elemetnu listy
        }
    }

    public FigureAdapter(List<Figure> figuresList) {
        this.figuresList = figuresList;
    } // konstruktor domyslny

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.figure_layout, parent, false);

        params = itemView.getLayoutParams();

        return new MyViewHolder(itemView);

        //przy utworzeniu ViewHoldera - odwolanie sie do layoutu figure_layout oraz pobranie jego parametrów
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //onBindViewHolder czyli update dla ViewHoldera, wypelnienie go elementami
        //przyjmuje dwa parametry tzn holder (jest to pojedynczy element listy
        //natomiast position to jego pozycja na liscie
        final Figure figure = figuresList.get(position); //pobranie pozycji danej figury


        //tutaj nie da się pobrać kontekstu więc obszedłem to w ten sposób że pobrałem sobie kontekst od obrazka jednego elementu i to się sprawdza
        //SharedPreferences opisane w pozostalych klasach
        pref = holder.image.getContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        currentPref = holder.image.getContext().getSharedPreferences("CurrentPref", 0);
        currentEditor = pref.edit();

        getPrefArea = pref.getInt("pole", 0);
        getPrefRectangle = pref.getInt("prostokaty", 0);
        getPrefTriangle = pref.getInt("trojkaty", 0);
        getPrefCircle = pref.getInt("kola", 0);

        //jeśli figura ma Visibility 0 to ustawiony jest widocznosc holdera na GONE (czyli znika), nastepnie parametry layoutu na 0,0 (zeby lista sie zaktualizowala)
        if (figure.getVisibility() == 0) {
            holder.cardView.setVisibility(View.GONE);
            holder.cardView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
        //analogicznie jak wyzej
        if (figure.getVisibility() == 1) {
            holder.cardView.setVisibility(View.VISIBLE);
            holder.cardView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        //pobranie typu figury, jesli 1 (czyli prostokat) to ustawienie obrazka prostokata
        //oraz wstawienie odpowiedniego tekstu do kazdego z textview
        if (figure.getTypeOfFigure() == 1) {
            holder.image.setImageResource(R.drawable.rectangle);
            holder.firstDimension.setText(figure.getDimension1().toString());
            holder.secondDimension.setText(figure.getDimension2().toString());
            holder.area.setText(figure.getRectangleArea().toString());

            holder.firstDimension2.setText("BOK 1");
            holder.secondDimension2.setText("BOK 2");
            holder.area2.setText("POLE");

            figure.setResult(figure.getRectangleArea());
        } else if (figure.getTypeOfFigure() == 2) {
            holder.image.setImageResource(R.drawable.triangle);
            holder.firstDimension.setText(figure.getDimension1().toString());
            holder.secondDimension.setText(figure.getDimension2().toString());
            holder.area.setText(figure.getTriangleArea().toString());

            holder.firstDimension2.setText("BOK");
            holder.secondDimension2.setText("WYSOKOŚĆ");
            holder.area2.setText("POLE");

            figure.setResult(figure.getTriangleArea());
        } else if (figure.getTypeOfFigure() == 3) {
            holder.image.setImageResource(R.drawable.circle);
            holder.firstDimension.setText(figure.getDimension1().toString());
            holder.secondDimension.setText(" - ");
            holder.area.setText(Integer.valueOf(figure.getCircleArea().intValue()).toString());

            holder.firstDimension2.setText("PROMIEŃ");
            holder.secondDimension2.setText(" - ");
            holder.area2.setText("POLE");

            figure.setResult(Integer.valueOf(figure.getCircleArea().intValue()));
        }


        //setOnLongClickListener tzn po dluzszym przycisnieciu na coinstraintLayout danego holdera (czyli po prostu na pojedynczy item na liscie)
        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.image.getContext()); //stworzenie nowego buildera dla dialogu
                builder.setTitle("Co zamierzasz zrobić?"); // nadanie mu tytulu

                //nadanie wyboru opcji values (opisane wyzej)
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            //jeśli wybrana opcja 1 to usuwa element na danej pozycji i odswieza liste
                            case 0:
                                Integer helpCounter1 = pref.getInt("prostokaty",0);
                                Integer helpCounter2 = pref.getInt("trojkaty",0);
                                Integer helpCounter3 = pref.getInt("kola",0);
                                Integer helpCounter4 = pref.getInt("pole",0);

                                editor.putInt("prostokaty",helpCounter1-1);
                                editor.putInt("trojkaty",helpCounter2-1);
                                editor.putInt("kola",helpCounter3-1);
                                if(figure.getTypeOfFigure() == 1) {
                                    editor.putInt("pole",helpCounter4-figure.getRectangleArea());
                                }
                                if(figure.getTypeOfFigure() == 2) {
                                    editor.putInt("pole",helpCounter4-figure.getTriangleArea());
                                }
                                if(figure.getTypeOfFigure() == 3) {
                                    editor.putInt("pole",helpCounter4-figure.getCircleArea().intValue());
                                }

                                figuresList.remove(holder.getPosition());
                                notifyItemRemoved(holder.getPosition());
                                notifyDataSetChanged();
                                break;
                            //jesli wybrana opcja 2 to tworzy nowy layout, wsadza do niego textviewy a nastepnie ten layout jest wyswietlany przez dialogboxa
                            case 1:
                                LinearLayout layout = new LinearLayout(holder.image.getContext());
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                layout.setLayoutParams(parms);

                                TextView tv1 = new TextView(holder.image.getContext());
                                TextView tv2 = new TextView(holder.image.getContext());
                                TextView tv3 = new TextView(holder.image.getContext());
                                TextView tv4 = new TextView(holder.image.getContext());

                                tv1.setPadding(0, 5, 0, 5);
                                tv2.setPadding(0, 0, 0, 5);
                                tv3.setPadding(0, 0, 0, 5);
                                tv4.setPadding(0, 0, 0, 5);

                                tv1.setTextSize(18);
                                tv2.setTextSize(18);
                                tv3.setTextSize(18);
                                tv4.setTextSize(18);

                                if (figure.getTypeOfFigure() == 1) {
                                    tv1.setText("Bok 1: " + holder.firstDimension.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("Bok 2: " + holder.secondDimension.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                    tv4.setText("Obwód: " + figure.getRectanglePerimeter().toString());
                                    tv4.setGravity(Gravity.CENTER);

                                } else if (figure.getTypeOfFigure() == 2) {
                                    tv1.setText("Bok: " + holder.firstDimension.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("Wysokość: " + holder.secondDimension.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                    tv4.setText("Obwód: " + figure.getTrianglePerimeter().toString());
                                    tv4.setGravity(Gravity.CENTER);

                                } else if (figure.getTypeOfFigure() == 3) {
                                    tv1.setText("Promień: " + holder.firstDimension.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("-" + holder.secondDimension.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                    tv4.setText("Obwód: " + Integer.valueOf(figure.getCirclePerimeter().intValue()).toString());
                                    tv4.setGravity(Gravity.CENTER);

                                }

                                layout.addView(tv1);
                                layout.addView(tv2);
                                layout.addView(tv3);
                                layout.addView(tv4);

                                final AlertDialog.Builder builder2 = new AlertDialog.Builder(holder.image.getContext());
                                builder2.setTitle("Szczegóły");
                                alertDialog2 = builder2.create();
                                alertDialog2.setView(layout);
                                alertDialog2.show();

                                break;
                            //analogicznie jak dla case1 z tym ze tutaj sa edittexty
                            case 2:
                                LinearLayout layout2 = new LinearLayout(holder.image.getContext());
                                layout2.setOrientation(LinearLayout.VERTICAL);

                                final TextView tv1_2 = new TextView(holder.image.getContext());
                                final TextView tv2_2 = new TextView(holder.image.getContext());
                                final TextView tv3_2 = new TextView(holder.image.getContext());

                                final EditText et1 = new EditText(holder.image.getContext());
                                final EditText et2 = new EditText(holder.image.getContext());

                                tv1_2.setPadding(0, 5, 0, 5);
                                tv2_2.setPadding(0, 0, 0, 5);
                                tv3_2.setPadding(0, 0, 0, 5);

                                tv1_2.setTextSize(18);
                                tv2_2.setTextSize(18);
                                tv3_2.setTextSize(18);

                                tv1_2.setGravity(Gravity.CENTER);
                                tv2_2.setGravity(Gravity.CENTER);
                                tv3_2.setGravity(Gravity.CENTER);

                                tv1_2.setId(R.id.figure_rectangle);
                                tv2_2.setId(R.id.figure_triangle);
                                tv3_2.setId(R.id.figure_circle);

                                tv1_2.setText("Prostokąt");
                                tv2_2.setText("Trójkąt");
                                tv3_2.setText("Koło");

                                if (figure.getTypeOfFigure() == 1) {
                                    tv1_2.setTextColor(Color.parseColor("#F44336"));
                                    tv2_2.setTextColor(Color.parseColor("#000000"));
                                    tv3_2.setTextColor(Color.parseColor("#000000"));

                                    et1.setHint("Wprowadź nowy bok 1");
                                    et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et1.setGravity(Gravity.CENTER);

                                    et2.setHint("Wprowadź nowy bok 2");

                                    et2.setGravity(Gravity.CENTER);
                                    et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et2.setVisibility(View.VISIBLE);

                                } else if (figure.getTypeOfFigure() == 2) {
                                    tv1_2.setTextColor(Color.parseColor("#000000"));
                                    tv2_2.setTextColor(Color.parseColor("#F44336"));
                                    tv3_2.setTextColor(Color.parseColor("#000000"));

                                    et1.setHint("Wprowadź nowy bok");
                                    et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et1.setGravity(Gravity.CENTER);

                                    et2.setHint("Wprowadź nową wysokość");
                                    et2.setGravity(Gravity.CENTER);
                                    et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et2.setVisibility(View.VISIBLE);

                                } else if (figure.getTypeOfFigure() == 3) {
                                    tv1_2.setTextColor(Color.parseColor("#000000"));
                                    tv2_2.setTextColor(Color.parseColor("#F44336"));
                                    tv3_2.setTextColor(Color.parseColor("#000000"));

                                    et1.setHint("Wprowadź nowy promień");
                                    et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    et1.setGravity(Gravity.CENTER);
                                    et2.setVisibility(View.INVISIBLE);
                                }

                                tv1_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        switchInteger = 1;

                                        tv1_2.setTextColor(Color.parseColor("#F44336"));
                                        tv2_2.setTextColor(Color.parseColor("#000000"));
                                        tv3_2.setTextColor(Color.parseColor("#000000"));

                                        et1.setHint("Wprowadź nowy bok 1");
                                        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et1.setGravity(Gravity.CENTER);

                                        et2.setHint("Wprowadź nowy bok 2");

                                        et2.setGravity(Gravity.CENTER);
                                        et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et2.setVisibility(View.VISIBLE);
                                    }
                                });

                                tv2_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        switchInteger = 2;

                                        tv1_2.setTextColor(Color.parseColor("#000000"));
                                        tv2_2.setTextColor(Color.parseColor("#F44336"));
                                        tv3_2.setTextColor(Color.parseColor("#000000"));

                                        et1.setHint("Wprowadź nowy bok");
                                        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et1.setGravity(Gravity.CENTER);

                                        et2.setHint("Wprowadź nową wysokość");
                                        et2.setGravity(Gravity.CENTER);
                                        et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et2.setVisibility(View.VISIBLE);
                                    }
                                });

                                tv3_2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        switchInteger = 3;

                                        tv1_2.setTextColor(Color.parseColor("#000000"));
                                        tv2_2.setTextColor(Color.parseColor("#000000"));
                                        tv3_2.setTextColor(Color.parseColor("#F44336"));

                                        et1.setHint("Wprowadź nowy promień");
                                        et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                                        et1.setGravity(Gravity.CENTER);
                                        et2.setVisibility(View.INVISIBLE);
                                    }
                                });

                                layout2.addView(tv1_2);
                                layout2.addView(tv2_2);
                                layout2.addView(tv3_2);
                                layout2.addView(et1);
                                layout2.addView(et2);

                                final AlertDialog.Builder builder3 = new AlertDialog.Builder(holder.image.getContext());
                                builder3.setTitle("Edytuj dane");

                                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (switchInteger == 1) {
                                            figure.setTypeOfFigure(1);

                                            holder.firstDimension.setText(et1.getText());
                                            holder.secondDimension.setText(et2.getText());

                                            figure.setDimension1(Integer.parseInt(et1.getText().toString()));
                                            figure.setDimension2(Integer.parseInt(et2.getText().toString()));

                                            holder.image.setImageResource(R.drawable.rectangle);

                                            notifyDataSetChanged();
                                        } else if (switchInteger == 2) {
                                            figure.setTypeOfFigure(2);

                                            holder.firstDimension.setText(et1.getText());
                                            holder.secondDimension.setText(et2.getText());

                                            figure.setDimension1(Integer.parseInt(et1.getText().toString()));
                                            figure.setDimension2(Integer.parseInt(et2.getText().toString()));

                                            holder.image.setImageResource(R.drawable.triangle);

                                            notifyDataSetChanged();

                                        } else if (switchInteger == 3) {
                                            figure.setTypeOfFigure(3);

                                            holder.firstDimension.setText(et1.getText());

                                            holder.image.setImageResource(R.drawable.circle);

                                            figure.setDimension1(Integer.parseInt(et1.getText().toString()));

                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                                builder3.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                alertDialog3 = builder3.create();
                                alertDialog3.setView(layout2);
                                alertDialog3.show();


                                break;

                            //duplikacja elementu na liscie i edycja SharedPref (opisane w innych klasach)
                            case 3:
                                figuresList.add(figure);
                                if (figure.getTypeOfFigure() == 1) {
                                    editor.putInt("pole", getPrefArea + figure.getRectangleArea());
                                    editor.putInt("prostokaty", getPrefRectangle + 1);

                                } else if (figure.getTypeOfFigure() == 2) {
                                    editor.putInt("pole", getPrefArea + figure.getTriangleArea());
                                    editor.putInt("trojkaty", getPrefTriangle + 1);

                                } else if (figure.getTypeOfFigure() == 3) {
                                    editor.putInt("pole", getPrefArea + Integer.valueOf(figure.getCircleArea().intValue()));
                                    editor.putInt("kola", getPrefCircle + 1);

                                }
                                editor.commit();
                                notifyDataSetChanged();
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();

                return true;
            }
        });
    }

    //pozwala innym klasom pobrac ilosc itemkow na liscie
    @Override
    public int getItemCount() {
        return figuresList.size();
    }
}
