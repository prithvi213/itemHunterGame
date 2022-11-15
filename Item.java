import java.awt.Color;
import java.awt.Graphics;

public class Item implements Constants
{
    private int x;
    private int y;
    private int width;
    private int height;

    public Item(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
    }

    public void drawMe(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.fillRect(x,y,width,height);
    }
}