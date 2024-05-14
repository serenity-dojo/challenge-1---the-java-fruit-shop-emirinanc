package com.serenitydojo.fruitmarket;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private final List<CatalogItem> availableFruits = new ArrayList<>();
    private final Map<Fruit, Double> fruitToPricePerKg = new HashMap<>();

    public void setPriceOf(Fruit fruit, double price) throws RuntimeException {
        try{
            fruitToPricePerKg.put(fruit, price);

        }
        catch(RuntimeException e){
            throw new RuntimeException("Create a map to keep track of fruits and their prices");
        }



    }

    public static Catalog withItems(CatalogItem... catalogItems) throws RuntimeException {

        try {
            Catalog catalog = new Catalog();
            catalog.availableFruits.addAll(Arrays.asList(catalogItems));
            return catalog;
        }
        catch (RuntimeException e) {
            throw new RuntimeException("TODO, create catalog and add items to the list of available fruits");
        }

    }

    public List<CatalogItem> getAvailableFruits() {
        return availableFruits;
    }

    public Double getPriceOf (Fruit fruit) {
        return fruitToPricePerKg.get(fruit);
    }

    public CatalogItem getFruit(Fruit fruit, int amountInKg) {
        try {
            List<CatalogItem> itemsForFruit = getAvailableFruits().stream()
                    .filter(it -> it.getFruit().equals(fruit)).collect(Collectors.toList());
            if (itemsForFruit.isEmpty()) {
                throw new FruitUnavailableException("no such fruit");
            }
            Optional<CatalogItem> optionalCatalogItem = itemsForFruit.stream()
                    .filter(it -> it.getAmountInKg() == amountInKg)
                    .findFirst();
            if (optionalCatalogItem.isPresent()) {
                return optionalCatalogItem.get();
            }
            throw new FruitUnavailableException("no such amount");
        } catch (FruitUnavailableException e) {
            // Handle the exception here, such as logging or returning a default value
            e.printStackTrace(); // Example: print the stack trace
            return null; // Example: return null
        }
    }

    public String getAvailabeFruitNames() {
        return getAvailableFruits().stream().map(it -> it.getFruit().name()).sorted().collect(Collectors.joining(", "));
    }



}
