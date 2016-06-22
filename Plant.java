package Life;

import java.util.Random;

public class Plant extends Entity
{
    Random rand = new Random();

    public Plant(int i, int j, char token)
    {
        super(i, j, token);
        setLife(rand.nextInt(9) + 2);//sets life between 2 and 10
    }


    
    public double lifeFactor()
    {//returns life factor a value between 0.1 and 1
        int n ;
        n = rand.nextInt(10)+1;
        return n/10.0;
    }
    
    public void plantGrow()
    {//increases the plant life
        double n;
        double l = getLife();
        n = lifeFactor();
        if(l == 1)
        {// if the plants life == 1 get a biger "life factor"
            n = rand.nextInt(4)+2;
        }
        n = n * l;
        if(n%1==0)
        {//rounds up the value
            setLife((int)l + (int)n);
        }
        else
        {
            setLife((int)l + (int)n+1);
        }
    }
    
    public void loseLife(double lifeLost)
    {
        setLife(getLife() - lifeLost);
    }
    
}
