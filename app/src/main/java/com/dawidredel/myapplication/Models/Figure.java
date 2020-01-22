package com.dawidredel.myapplication.Models;

import java.util.Random;

public class Figure {
    private Integer firstrandom, secondrandom;
    int randomNumber = new Random().nextInt(3) + 1;
    int randomDimension1;
    int randomDimension2;
    int result;

    public Figure(int firstrandom, int secondrandom) {
        this.firstrandom = firstrandom;
        this.secondrandom = secondrandom;

        randomDimension1 = new Random().nextInt((secondrandom - firstrandom) + 1) + firstrandom;
        randomDimension2 = new Random().nextInt((secondrandom - firstrandom) + 1) + firstrandom;
    }

    public int getTypeOfFigure() {
        return randomNumber;
    }

    public Integer getDimension1() {
        return randomDimension1;
    }

    public Integer getDimension2() {
        return randomDimension2;
    }

    public Integer getRectangleArea() {
        return randomDimension1 * randomDimension2;
    }

    public Integer getTriangleArea() {
        return ((randomDimension1 * randomDimension2) / 2);
    }

    public Double getCircleArea() {
        return (3.141592 * ((getDimension1() * (getDimension1()) / 2)));
    }

    public void setDimension1(int randomDimension1) {
        this.randomDimension1 = randomDimension1;

    }

    public void setDimension2(int randomDimension2) {
        this.randomDimension2 = randomDimension2;
    }

    public void setResult (int result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

}

