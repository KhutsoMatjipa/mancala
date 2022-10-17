package com.game.mancala.model;

import java.util.Objects;

public class Players {

    private Player playerOne;
    private  Player playerTwo;

    public Players(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Players)) return false;
        Players players = (Players) o;
        return Objects.equals(playerOne, players.playerOne) && Objects.equals(playerTwo, players.playerTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOne, playerTwo);
    }

    @Override
    public String toString() {
        return "Players{" +
                "playerOne=" + playerOne +
                ", playerTwo=" + playerTwo +
                '}';
    }
}
