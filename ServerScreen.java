import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Stack;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Iterator;
import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

// Have a Map object with Key,Value pairs
public class ServerScreen extends JPanel implements KeyListener, Constants
{
    private HashMap<Location,Integer> mapLocation;
    private Player player;
    private Player otherPlayer;
    private int items;
    private Font font;
    private Stack<Integer> nums;
    private int lives;
    private ObjectOutputStream outStrObj;
    private ObjectInputStream inStrObj;
    private Winner[] winDrops;
    private int playerItems;
    private int otherItems;
    private Color darkGreen;
    private Color darkBlue;
    private Color gray;

    public ServerScreen()
    {
        items = 0;
        mapLocation = new HashMap<Location,Integer>();
        player = new Player(X_MIN+X_ADJ,Y_MIN+Y_ADJ);
        otherPlayer = new Player(X_MIN+X_ADJ,Y_MIN+Y_ADJ);
        playerItems = player.getItems();
        otherItems = otherPlayer.getItems();
        winDrops = new Winner[500];
        font = new Font("Arial",Font.BOLD,14);
        darkGreen = new Color(22,71,27);
        darkBlue = new Color(10,15,66);
        gray = new Color(90,93,99);
        nums = new Stack<Integer>();
        nums.push(1);
        nums.push(2);
        nums.push(3);
        nums.push(4);
        nums.push(5);
        lives = nums.size();

        for(int i=0; i<winDrops.length; i++)
        {
            winDrops[i] = new Winner();
        }
        // 1 will be an item
        // 2 will be an obstacle
        mapLocation.put(new Location(12,12),3);
        mapLocation.put(new Location(12,13),3);
        mapLocation.put(new Location(13,12),3);
        mapLocation.put(new Location(13,13),3);
        mapLocation.put(new Location(0,12),3);
        mapLocation.put(new Location(0,13),3);
        mapLocation.put(new Location(1,12),3);
        mapLocation.put(new Location(1,13),3);
        mapLocation.put(new Location(5,5),4);
        mapLocation.put(new Location(5,6),4);
        mapLocation.put(new Location(6,5),4);
        mapLocation.put(new Location(6,6),4);
        mapLocation.put(new Location(12,0),4);
        mapLocation.put(new Location(12,1),4);
        mapLocation.put(new Location(13,0),4);
        mapLocation.put(new Location(13,1),4);
        mapLocation.put(new Location(9,3),5);
        mapLocation.put(new Location(9,4),5);
        mapLocation.put(new Location(10,3),5);
        mapLocation.put(new Location(10,4),5);
        mapLocation.put(new Location(0,7),5);
        mapLocation.put(new Location(0,8),5);
        mapLocation.put(new Location(1,7),5);
        mapLocation.put(new Location(1,8),5);

        while(mapLocation.size() < 39)
        {
            Location l = new Location((int)(Math.random() * 12)+1, (int)(Math.random() * 14));

            if(mapLocation.get(l) == null)
            {
                mapLocation.put(l,2);
            }
        }

        /*Location loc = new Location(12,12);

        if(mapLocation.get(loc) == null)
        {
            mapLocation.put(new Location(12,12),3);
        }
        
        mapLocation.put(new Location(13,13),3);
        mapLocation.put(new Location(13,12),3);
        mapLocation.put(new Location(12,13),3);
        mapLocation.put(new Location(0,12),3);
        mapLocation.put(new Location(0,13),3);
        mapLocation.put(new Location(1,12),3);
        mapLocation.put(new Location(1,13),3);*/

        while(mapLocation.size() < 50)
        {
            Location l = new Location((int)(Math.random() * 12)+1, (int)(Math.random() * 14));

            if(mapLocation.get(l) == null)
            {
                mapLocation.put(l,1);
            }
        }

        this.setFocusable(true);
        addKeyListener(this);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(800,800);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,800);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Lakes in navy",450,20);
        g.drawString("Boulders in gray",550,20);
        g.drawString("Trees in green",670,20);
        int x = X_MIN;
        int y = Y_MIN;

        for(int r=0; r<NUM_ROWS; r++)
        {
            for(int c=0; c<NUM_COLS; c++)
            {
                g.setColor(Color.BLACK);
                g.drawRect(x+c*CELL_WIDTH,y+r*CELL_HEIGHT,CELL_WIDTH,CELL_HEIGHT);

                Integer result = mapLocation.get(new Location(r,c));

                if(result != null && result == 2)
                {
                    g.setColor(Color.BLUE);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ,y+r*CELL_HEIGHT+Y_ADJ,PLAYER_WIDTH,PLAYER_HEIGHT);
                    g.setColor(darkBlue);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ+5,y+r*CELL_HEIGHT+Y_ADJ+5,20,20);
                }

