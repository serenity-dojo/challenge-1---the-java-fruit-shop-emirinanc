package com.serenitydojo.fruitmarket;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static com.serenitydojo.fruitmarket.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class CatalogTest {

    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {

        Catalog catalog = catalogWithSomeItemsAndPrices();
        assertThat(catalog.getPriceOf(Apple)).isEqualTo(6.00);
    }

    @Test
    public void shouldListAvailableFruitsAlphabetically() {


        Catalog catalog =catalogWithSomeItemsAndPrices();

         List<CatalogItem> availableFruits = catalog.getAvailableFruits();
         assertThat(availableFruits.get(0).getFruit()).isEqualTo(Pear);
         assertThat(availableFruits.get(1).getFruit()).isEqualTo(Apple);
         assertThat(availableFruits.get(2).getFruit()).isEqualTo(Banana);
    }

    @Test
    public void shouldCaclulateTheRunningTotalCorrectlyAnyFruitLessThan5Kg() throws FruitUnavailableException {

        Catalog catalog = catalogWithSomeItemsAndPrices();

        catalog.setPriceOf(Pear,5);
        catalog.setPriceOf(Apple,6);
        catalog.setPriceOf(Banana,7);

        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Apple,2);
        cart.addItem(Banana,3);
        Double sum = cart.calculateSumTotal();

        assertThat(sum).isEqualTo(1*5 + 2*6 + 3*7);


    }

    @Test
    public void shoppingCartKeepsRunningTotal() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        assertThat(cart.calculateSumTotal()).isEqualTo(1*5);
        cart.addItem(Apple,2);
        assertThat(cart.calculateSumTotal()).isEqualTo(1*5+2*6);
    }

    @Test
    public void shouldDecideWhetherDiscountIsToBeAppliedWhenSomeFruitGreaterThan5Kg() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Apple,2);
        cart.addItem(Banana,5);

        assertThat(cart.anyFruitAbove5kg()).isTrue();
    }

    @Test
    public void shouldDecideWhetherDiscountIsToBeAppliedWhenSomeFruitLesserThan5Kg() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Apple,2);
        cart.addItem(Banana,4);

        assertThat(cart.anyFruitAbove5kg()).isFalse();
    }

    @Test
    public void shouldCalculateTheDiscountCorrectlyWhenSomeFruitGreaterThan5Kg() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Apple,2);
        cart.addItem(Banana,6);



        assertThat(cart.calculateSumTotal()).isEqualTo((1*5 + 2*6 + 6*7) * 0.9);
    }

    @Test
    public void shouldApplyTheSameDiscountEvenIfThereMoreThanOneFruitAbove5Kg() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Apple,6);
        cart.addItem(Banana,6);

        assertThat(cart.calculateSumTotal()).isEqualTo((1*5 + 6*6 + 6*7) * 0.9);
    }

    @Test
    public void throwsExceptionNoSuchFruit() {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);

        FruitUnavailableException noSuchFruit = assertThrows(
                FruitUnavailableException.class,
                () -> cart.addItem(Orange,99)
        );

        assertThat(noSuchFruit.getMessage()).isEqualTo("no such fruit");

        FruitUnavailableException noSuchAmount = assertThrows(
                FruitUnavailableException.class,
                () -> cart.addItem(Apple,99)
        );

        assertThat(noSuchAmount.getMessage()).isEqualTo("no such amount");
    }

    @Test(expected = FruitUnavailableException.class)
    public void shouldReturnFruitUnavailableExceptionWhenQuantityAvailableIsExceeded() throws FruitUnavailableException {
        Catalog catalog = catalogWithSomeItemsAndPrices();
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear,1);
        cart.addItem(Pear,1);
        cart.addItem(Pear,1);
        cart.addItem(Pear,0);

        assertThat(cart.calculateSumTotal()).isEqualTo((1*5 + 6*6 + 6*7) * 0.9);
    }



    private static Catalog catalogWithSomeItemsAndPrices() {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Pear,3),
                new CatalogItem(Apple,10),
                new CatalogItem(Banana,10)
        );

        catalog.setPriceOf(Pear,5);
        catalog.setPriceOf(Apple,6);
        catalog.setPriceOf(Banana,7);

        return  catalog;

    }

}
