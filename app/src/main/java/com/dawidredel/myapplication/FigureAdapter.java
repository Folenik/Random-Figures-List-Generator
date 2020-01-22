package com.dawidredel.myapplication;

import android.content.DialogInterface;
import android.os.Build;
import android.os.NetworkOnMainThreadException;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dawidredel.myapplication.Models.Figure;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.MyViewHolder> {

    private List<Figure> figuresList;
    CharSequence[] values = {"Usuń element", "Wyświetl szczegóły", "Edytuj dane", "Duplikuj wpis"};
    AlertDialog alertDialog1, alertDialog2, alertDialog3;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayout;
        public AppCompatTextView name, characteristic, area, name2, characteristic2, area2;
        public AppCompatImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (AppCompatImageView) view.findViewById(R.id.figure_logo);
            name = (AppCompatTextView) view.findViewById(R.id.figure_name);
            characteristic = (AppCompatTextView) view.findViewById(R.id.figure_characteristic);
            area = (AppCompatTextView) view.findViewById(R.id.figure_area);

            name2 = (AppCompatTextView) view.findViewById(R.id.figure_name2);
            characteristic2 = (AppCompatTextView) view.findViewById(R.id.figure_characteristic2);
            area2 = (AppCompatTextView) view.findViewById(R.id.figure_area2);

            constraintLayout = view.findViewById(R.id.constraint_layout);
        }
    }

    public FigureAdapter(List<Figure> figuresList) {
        this.figuresList = figuresList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.figure_layout, parent, false);

        return new MyViewHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Figure figure = figuresList.get(position);

        holder.constraintLayout.setVisibility(View.INVISIBLE);
        holder.constraintLayout.setVisibility(View.VISIBLE);

        if (figure.getTypeOfFigure() == 1) {
            holder.image.setImageResource(R.drawable.rectangle);
            holder.name.setText(figure.getDimension1().toString());
            holder.characteristic.setText(figure.getDimension2().toString());
            holder.area.setText(figure.getRectangleArea().toString());

            holder.name2.setText("BOK 1");
            holder.characteristic2.setText("BOK 2");
            holder.area2.setText("POLE");

            figure.setResult(figure.getRectangleArea());


        } else if (figure.getTypeOfFigure() == 2) {
            holder.image.setImageResource(R.drawable.triangle);
            holder.name.setText(figure.getDimension1().toString());
            holder.characteristic.setText(figure.getDimension2().toString());
            holder.area.setText(figure.getTriangleArea().toString());

            holder.name2.setText("BOK");
            holder.characteristic2.setText("WYSOKOŚĆ");
            holder.area2.setText("POLE");

            figure.setResult(figure.getTriangleArea());
        } else if (figure.getTypeOfFigure() == 3) {
            holder.image.setImageResource(R.drawable.circle);
            holder.name.setText(figure.getDimension1().toString());
            holder.characteristic.setText(" - ");
            holder.area.setText(figure.getCircleArea().toString());

            holder.name2.setText("PROMIEŃ");
            holder.characteristic2.setText(" - ");
            holder.area2.setText("POLE");

            figure.setResult(Integer.valueOf(figure.getCircleArea().intValue()));
        }



        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.image.getContext());
                builder.setTitle("Co zamierzasz zrobić?");
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                figuresList.remove(holder.getPosition());
                                notifyItemRemoved(holder.getPosition());
                                break;
                            case 1:
                                LinearLayout layout = new LinearLayout(holder.image.getContext());
                                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                layout.setLayoutParams(parms);

                                TextView tv1 = new TextView(holder.image.getContext());
                                TextView tv2 = new TextView(holder.image.getContext());
                                TextView tv3 = new TextView(holder.image.getContext());

                                tv1.setPadding(0, 5, 0, 5);
                                tv2.setPadding(0, 0, 0, 5);
                                tv3.setPadding(0, 0, 0, 5);

                                tv1.setTextSize(18);
                                tv2.setTextSize(18);
                                tv3.setTextSize(18);

                                if (figure.getTypeOfFigure() == 1) {
                                    tv1.setText("Bok 1: " + holder.name.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("Bok 2: " + holder.characteristic.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                } else if (figure.getTypeOfFigure() == 2) {
                                    tv1.setText("Bok: " + holder.name.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("Wysokość: " + holder.characteristic.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                } else if (figure.getTypeOfFigure() == 3) {
                                    tv1.setText("Promień: " + holder.name.getText());
                                    tv1.setGravity(Gravity.CENTER);

                                    tv2.setText("-" + holder.characteristic.getText());
                                    tv2.setGravity(Gravity.CENTER);

                                    tv3.setText("Pole: " + holder.area.getText());
                                    tv3.setGravity(Gravity.CENTER);

                                }

                                layout.addView(tv1);
                                layout.addView(tv2);
                                layout.addView(tv3);

                                final AlertDialog.Builder builder2 = new AlertDialog.Builder(holder.image.getContext());
                                builder2.setTitle("Szczegóły");
                                alertDialog2 = builder2.create();
                                alertDialog2.setView(layout);
                                alertDialog2.show();

                                break;
                            case 2:
                                LinearLayout layout2 = new LinearLayout(holder.image.getContext());
                                LinearLayout.LayoutParams parms2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                layout2.setOrientation(LinearLayout.VERTICAL);
                                layout2.setLayoutParams(parms2);

                                final EditText et1 = new EditText(holder.image.getContext());
                                final EditText et2 = new EditText(holder.image.getContext());

                                et1.setPadding(0, 5, 0, 5);
                                et1.setPadding(0, 0, 0, 5);

                                et1.setTextSize(18);
                                et1.setTextSize(18);

                                if (figure.getTypeOfFigure() == 1) {
                                    et1.setHint("Wprowadź nowy bok 1");
                                    et1.setGravity(Gravity.CENTER);

                                    et2.setHint("Wprowadź nowy bok 2");
                                    et2.setGravity(Gravity.CENTER);
                                    et2.setVisibility(View.VISIBLE);

                                } else if (figure.getTypeOfFigure() == 2) {
                                    et1.setHint("Wprowadź nowy bok");
                                    et1.setGravity(Gravity.CENTER);

                                    et2.setHint("Wprowadź nową wysokość");
                                    et2.setGravity(Gravity.CENTER);
                                    et2.setVisibility(View.VISIBLE);

                                } else if (figure.getTypeOfFigure() == 3) {
                                    et1.setHint("Wprowadź nowy promień");
                                    et2.setVisibility(View.INVISIBLE);
                                }

                                layout2.addView(et1);
                                layout2.addView(et2);

                                final AlertDialog.Builder builder3 = new AlertDialog.Builder(holder.image.getContext());
                                builder3.setTitle("Edytuj dane");

                                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (figure.getTypeOfFigure() == 1 || figure.getTypeOfFigure() == 2) {
                                            holder.name.setText(et1.getText());
                                            holder.characteristic.setText(et2.getText());

                                            figure.setDimension1(Integer.parseInt(et1.getText().toString()));
                                            figure.setDimension2(Integer.parseInt(et2.getText().toString()));
                                            notifyDataSetChanged();
                                        } else if (figure.getTypeOfFigure() == 3) {
                                            holder.name.setText(et1.getText());

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
                            case 3:
                                figuresList.add(figure);
                                //SORTOWANIE DZIAŁAJĄCE WG KRYTERIUM
                                Collections.sort(figuresList, new Comparator() {
                                    @Override
                                    public int compare(Object o1, Object o2) {
                                        Figure f1 = (Figure) o1;
                                        Figure f2 = (Figure) o2;
                                        return f1.getDimension1().compareTo(f2.getDimension1());
                                    }
                                });
                                /////
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

    @Override
    public int getItemCount() {
        return figuresList.size();
    }
}
