package com.game.mancala.model;

public class Store extends Pit {

    Store(PlayerNumber playerNumber) {
        super(playerNumber, 0);
    }

    Store(PlayerNumber playerNumber, int stones) {
        super(playerNumber, stones);
    }

    public void sow(int i) {
        stones += i;
    }

    boolean isSowable(PlayerNumber player) {
        return player.equals(playerNumber);
    }

    @Override
    public String toString() {
        return "Store{" +
                "stones=" + stones +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
