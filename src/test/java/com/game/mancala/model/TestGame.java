package com.game.mancala.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.game.mancala.model.Game.Status.*;
import static com.game.mancala.model.PlayerNumber.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGame {

    @Test
    void playerOneMustMoveFirst() {
        Game game = Game.create(Board.create());
        Player player = game.getActivePlayer();
        assertThat(player.getNum()).isEqualTo(ONE);
    }

    @Test
    void mustRejectMoveByInactivePlayer() {
        Game game = Game.create(Board.create());
        assertThrows(IllegalStateException.class, () -> game.move(TWO, 1));
    }

    @Test
    void mustAllowPlayerToSow() {
        Game game = Game.create(Board.create());
        Game.Result result = game.move(ONE, 1);
        assertThat(result.getBoard().getPlayers().getPlayerOne().score()).isEqualTo(1);
        assertThat(result.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void mustAllowAlternateTurns() {
        Game game = Game.create(Board.create());

        Game.Result result = game.move(ONE, 2);
        assertThat(result.getNext()).as("Checking if next is Player Two: ").isEqualTo(TWO);

        result = game.move(TWO, 3);
        assertThat(result.getNext()).as("Checking if next is Player One: ").isEqualTo(ONE);
        assertThat(result.getStatus()).as("Checking if next is Player Two: ").isEqualTo(ACTIVE);
    }

    @Test
    void mustGetAnotherTurnWhenLandingInOwnStore() {
        Game game = Game.create(Board.create());
        Game.Result result = game.move(ONE, 1);
        assertThat(result.getNext()).isEqualTo(ONE);
        assertThat(result.getStatus()).isEqualTo(ACTIVE);
    }

    @Test
    void mustCaptureOppositeWhenLandingInOwnEmptyHouse() {
        Game game = Game.create(Board.create());
        game.move(ONE, 1);

        Game.Result result = game.move(ONE, 2);
        assertThat(result.getBoard().getPlayers().getPlayerTwo().getHouses().get(4).stones).as("House 2 of Player two must be captured: ").isEqualTo(0);
        assertThat(result.getBoard().getPlayers().getPlayerOne().score()).as("Checking total score is 9 : ").isEqualTo(9);

    }

    @Test
    void mustFinishGameWhenOnePlayerHasNoStones() {
        var board = Board.from(
                List.of(0, 0, 0, 0, 0, 1),
                36,
                List.of(6, 6, 6, 6, 6, 5),
                0
        );
        Game game = Game.create(board);

        Game.Result result = game.move(ONE, 6);
        Players players = board.getPlayers();
        assertThat(players.getPlayerOne().score()).isEqualTo(37);
        assertThat(players.getPlayerTwo().score()).isEqualTo(35);   //TODO: Remaining Stones should go to player 1
        assertThat(result.getStatus()).isEqualTo(Game.Status.PLAYER_ONE_WIN);
    }

    @Test
    void mustAllowDrawWhenSameStones() {
        var board = Board.from(
                List.of(0, 0, 0, 0, 0, 1),
                35,
                List.of(6, 6, 6, 6, 6, 6),
                0
        );
        Game game = Game.create(board);

        Game.Result result = game.move(ONE, 6);
        Players players = board.getPlayers();
        assertThat(players.getPlayerOne().score()).isEqualTo(36);
        assertThat(players.getPlayerTwo().score()).isEqualTo(36);
        assertThat(result.getStatus()).isEqualTo(Game.Status.DRAW);
    }

}
