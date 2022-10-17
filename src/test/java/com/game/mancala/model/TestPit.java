package com.game.mancala.model;

import org.junit.jupiter.api.Test;

import static com.game.mancala.model.PlayerNumber.ONE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestPit {


    @Test
    public void mustStoreStonesInHouse() {
        Pit house = new House(ONE, 6);
        assertThat(house.count()).isEqualTo(6);
    }

    @Test
    public void storeMustBeginEmpty() {
        Pit pit = new Store(ONE);
        assertThat(pit.count()).isEqualTo(0);
    }

    @Test
    public void mustPointToTheNextPit() {
        Pit pit = new House(ONE, 6);
        Store store = new Store(ONE);
        House house = new House(ONE, 6);

        pit.setNext(store);
        store.setNext(house);

        assertThat(pit.next()).isEqualTo(store);
        assertThat(store.next()).isEqualTo(house);
    }

    @Test
    public void mustBeAbleToTakeStonesFromHouse() {
        House house = new House(ONE, 6);
        assertThat(house.count()).isEqualTo(6);

        Integer taken = house.take();

        assertThat(taken).isEqualTo(6);
        assertThat(house.count()).isEqualTo(0);
    }

    @Test
    public void mustBeAbleToSowStoneInHouse() {
        House house = new House(ONE, 0);
        house.sow();
        assertThat(house.count()).isEqualTo(1);
    }

    @Test
    public void mustBeAbleToSowStoneInStore() {
        Store store = new Store(ONE);
        store.sow();
        assertThat(store.count()).isEqualTo(1);
    }

    @Test
    public void mustBeAbleToSowMultipleStonesInStore() {
        Store store = new Store(ONE);
        store.sow(6);
        assertThat(store.count()).isEqualTo(6);
    }
}
