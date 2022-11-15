import java.awt.Color;
import java.awt.Graphics;

public class Obstacle implements Constants
{
    private int x;
    private int y;
    private int width;
    private int height;

    public Obstacle(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
    }

    public void drawMe(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
    }
}