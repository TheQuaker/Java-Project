package Life;

public class Animal extends Entity 
{
    int hungerCount = 0;
    double eatenFood = 0;
    int eatCount;
    char gender;
    boolean adult;
    String breedingPeriod;
    
    public Animal(int i, int j, char token, char gender, boolean adult)
    {
        super(i,j,token);
        setLife(1);
        this.gender = gender;
        this.adult = adult;
        life = 1;
    }
    
    public char getGender()
    {
        return gender;
    }
    
    public int get_x()
    {
        return x;
    }
    
    public int get_y()
    {
        return y;
    }
    
    public void setAdultTrue()
    {
        adult = true;
    }
    
    public double getEatenFood()
    {
        return eatenFood;
    }
    public int getEatCount()
    {
        return eatCount;
    }
    
    public int getHungerCount()
    {
        return hungerCount;
    }
    
    public void setHungerCount()
    {
        hungerCount = 0;
    }
    
    public void addHungerCount()
    {
        hungerCount++;
    }
    
    public void move(int terrainSize, int season){}
    
    public void eat(Animal animal){}
    
    public void eat(Plant plant){}
        
    public void raise(){}
    
    public boolean fertile(int season){return false;}

}
