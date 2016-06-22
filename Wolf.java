package Life;

import java.util.Random;

public class Wolf extends Carnivores
{
    Random rand = new Random();
    int maxEatCount = 8;  

    public Wolf(int i, int j, char token, char gender, boolean adult)
    {
        super(i, j, token, gender, adult);
        if(adult == true)
            eatCount = maxEatCount;
        else
            eatCount = 2;
    }
    
    @Override
    public void raise()
    {
        if(eatCount < maxEatCount)
            eatCount = eatCount + 2;
        if(eatCount == maxEatCount)
            setAdultTrue();
    }
    
    @Override
    public void eat(Animal animal)
    {
        animal.setLife(0);
        if(animal.adult == true)
        {//adult animals offer more food
            eatenFood = eatenFood + rand.nextInt(3)+3;
        }
        else
        {
            eatenFood = eatenFood + rand.nextInt(3)+1;
        }
    }
    
    @Override
    public boolean fertile(int season)
    {// checks if the animal is fertile
        if(season == 3 && adult == true)
            return true;
        else
            return false;
    }
    
    @Override
    public void move(int terrainSize,int season)
    {//changes the coordinates of the animal
        int c;
        for(int k=0; k<3; k++)
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
