package com.serenitydojo.fruitmarket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final Catalog catalog;
    private final List<CatalogItem> items = new ArrayList<>();

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
    }

    public Double calculateSumTotal() {
        double sum = items.stream().mapToDouble(it -> catalog.getPriceOf(it.getFruit()) * it.getAmountInKgRequested()).sum();

        if(anyFruitAbove5kg()) {
            int discountInPercent = 10;
            return sum * (1 - (discountInPercent / 100.));
        }

        return sum;

    }

    public Boolean anyFruitAbove5kg() {
        Map<Fruit, List<CatalogItem>> fruitToItems = items.stream().collect(Collectors.groupingBy(CatalogItem::getFruit));

        return fruitToItems.values().stream().anyMatch(items1 -> items1.stream().mapToInt(CatalogItem::getAmountInKgRequested).sum() >= 5);
    }

    public void addItem(Fruit fruit, int amountInKgRequested) {
            items.add(catalog.getFruit(fruit, amountInKgRequested));
    }
}
