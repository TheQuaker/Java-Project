package Life;

public class Entity 
{
    double life;
    int x,y;
    char desckToken;
    
    public Entity(int i,int j, char token)
    {
        desckToken = token;
        x=i;
        y=j;
    }
    public double getLife()
    {
        return life;
    }
    
    public void setLife(double life)
    {
        this.life = life;
    }
    
    public char getToken()
    {
        return this.desckToken;
    }

}

