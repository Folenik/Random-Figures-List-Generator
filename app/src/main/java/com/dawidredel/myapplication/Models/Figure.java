package com.dawidredel.myapplication.Models;

import java.util.Random;

//klasa zawierająca w sobie same settery i gettery
//jest to pojedynczy element na liście

public class Figure {
    private Integer firstrandom, secondrandom; // beda używane w konstruktorze domyslnym (public Figure ponizej)
    int randomNumber = new Random().nextInt(3) + 1; // wylosowanie liczby z zakresu 1-3
    int randomDimension1; // losowy wymiar1
    int randomDimension2; // losowy wymiar2
    int result;
    int visibility = 1;

    public Figure(int firstrandom, int secondrandom) {
        this.firstrandom = firstrandom;
        this.secondrandom = secondrandom;

        randomDimension1 = new Random().nextInt((secondrandom - firstrandom) + 1) + firstrandom;
        randomDimension2 = new Random().nextInt((secondrandom - firstrandom) + 1) + firstrandom;

        //tworzac nowy obiekt Figure nalezy podac paremtr firstrandom oraz secondrandom ktore to
        //sa zakresem liczbowym (np od 5 do 10), nastepnie w tym zakresie losuja sie dwie liczby
        // randomDimension1 oraz randomDimension2
    }

    public int getTypeOfFigure() {
        return randomNumber;
    } // funkcja umozliwiajaca pobranie typu figury (ustalilem ze 1 to prostokat, 2 to trojkat, 3 to kolo)

    public Integer getDimension1() {
        return randomDimension1;
    } // umozliwia pobranie losowego boku 1

    public Integer getDimension2() {
        return randomDimension2;
    } // umozliwia pobranie losowego bokui 2

    public Integer getRectangleArea() {
        return randomDimension1 * randomDimension2;
    } // umozliwia pobranie pola prostokata

    public Integer getTriangleArea() {
        return ((randomDimension1 * randomDimension2) / 2);
    } // jak wyzej

    public Double getCircleArea() {
        return (3.141592 * ((getDimension1() * (getDimension1()) / 2)));
    } // jak wyzej

    public void setDimension1(int randomDimension1) {
        this.randomDimension1 = randomDimension1;
    } // pozwala ustawić randomDimension1 (uzywane jest przy edycji elementu dla nadania mu nowego boku)

    public void setDimension2(int randomDimension2) {
        this.randomDimension2 = randomDimension2;
    } // jak wyzej. ponizsze settery i gettery dzialaja analogicznie

    public void setResult(int result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Integer getVisibility() {
        return visibility;
    }
}