                else if(result != null && result == 1)
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ,y+r*CELL_HEIGHT+Y_ADJ,PLAYER_WIDTH,PLAYER_HEIGHT);
                    g.setColor(darkGreen);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ+5,y+r*CELL_HEIGHT+Y_ADJ+5,20,20);
                }

                else if(result != null && result == 3)
                {
                    g.setColor(darkGreen);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ,y+r*CELL_HEIGHT+Y_ADJ,PLAYER_WIDTH,PLAYER_HEIGHT);
                }

                else if(result != null && result == 4)
                {
                    g.setColor(darkBlue);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ,y+r*CELL_HEIGHT+Y_ADJ,PLAYER_WIDTH,PLAYER_HEIGHT);
                }

                else if(result != null && result == 5)
                {
                    g.setColor(gray);
                    g.fillRect(x+c*CELL_WIDTH+X_ADJ,y+r*CELL_HEIGHT+Y_ADJ,PLAYER_WIDTH,PLAYER_HEIGHT);
                }
            }
        }

        for(int i=0; i<nums.size(); i++)
        {
            g.setColor(Color.RED);
            g.fillRect(200+(i*50),10,40,30);
        }

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Items: " + items,50,ITEMS_LIVES_LABELS);
        g.drawString("Lives: " + lives,120,ITEMS_LIVES_LABELS);

        otherPlayer.drawMe2(g);

        if(player != null)
        {
            player.drawMe(g);
        }

        if(lives <= 0)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,800,800);
            g.setColor(Color.CYAN);
            font = new Font("Arial",Font.BOLD,30);
            g.setFont(font);
            g.drawString("PLAYER 2 WINS",275,400);
            g.drawString("YOU LOSE!",300,450);
        }

        else if(otherPlayer.getLives() <= 0)
        {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,800,800);
            g.setColor(Color.RED);
            font = new Font("Arial",Font.BOLD,30);
            g.setFont(font);
            g.drawString("PLAYER 1 WINS",275,400);
            g.drawString("YOU WIN!",300,450);
            System.out.println(player.getLives());

            for(int j=0; j<winDrops.length; j++)
            {
                winDrops[j].drawMe(g,Color.RED);
            }

            for(int k=0; k<winDrops.length; k++)
            {
                winDrops[k].move();
            }
        }

        else if(mapLocation.size() < 40)
        {
            //int playerItems = player.getItems();
            //int otherItems = otherPlayer.getItems();

            if(items > otherPlayer.getItems())
            {
                g.setColor(Color.WHITE);
                g.fillRect(0,0,800,800);
                g.setColor(Color.RED);
                font = new Font("Arial",Font.BOLD,30);
                g.setFont(font);
                g.drawString("PLAYER 1 WINS",275,400);
                g.drawString("YOU WIN!",300,450);

                for(int j=0; j<winDrops.length; j++)
                {
                    winDrops[j].drawMe(g,Color.RED);
                }

                for(int k=0; k<winDrops.length; k++)
                {
                    winDrops[k].move();
                }

                System.out.println("PLAYER 1 HAS WON!");
            }

            else
            {
                g.setColor(Color.BLACK);
                g.fillRect(0,0,800,800);
                g.setColor(Color.CYAN);
                font = new Font("Arial",Font.BOLD,30);
                g.setFont(font);
                g.drawString("PLAYER 2 WINS",275,400);
                g.drawString("YOU LOSE!",300,450);
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            player.moveLeft();
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            player.moveUp();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            player.moveRight();
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            player.moveDown();
        }

        int row = player.getRow();
        int col = player.getCol();
        System.out.println("Row: " + row + " | Col: " + col);

        Location newLoc = new Location(row,col);

        if(mapLocation.get(newLoc) != null && mapLocation.get(newLoc) == 1)
        {
            System.out.println("HELLO1");
            items++;
            playerItems++;
            player.addItems();
            mapLocation.remove(newLoc);
            System.out.println(items + " | " + otherPlayer.getItems());
            player.playItem();
        }

        else if(mapLocation.get(newLoc) != null && mapLocation.get(newLoc) == 2)
        {
            System.out.println("HELLO2");
            player.setPosition(X_MIN+X_ADJ,Y_MIN+Y_ADJ);
            nums.pop();
            lives--;
            player.setLives(nums.size());
            player.playObstacle();
        }

        try
        {
            outStrObj.reset();
            outStrObj.writeObject(mapLocation);
            outStrObj.reset();
            System.out.println("Sending player");
            outStrObj.writeObject(player);
        }
        
        catch(IOException e2)
        {
            System.err.println("Couldn't get I/O for the connection");
            e2.printStackTrace();
            //System.exit(1);
        }

        repaint();
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e)
    {
        
    }

    public void poll() throws IOException
    {
        int portNumber = 52137;
        ServerSocket objServerSocket = new ServerSocket(portNumber);
        //System.out.println("listening on port: " + objServerSocket.getLocalPort());
        
        try
        {
            Socket clientSocket = objServerSocket.accept();
            System.out.println("connection received from " + clientSocket.getInetAddress().getHostName());
            outStrObj = new ObjectOutputStream(clientSocket.getOutputStream());
            outStrObj.flush();
            inStrObj = new ObjectInputStream(clientSocket.getInputStream());
            outStrObj.reset();
            outStrObj.writeObject(mapLocation);

            while(true)
            {
                Object obj = inStrObj.readObject();

                if(obj instanceof HashMap)
                {
                    mapLocation = (HashMap<Location,Integer>)obj;
                }

                if(obj instanceof Player)
                {
                    otherPlayer = (Player)obj;
                }

                System.out.println("Receive");
                repaint();
            }
        }

        catch(ClassNotFoundException e)
        {
            System.err.println("Class does not exist");
            e.printStackTrace();
            //System.exit(1);
        }
        
        catch(IOException e)
        {
            System.err.println("Couldn't get I/O for the connection");
            e.printStackTrace();
            //System.exit(1);
        } 
        
        finally
        {
            outStrObj.close();
            inStrObj.close();
            objServerSocket.close();
        }
    }

    /*public void animate()
    {
        if((playerItems > otherPlayer.getItems()) || (playerItems < otherPlayer.getItems()) || otherPlayer.getLives() <= 0 || lives <= 0)
        {
            while(true)
            {
                try
                {
                    Thread.sleep(10);
                    //counter += 10;
                }

                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }

                repaint();
                if(counter == 3570)
                {
                    counter = 0;
                    season++;
                    if(season == 5){
                        season = 1;
                    }
                }
            }
        }
    }*/
}