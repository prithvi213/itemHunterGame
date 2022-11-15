public interface Constants
{
    public final int CELL_WIDTH = 50;
    public final int CELL_HEIGHT = 50;
    public final int NUM_COLS = 14;
    public final int NUM_ROWS = 14;
    public final int X_MIN = 50;
    public final int Y_MIN = 50;
    public final int X_MAX = X_MIN + (CELL_WIDTH * NUM_COLS);
    public final int Y_MAX = Y_MIN + (CELL_HEIGHT * NUM_ROWS);
    public final int PLAYER_WIDTH = 30;
    public final int PLAYER_HEIGHT = 30;
    public final int X_ADJ = (CELL_WIDTH - PLAYER_WIDTH) / 2;
    public final int Y_ADJ = (CELL_HEIGHT - PLAYER_HEIGHT) / 2;
    public final int GRID_DIST = 50;
    public final int ITEMS_LIVES_LABELS = 30;
}