package com.example.service;

import org.springframework.stereotype.Component;

import com.example.model.Board;
import com.example.model.Stone;


//合法手判定専用クラス
//「その場所に置けるか？」のみを判定する責務を持つ

@Component
public class MoveValidator {

    // 8方向ベクトル
    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

	 //指定プレイヤーが置ける場所があるか判定
    public boolean hasAnyValidMove(Board board, Stone player) {

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {

                if (board.getStone(row, col) == Stone.EMPTY &&
                        canFlipAnyDirection(board, row, col, player)) {
                    return true;
                }
            }
        }
        return false;
    }
	 
	 //8方向のどこかで挟めるか
    public boolean canFlipAnyDirection(
            Board board, int row, int col, Stone player) {

        for (int[] dir : DIRECTIONS) {
            if (canFlipDirection(
                    board, row, col,
                    dir[0], dir[1], player)) {
                return true;
            }
        }
        return false;
    }
	 
	 //1方向で挟めるか判定
    private boolean canFlipDirection(
            Board board,
            int row, int col,
            int dRow, int dCol,
            Stone player) {

        int r = row + dRow;
        int c = col + dCol;

        if (!isInBounds(board, r, c)) return false;

        if (board.getStone(r, c) != player.opposite()) {
            return false;
        }

        r += dRow;
        c += dCol;

        while (isInBounds(board, r, c) &&
                board.getStone(r, c) == player.opposite()) {
            r += dRow;
            c += dCol;
        }

        return isInBounds(board, r, c)
                && board.getStone(r, c) == player;
    }


     //盤面内チェック    
    private boolean isInBounds(Board board, int row, int col) {
        return row >= 0 && row < board.getSize()
                && col >= 0 && col < board.getSize();
    }
}
