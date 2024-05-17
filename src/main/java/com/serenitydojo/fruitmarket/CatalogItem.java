package com.serenitydojo.fruitmarket;

import java.util.Objects;

public class CatalogItem {

    private final Fruit fruit;
    private int amountInKgAvailable;
    private int amountInKgRequested;
    private String name;


    public CatalogItem(Fruit fruit, int amountInKgAvailable) {
        this.fruit = fruit;
        this.amountInKgAvailable = amountInKgAvailable;
        this.amountInKgRequested = 0;
        this.name = fruit.name();
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getAmountInKgAvailable() {
        return amountInKgAvailable;
    }

    public void setAmountInKgRequested (int amountInKgRequested) {
        this.amountInKgRequested = amountInKgRequested;
    }

    public int getAmountInKgRequested() {
        return amountInKgRequested;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogItem that = (CatalogItem) o;
        return amountInKgAvailable == that.amountInKgAvailable && amountInKgRequested == that.amountInKgRequested && fruit == that.fruit && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruit, amountInKgAvailable, amountInKgRequested, name);
    }

    public void setAmountInKgAvailable(int amountInKgAvailable) {
        this.amountInKgAvailable = amountInKgAvailable;
    }

    public String getName() {
        return name;
    }
}
