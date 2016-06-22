package Grid;

import Life.Animal;
import Life.Plant;
import java.util.*;

/**
 *
 * @author quaker
 */
public class Tile
{
    int x,y;
    char enviroment = '.';
    boolean planted = false;
    Plant p;
    List<Animal> animals;

    public Tile(int i,int j)
    {
        x = i;
        y = j;
        this.animals = new ArrayList<>();
    }
    
    public void set_enviroment(char enviroment)
    {//returns tiles token
        this.enviroment = enviroment;
    }
    
    public char get_enviroment()
    {//returns the token of the animal(if any) or the plant(if life>1) or the ground
        if(enviroment == '#')
            return enviroment;
        else if(!animals.isEmpty())
        {
            return animals.get(0).getToken();
        }
        else if (planted == true)
        {
            return p.getToken();
        }
        else
            return this.enviroment;
    }
    
    public void addAnimal(Animal animal)
    {//adds animal to tile
        animals.add(animal);
    }
    
    public void moveAnimals(int size, int season)
    {//calls the function that changes the animals coordinates
        Iterator<Animal> it = animals.iterator();
        while(it.hasNext())
        {
            Animal animal = it.next();
            animal.move(size,season);
        }
        /*animals.stream().forEach((animal) ->
        {
            animal.move(size,season);
        });*/
        
    }
    
    public void setMisplacedAnimals(Tile[][] terrain, int x, int y)
    {//checks if the animal is on the proper tile then sets it to it if needed
        int i, j;
        Iterator<Animal> it = animals.iterator();
        while(it.hasNext())
        {
            Animal animal = it.next();
            i = animal.get_x();
            j = animal.get_y();
            if (i!=x || j!=y)
            {
                terrain[i][j].addAnimal(animal);
                it.remove();
            }

        }
    }
    
    public void eat()
    {//go through the list of animals and calls the eat function
        char t;
        for (Animal animal1 : animals)
        {
            t = animal1.getToken();
            if (t == 'H')
            {//if there is a plant the herbivores can eat
                if(enviroment !='#')
                    animal1.eat(p);
            }
            else
            {
                Iterator<Animal> it = animals.iterator();
                while(it.hasNext())
                {// if there is an herbivore on the list the carnivore can eat it
                    Animal animal = it.next();
                    t = animal.getToken();
                    if(t=='H')
                    {
                        animal1.eat(animal);
                        break;
                    }
                }
            }
        }
    }
}
