package org.slytherin.cinema.model.entities;

public enum PriceType {
    STANDARD(9.90),

    CHILD(6.90);

    private final double VALUE;
    PriceType(double VALUE) {
        this.VALUE = VALUE;
    }


    public double getVALUE() {
        return VALUE;
    }




}
