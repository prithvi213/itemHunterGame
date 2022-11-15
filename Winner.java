import java.awt.Color;
import java.awt.Graphics;

public class Winner
{
    private int x;
    private int y;

    public Winner()
    {
        x = (int)(Math.random() * 800);
        y = (int)(Math.random() * 800);
    }

    public void drawMe(Graphics g, Color c)
    {
        g.setColor(c);  
        g.fillOval(x,y,2,5);
    }

    public void move()
    {
        y+=5;
        
        if(y>800)
        {
            y = 10;
            x = (int)(Math.random() * 800);
        }
    }
}