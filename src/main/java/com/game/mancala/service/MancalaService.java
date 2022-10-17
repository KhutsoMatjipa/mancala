package com.game.mancala.service;

import com.game.mancala.model.Game;
import com.game.mancala.model.Result;

public interface MancalaService {

    public Game create();
    public Result move();
}
