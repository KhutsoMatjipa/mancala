package com.game.mancala.model;

import java.util.Optional;

public class House extends Pit {

    private House opposite;

    House(PlayerNumber playerNumber, int stones) {
        super(playerNumber, stones);
    }

    @Override
    public Integer take() {
        int stones = this.stones;
        this.stones = 0;
        return stones;
    }

    @Override
    public Optional<House> getOpposite() {
        return Optional.ofNullable(opposite);
    }

    public void setOpposite(House opposite) {
        this.opposite = opposite;
    }

    boolean isSowable(PlayerNumber player) {
        return true;
    }

    @Override
    public String toString() {
        return "House{" +
                "stones=" + stones +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
