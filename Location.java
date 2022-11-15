import java.io.Serializable;

public class Location implements Serializable
{
    private int x;
    private int y;

    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void printLocation()
    {
        System.out.println("Location at:" + x + ", " + y);
    }

    public Location getLocation()
    {
        return new Location(x,y);
    }

    public int hashCode()
    {
        return (31 * x) + y;
    }

    public boolean equals(Object o)
    {
        Location l = (Location)o;

        if(l.hashCode() == this.hashCode())
        {
            return true;
        }

        return false;
    }
}