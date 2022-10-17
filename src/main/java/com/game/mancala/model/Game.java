package com.game.mancala.model;

import static com.game.mancala.model.PlayerNumber.*;

public class Game {

    public enum Status {

        ACTIVE,

        DRAW,

        PLAYER_ONE_WIN,

        PLAYER_TWO_WIN

    }

    public class Result {
        private Status status;
        private PlayerNumber next;
        private Board board;

        public Result(Status status, PlayerNumber next, Board board) {
            this.status = status;
            this.next = next;
            this.board = board;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public PlayerNumber getNext() {
            return next;
        }

        public void setNext(PlayerNumber next) {
            this.next = next;
        }

        public Board getBoard() {
            return board;
        }

        public void setBoard(Board board) {
            this.board = board;
        }
    }

    private Board board;
    private Player player;
    private Status status;

    public static Game create(Board board) {
        Game game = new Game();
        game.board = board;
        game.player = board.getPlayers().getPlayerOne();
        game.status = Status.ACTIVE;
        return game;
    }

    public Result move(PlayerNumber num, int house) {
        if (!player.getNum().equals(num)) {
            throw new IllegalStateException(String.format("Player %s cannot take their turn yet", num));
        }
        Pit landed = player.turn(house);
        if (player.complete()) {
            otherPlayer().finish();
            status = declareWinner();
        }
        player = nextPlayer(landed);
        return new Result(status, player.getNum(), board);
    }

    private Status declareWinner() {
        Players players = board.getPlayers();
        int score1 = players.getPlayerOne().score();
        int score2 = players.getPlayerTwo().score();
        if (score1 > score2) {
            return Status.PLAYER_ONE_WIN;
        }
        if (score2 > score1) {
            return Status.PLAYER_TWO_WIN;
        }
        return Status.DRAW;
    }

    public Player nextPlayer(Pit landed) {
        if (landed.equals(player.getStore())) {
            return player;
        }
        return otherPlayer();
    }

    private Player otherPlayer() {
        Players players = board.getPlayers();

        return (player.getNum().equals(ONE) ? players.getPlayerTwo() : players.getPlayerOne());
    }

    public Player getActivePlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
