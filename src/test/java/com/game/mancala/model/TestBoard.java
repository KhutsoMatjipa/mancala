package com.game.mancala.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.game.mancala.model.PlayerNumber.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestBoard {

    @Test
    void boardMustHaveTwelveHouses() {
        Board board = Board.create();
        List<House> houses = board.getHouses();
        Map<PlayerNumber, List<House>> sides = houses.stream().collect(Collectors.groupingBy(House::getOwner));
        assertThat(sides.get(ONE).size()).isEqualTo(6);
        assertThat(sides.get(TWO).size()).isEqualTo(6);
    }

    @Test
    void boardMustHaveTwoStores() {
        Board board = Board.create();
        List<Store> stores = board.getStores();
        Map<PlayerNumber, List<Store>> sides = stores.stream().collect(Collectors.groupingBy(Store::getOwner));
        assertThat(sides.get(ONE).size()).isEqualTo(1);
        assertThat(sides.get(TWO).size()).isEqualTo(1);
    }

    @Test
    void boardMustHaveTwoPlayers() {
        Board board = Board.create();
        Players players = board.getPlayers();
        assertThat(players.getPlayerOne().getNum()).isEqualTo(ONE);
        assertThat(players.getPlayerTwo().getNum()).isEqualTo(TWO);
    }

    @Test
    void housesMustHaveMutualOpposites() {
        Board board = Board.create();
        Players players = board.getPlayers();
        List<House> housesOne = players.getPlayerOne().getHouses();
        List<House> housesTwo = players.getPlayerTwo().getHouses();
        assertThat(housesOne.get(0).getOpposite().get()).isEqualTo(housesTwo.get(5));
        assertThat(housesOne.get(1).getOpposite().get()).isEqualTo(housesTwo.get(4));
        assertThat(housesOne.get(2).getOpposite().get()).isEqualTo(housesTwo.get(3));
        assertThat(housesOne.get(3).getOpposite().get()).isEqualTo(housesTwo.get(2));
        assertThat(housesOne.get(4).getOpposite().get()).isEqualTo(housesTwo.get(1));
        assertThat(housesOne.get(5).getOpposite().get()).isEqualTo(housesTwo.get(0));
        assertThat(housesTwo.get(0).getOpposite().get()).isEqualTo(housesOne.get(5));
        assertThat(housesTwo.get(1).getOpposite().get()).isEqualTo(housesOne.get(4));
        assertThat(housesTwo.get(2).getOpposite().get()).isEqualTo(housesOne.get(3));
        assertThat(housesTwo.get(3).getOpposite().get()).isEqualTo(housesOne.get(2));
        assertThat(housesTwo.get(4).getOpposite().get()).isEqualTo(housesOne.get(1));
        assertThat(housesTwo.get(5).getOpposite().get()).isEqualTo(housesOne.get(0));
    }

    @Test
    void allPitsMustFormACycle() {
        Board board = Board.create();
        Pit first = board.getHouses().get(0);
        Pit pit = first;

        Set<Pit> all = new HashSet<>();
        all.add(pit);

        for (int i=0; i<14; i++) {
            pit = pit.next();
            all.add(pit);
        }

        assertThat(pit).isEqualTo(first);
        assertThat(all.size()).isEqualTo(14);
    }
}
