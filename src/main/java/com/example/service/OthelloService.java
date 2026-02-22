package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.Board;
import com.example.model.Stone;

//ゲーム進行管理クラス
//ターン管理
//石配置
//ゲーム終了判定

@Service
public class OthelloService {
	
	private final Board board = new Board();
	private final MoveValidator validator;
    private Stone currentTurn = Stone.BLACK;
    private boolean lastMoveWasPass = false;
    
    
    public OthelloService(MoveValidator validator) {
        this.validator = validator;
    }
    
    
    public Board getBoard() {
        return board;
    }
    
    
    public Stone getCurrentTurn() {
        return currentTurn;
    }
 
    //石配置処理
    public void placeStone(int row, int col) {
    	
    	//すでに石がある場合は何も実行しない
    	if(board.getStone(row, col) != Stone.EMPTY) {
    		return;
    	}
    	
    	
    	 // 合法手でなければ置かない
        if (!validator.canFlipAnyDirection(board, row, col, currentTurn)) {
            return;
        }
    	
    	//石を置く
    	board.setStone(row, col, currentTurn);
    	
    	//反転処理
    	flipAllDirections(row, col);
    	
    	//次プレイヤー判定
    	Stone nextPlayer = currentTurn.opposite();
    	
    	if (validator.hasAnyValidMove(board, nextPlayer)) {
            currentTurn = nextPlayer;
            lastMoveWasPass = false;
        }
        else if (validator.hasAnyValidMove(board, currentTurn)) {
            // 相手が置けない → パス（自分のまま）
        	lastMoveWasPass = true;
        }
        else {
            // 両者置けない → ゲーム終了
        	lastMoveWasPass = false;
        }
    	 

    }
    
    //8方向反転処理
    private void flipAllDirections(int row, int col) {

        int[][] directions = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1},  {1, 0},  {1, 1}
        };

        for (int[] dir : directions) {
            flipDirection(row, col, dir[0], dir[1]);
        }
    }
    
        
    //1方向の反転処理   
    private void flipDirection(int row, int col, int dRow, int dCol) {

        int r = row + dRow;
        int c = col + dCol;

        if (!isInBounds(r, c)) return;
        if (board.getStone(r, c) != currentTurn.opposite()) return;

        r += dRow;
        c += dCol;

        while (isInBounds(r, c) &&
                board.getStone(r, c) == currentTurn.opposite()) {
            r += dRow;
            c += dCol;
        }

        if (!isInBounds(r, c)
                || board.getStone(r, c) != currentTurn) {
            return;
        }

        int flipR = row + dRow;
        int flipC = col + dCol;

        while (flipR != r || flipC != c) {
            board.setStone(flipR, flipC, currentTurn);
            flipR += dRow;
            flipC += dCol;
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < board.getSize()
                && col >= 0 && col < board.getSize();
    }

    public int countStones(Stone stone) {
        int count = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getStone(row, col) == stone) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isGameOver() {
        return !validator.hasAnyValidMove(board, Stone.BLACK)
                && !validator.hasAnyValidMove(board, Stone.WHITE);
    }

    public Stone getWinner() {

        int black = countStones(Stone.BLACK);
        int white = countStones(Stone.WHITE);

        if (black > white) return Stone.BLACK;
        if (white > black) return Stone.WHITE;

        return null;
    }

    public boolean wasLastMovePass() {
        return lastMoveWasPass;
    }
}