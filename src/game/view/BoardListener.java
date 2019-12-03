package game.view;

import game.board.Board;

public interface BoardListener {
    public void boardClicked(Board board, int x, int y);
}
