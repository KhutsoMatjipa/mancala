package com.game.mancala.model;

import java.util.Optional;

public abstract class Pit {

    private Pit next;
    protected int stones;
    protected final PlayerNumber playerNumber;

    public Pit(PlayerNumber playerNumber, int stones) {
        this.playerNumber = playerNumber;
        this.stones = stones;
    }

    public Integer count() {
        return stones;
    }

    public Pit next() {
        return next;
    }

    public Pit setNext(Pit next) {
        this.next = next;
        return next;
    }

    public void sow() {
        this.stones++;
    }

    public PlayerNumber getOwner() {
        return playerNumber;
    }

    abstract boolean isSowable(PlayerNumber player);

    public boolean isEmpty() {
        return this.stones == 0;
    }

    public Optional<House> getOpposite() {
        return Optional.empty();
    }

    public Integer capture() {
        if (this.getOpposite().isEmpty()) {
            return 0;
        }
        return this.getOpposite().get().take();
    }

    public Integer take() {
        return 0;
    }
}
