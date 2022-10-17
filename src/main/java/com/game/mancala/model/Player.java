package com.game.mancala.model;

import java.util.List;
import java.util.Objects;

public class Player {

    private PlayerNumber num;
    private List<House> houses;
    private Store store;

    public Player(PlayerNumber num, List<House> houses, Store store) {
        this.num = num;
        this.houses = houses;
        this.store = store;
    }

    public Pit turn(int houseNum) {
        House house = getHouse(houseNum);
        hasStones(house);
        Pit pit = takeTurn(house);
        if (captureOpposite(pit)) {
            store.sow(pit.take());
            store.sow(pit.capture());
        }
        return pit;
    }

    public boolean complete() {
        return houses.stream().allMatch(House::isEmpty);
    }

    public void finish() {
        for (House house: houses) {
            store.sow(house.take());
        }
    }

    public int score() {
        return store.count();
    }

    private boolean captureOpposite(Pit pit) {
        return pit.stones == 0 && pit.getOpposite().isPresent();
    }

    private void hasStones(House house) {
        if (house.isEmpty()) {
            throw new IllegalArgumentException("House must have stones to take turn");
        }
    }

    private Pit takeTurn(House house) {
        Integer stones = house.take();
        Pit pit = house;
        while (stones > 0) {
            pit = pit.next();

            if (house.playerNumber.equals(PlayerNumber.ONE) && pit.getOwner().equals(PlayerNumber.TWO))
                pit = getHouse(1);
            else if (house.playerNumber.equals(PlayerNumber.TWO) && pit.getOwner().equals(PlayerNumber.ONE))
                pit = getHouse(1);

            if (pit.isSowable(num)) {
                stones--;
                pit.sow();
            }

            if (stones == 0 && pit.stones == 1)
                store.sow(pit.take());
        }
        return pit;
    }

    private House getHouse(int houseNum) {
        if (houseNum < 1 || houseNum > houses.size()) {
            throw new IllegalArgumentException("House number must be between 1 and " + houses.size());
        }
        return this.houses.get(houseNum - 1);
    }

    public PlayerNumber getNum() {
        return num;
    }

    public void setNum(PlayerNumber num) {
        this.num = num;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return num == player.num && houses.equals(player.houses) && store.equals(player.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, houses, store);
    }

    @Override
    public String toString() {
        return "Player{" +
                "num=" + num +
                ", houses=" + houses +
                ", store=" + store +
                '}';
    }
}
