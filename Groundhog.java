package Life;

import java.util.Random;

public class Groundhog extends Herbivores
{
    int maxEatCount = 5;
    Random rand = new Random();
    
    public Groundhog(int i, int j, char token, char gender, boolean adult) 
    {
        super(i, j, token, gender, adult);
        if(adult == true)
            eatCount = maxEatCount;
        else
            eatCount = 3;
    }
    
    @Override
    public void raise()
    {
        if(eatCount < maxEatCount)
            eatCount++;
        if(eatCount == maxEatCount)
            setAdultTrue();
    }
    
    @Override
    public void eat(Plant p)
    {
        if(p.life>1)//checks if the plant can be eaten
        {
            if(p.life < 2)//checks if the plant has enough life 
            {// if not the animal eats what it can
                p.loseLife(p.life - 1);
                eatenFood = eatenFood + (p.life-1);
            }
            else
            {//eats normaly
                p.loseLife(1);
                eatenFood = eatenFood + 1;
            }
        }
    }
    
    @Override
    public boolean fertile(int season)
    {// checks if the animal is fertile
        if(season == 2 && adult == true)
            return true;
        else
            return false;
    }
    
    @Override
    public void move(int terrainSize,int season)
    {//changes the coordinates of the animal
        int c;
        int m=3;
        if(season==4)
        {
            m=1;
        }
        for(int k=0; k<m; k++)
        {
            c = rand.nextInt(4);
            switch(c)
            {
                case 0://go up
                    if(x+1 < terrainSize)
                    {
                        x++;
                        break;
                    }
                    else
                    {
                        k--;
                        break;
                    }
                case 1://go down
                    if(x-1 >= 0)
                    {
                        x--;
                        break;
                    }
                    else
                    {
                        k--;
                        break;
                    }
                case 2://go left
                    if(y+1 < terrainSize)
                    {
                        y++;
                        break;
                    }
                    else
                    {
                        k--;
                        break;
                    }
                case 3://go right
                    if(y-1 >= 0)
                    {
                        y--;
                        break;
                    }
                    else
                    {
                        k--;
                        break;
                    }
            }
        }
    }
}
