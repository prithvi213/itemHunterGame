import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player implements Constants, Serializable
{
    private int x;
    private int y;
    private int width;
    private int height;
    private int lives;
    private int items;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.lives = 5;
        this.items = 0;
    }

    public void drawMe(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(x,y,width,height);
        g.setColor(Color.YELLOW);
        g.fillRect(x+5,y+5,20,20);
    }

    public void drawMe2(Graphics g)
    {
        g.setColor(Color.CYAN);
        g.fillRect(x,y,width,height);
        g.setColor(Color.YELLOW);
        g.fillRect(x+5,y+5,20,20);
    }

    public void moveUp()
    {
        if(y > Y_MIN+Y_ADJ)
        {
            y -= GRID_DIST;
        }
    }

    public void moveDown()
    {
        if(y < Y_MAX-Y_ADJ-PLAYER_WIDTH)
        {
            y += GRID_DIST;
        }    
    }

    public void moveLeft()
    {
        if(x > X_MIN+X_ADJ)
        {
            x -= GRID_DIST;
        }
    }

    public void moveRight()
    {
        if(x < X_MAX-X_ADJ-PLAYER_WIDTH)
        {
            x += GRID_DIST;
        }
    }

    public int getRow()
    {
        return (y/GRID_DIST)-1;
    }

    public int getCol()
    {
        return (x/GRID_DIST)-1;
    }

    public void setPosition(int newX, int newY)
    {
        this.x = newX;
        this.y = newY;
    }

    public void playItem()
    {
        try
        {
            URL url = this.getClass().getClassLoader().getResource("sounds/hit.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        }

        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void playObstacle()
    {
        try
        {
            URL url = this.getClass().getClassLoader().getResource("sounds/lose.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        }

        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public int getLives()
    {
        return lives;
    }

    public void setLives(int l)
    {
        lives = l;
    }

    public int getItems()
    {
        return items;
    }

    public void addItems()
    {
        items++;
    }
}