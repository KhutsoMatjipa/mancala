package com.game.mancala.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.game.mancala.model.PlayerNumber.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPlayer {

    @Test
    void playerMustSowStonesOnTurn() {
        House last = new House(ONE, 0);
        House middle = new House(ONE,0);
        House first = new House(ONE,2);
        Store end = new Store(ONE);

        first.setNext(middle).setNext(last).setNext(end);

        Player player = new Player(ONE, List.of(first, middle, last), end);
        Pit landed = player.turn(1);

        assertThat(landed).isEqualTo(last);
        assertThat(first.count()).isZero();
        assertThat(middle.count()).isEqualTo(1);
        assertThat(last.count()).isEqualTo(0);
        assertThat(end.count()).isEqualTo(1);
    }

    @Test
    void playerCannotChooseEmptyHouse() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player(ONE, List.of(new House(ONE, 0)), new Store(ONE));
            player.turn(1);
        });
    }

    @Test
    void playerCannotChooseHouseBelowRange() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player(ONE, List.of(new House(ONE, 0)), new Store(ONE));
            player.turn(0);
        });
    }

    @Test
    void playerSkipsOpponentsStore() {
        House myHouse = new House(ONE,3);
        Store myStore = new Store(ONE);
        House opponentHouse = new House(TWO,0);
        Store opponentStore = new Store(TWO);

        myHouse.setNext(myStore)
                .setNext(opponentHouse)
                .setNext(opponentStore)
                .setNext(myHouse);

        Player player = new Player(ONE, List.of(myHouse), myStore);
        Pit landed = player.turn(1);

        assertThat(landed).as("Checking landed : ").isEqualTo(myStore);
        assertThat(myHouse.count()).as("Checking myHouse : ").isEqualTo(1);
        assertThat(myStore.count()).as("Checking myStore : ").isEqualTo(2);
        assertThat(opponentHouse.count()).as("Checking opponentHouse : ").isEqualTo(0);
        assertThat(opponentStore.count()).as("Checking opponentStore : ").isZero();
    }
}
