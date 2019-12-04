package a8.view;

import a8.board.Board;

public interface BoardListener {
    public void boardClicked(Board board, int x, int y);
}
