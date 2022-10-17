package com.game.mancala.controller;

import com.game.mancala.model.Board;
import com.game.mancala.model.Game;
import com.game.mancala.model.PlayerNumber;
import com.game.mancala.model.Players;
import com.game.mancala.service.MancalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MancalaController {

    @Autowired
    MancalaService service;

    Game game;

    @GetMapping("/")
    public String newGame(ModelMap model) {
        game = Game.create(Board.create());

        Players players =  game.getBoard().getPlayers();
        model.put("player1", players.getPlayerOne());
        model.put("player2", players.getPlayerTwo());

        return "index";
    }

    @GetMapping("/move/{num}/{id}")
    public String makeMove(@PathVariable("num") int num,@PathVariable("id") int id, ModelMap model) {

        PlayerNumber playerNumber = num == 1 ? PlayerNumber.ONE : PlayerNumber.TWO;
        Game.Result result = game.move(playerNumber, id);
        model.put("player1", result.getBoard().getPlayers().getPlayerOne());
        model.put("player2", result.getBoard().getPlayers().getPlayerTwo());
        return "index";
    }
}
