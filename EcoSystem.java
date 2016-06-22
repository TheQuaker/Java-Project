package Grid;

import Life.*;
import java.util.*;

public class EcoSystem
{
    int daysOfsimulation;
    int durationOfSeason;
    Random rand =new Random();
    String season ;
    int sizeOfSpecies;
    int terrainSize;
    int lakeSize;
    Tile[][] terrain;
    
    public static void main(String [] args)
    {
        int N = args.length;
        int[] iargs = new int[N];
        if(args.length == 2)
        {
            for (int i=0; i<N; i++)
            {
                iargs[i] = Integer.parseInt(args[i]);
            }
        }
        else
            System.exit(1);
        
        EcoSystem eco = new EcoSystem(iargs[0], iargs[1], 100, 20, 3, "Spring");
        eco.createTerrain();
        eco.generateRiver();
        eco.genereteLake();
        eco.placePlants();
        eco.placeAnimals();
        eco.runEcosystem();

    }
    
    public EcoSystem(int daysOfsimulation, int durationOfSeason, int sizeOfSpecies, int terrainSize,int lakeSize, String startingSeason)
    {
        this.daysOfsimulation = daysOfsimulation;
        this.durationOfSeason = durationOfSeason;
        this.sizeOfSpecies = sizeOfSpecies;
        this.terrainSize = terrainSize;
        this.lakeSize = lakeSize;
        season = startingSeason;
        terrain = new Tile[this.terrainSize][this.terrainSize];
    }
    
    public void runEcosystem()
    {
        printSystem();
        printAnimalStatistics();
        for(int n=1; n < daysOfsimulation ; n++)
        {
            dailyReset(n);
            for(int h=0; h<24; h++)
            {
                animalMovement();
                animalEating();
                checkDeadEntities();
            }
            generateOffspring();
            checkHunger();
            checkDeadEntities();
        }
    }

    public void ChangeSeason()
    {
        if(season != null)
        switch (season)
        {
            case "Spring":
                season = "Summer";
                break;
            case "Summer":
                season = "Autumn";
                break;
            case "Autumn":
                season = "Winter";
                break;
            case "Winter":
                season = "Spring";
                break;
        }
    }

    public int getSeason(String season)
    {
        int s = 0;
        switch (season)
        {
            case "Spring":
                s = 1;
                break;
            case "Summer":
                s = 2;
                break;
            case "Autumn":
                s = 3;
                break;
            case "Winter":
                s = 4;
                break;
        }
        return s;
    }

    public void plantGrow()
    {// calls the function for plant growth for every tile of the grid with plant
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                if(terrain[i][j].get_enviroment() == '^')
                    terrain[i][j].p.plantGrow();
            }
    }
    
    public void createTerrain()
    {//greates the grid of the eco system
        terrain = new Tile[terrainSize][terrainSize];
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
                terrain[i][j] = new Tile(i,j);
    }
    
    public void printSystem()
    {//prints the grid and the amount of plants and animals
        int p=0,c=0,h=0;
        char e;
        for (int i = 0; i < terrainSize; i ++)
        {
            for (int j = 0; j < terrainSize; j++)
            {
                e = terrain[i][j].get_enviroment();
                System.out.print(e);
                if(e!='#')
                {
                    if(terrain[i][j].planted == true && terrain[i][j].p.getLife() > 1)
                        p++;
                }
                Iterator<Animal> it = terrain[i][j].animals.iterator();
                while(it.hasNext())
                {
                    Animal animal = it.next();
                    if(animal.getToken() == 'C')
                        c++;
                    else
                        h++;
                }
            }
            System.out.print("\n");
        }
        System.out.println("\nThe system has "+p+" plants "+h+" herbivores and "+c+" carnivores");
    }
    
    public void generateRiver()
    {//creates the river of the ecosystem
        int j, route;
        j = rand.nextInt((terrainSize-9)) + 5;
        for(int i=0 ; i<terrainSize ; i++)
        {
            terrain[i][j].set_enviroment('#');
            if((rand.nextInt(100))<25)
            {
                route = rand.nextInt(4) + 1;
                switch (route)
                {
                    case 1:
                        for(int c=0 ; c<2 ; c++)
                        {
                            if((j-1)<0)
                                break;
                            else
                            {
                                j--;
                                terrain[i][j].set_enviroment('#');
                            }
                        }
                        break;
                    case 2:
                            if((j-1)<0)
                            {
                                break;
                            }
                            else
                            {
                                j--;
                                terrain[i][j].set_enviroment('#');
                            }
                        break;
                    case 3:
                            if((j+1)>=terrainSize)
                            {
                                break;
                            }
                            else
                            {
                                j++;
                                terrain[i][j].set_enviroment('#');
                            }
                        break;
                    case 4:
                        for(int c=0 ; c<2 ; c++)
                        {
                            if((j+1)>=terrainSize)
                                break;
                            else
                            {
                                j++;
                                terrain[i][j].set_enviroment('#');
                            }
                        }
                        break;
                }
            }
        }
    }
    
    public void genereteLake()
    {//creates the lake of the ecosystem
        int i,j;
        i = rand.nextInt(terrainSize-lakeSize);
        j = rand.nextInt(terrainSize-lakeSize);
        for(int k=0;k<lakeSize;k++)
        {
		for(int m=0;m<lakeSize;m++)
                {
			terrain[i+m][j].set_enviroment('#');
                }
                j++;
        }
    }
    
    public void placePlants()
    {//puts plants in every tile exept the '#'(water) tiles
        for(int i=0; i<terrainSize; i++)
        {
            for(int j=0; j<terrainSize; j++)
            {
                if(terrain[i][j].get_enviroment() == '.')
                {
                    terrain[i][j].p= new Plant(i, j, '^');
                    terrain[i][j].planted = true;
                }
            }
        }
    }
    
    public void placeAnimals()
    {//puts animals of each species on random places on the map
        Animal a;
        int i,j;
        for(int n=0; n<sizeOfSpecies; n++)
        {
            i=rand.nextInt(terrainSize);
            j=rand.nextInt(terrainSize);
            if(terrain[i][j].get_enviroment()!='#')
            {
                if(n<sizeOfSpecies/2)
                {
                    a = new Wolf(i, j, 'C', 'm', true);
                    terrain[i][j].addAnimal(a);
                }
                else
                {
                    a = new Wolf(i, j, 'C', 'f', true);
                    terrain[i][j].addAnimal(a);
                }
            }
            else
                n--;
        }
        for(int n=0; n<sizeOfSpecies; n++)
        {
            i=rand.nextInt(terrainSize);
            j=rand.nextInt(terrainSize);
            if(terrain[i][j].get_enviroment()!='#')
            {
                if(n<sizeOfSpecies/2)
                {
                    a = new Groundhog(i, j, 'H','m', true);
                    terrain[i][j].addAnimal(a);
                }
                else
                {
                    a = new Groundhog(i, j, 'H','f', true);
                    terrain[i][j].addAnimal(a);
                }
            }
            else
                n--;
        }
        for(int n=0; n<sizeOfSpecies; n++)
        {
            i=rand.nextInt(terrainSize);
            j=rand.nextInt(terrainSize);
            if(terrain[i][j].get_enviroment()!='#')
            {
                if(n<sizeOfSpecies/2)
                {
                    a = new Rabbit(i, j, 'H', 'm', true);
                    terrain[i][j].addAnimal(a);
                }
                else
                {
                    a = new Rabbit(i, j, 'H', 'f', true);
                    terrain[i][j].addAnimal(a);
                }
            }
            else
                n--;
        }
    }
    
    public void animalMovement()
    {//calls 2 functions first to change the animals coordinates 
        //and then to place them to the correct tile
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                int s = getSeason(season);
                terrain[i][j].moveAnimals(terrainSize, s);
            }
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                 terrain[i][j].setMisplacedAnimals(terrain, i, j);
            }
    }
    
    public void animalEating()
    {//calls the eat function for every tile in the grid
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
                    terrain[i][j].eat();
    }
    
    public void checkDeadEntities()
    {//scans the grig in order to find animals with life == 0 (dead)
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                Iterator<Animal> it = terrain[i][j].animals.iterator();
                while(it.hasNext())
                {
                    Animal animal = it.next();
                    if(animal.getLife() == 0.0)
                    {
                        it.remove();
                    }
                }
            }
    }
    
    public void dailyReset(int n)
    {//changes seasons, and call plant growth and animal raise functions
        if(n%durationOfSeason == 0)
        {
            ChangeSeason();
            plantGrow();
            printSystem();
            printAnimalStatistics();
        }
        else if("Summer".equals(season) || "Spring".equals(season))
        {
            if(n%durationOfSeason == durationOfSeason/2)
                plantGrow();
        }
        for (int i = 0; i < terrainSize; i ++)
        {
            for (int j = 0; j < terrainSize; j++)
            {
                if((n+1)%8 == 0)
                {
                    for(int k = 0; k < terrain[i][j].animals.size(); k++)
                    {
                        if("Life.Wolf".equals(terrain[i][j].animals.get(k).getClass().getName()))
                        terrain[i][j].animals.get(k).raise();
                    }
                }
                else if((n+1)%10 == 0)
                {
                    for(int k = 0; k < terrain[i][j].animals.size(); k++)
                    {
                        if("Life.Groundhog".equals(terrain[i][j].animals.get(k).getClass().getName()))
                        terrain[i][j].animals.get(k).raise();
                    }
                }
                else if((n+1)%15 == 0)
                {
                    for(int k = 0; k < terrain[i][j].animals.size(); k++)
                    {
                        if("Life.Rabbit".equals(terrain[i][j].animals.get(k).getClass().getName()))
                        terrain[i][j].animals.get(k).raise();
                    }
                }
            }
        }
    }
    
    public void checkHunger()
    {//scans the grid and checks the hunger of animals if an animal is 
        //hungry for 10 days in a row it is set dead
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                Iterator<Animal> it = terrain[i][j].animals.iterator();
                while(it.hasNext())
                {
                    Animal animal = it.next();
                    if(animal.getEatenFood() < animal.getEatCount())
                        animal.addHungerCount();
                    else
                    {
                        animal.setHungerCount();
                    }
                    if(animal.getHungerCount() >= 10)
                        animal.setLife(0);
                }
            }
    }
    
    public void printAnimalStatistics()
    {//prints the number of wolfs, rabbits and groundhogs
        String s;
        int w=0,g=0,r=0;
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                Iterator<Animal> it = terrain[i][j].animals.iterator();
                while(it.hasNext())
                {
                    Animal animal = it.next();
                    s = animal.getClass().getName();
                    if(null != s)
                        switch (s)
                        {
                        case "Life.Groundhog":
                            g++;
                            break;
                        case "Life.Rabbit":
                            r++;
                            break;
                        case "Life.Wolf":
                            w++;
                            break;
                    }
                }
            }
        System.out.println("There are:\n"+r+" rabbits\n"+g+" groundhogs\n"+w+" wolfs\n");
    }
    
    public void generateOffspring()
    {// scan the grid
        for (int i = 0; i < terrainSize; i ++)
            for (int j = 0; j < terrainSize; j++)
            {
                for(int k=0; k<terrain[i][j].animals.size(); k++)
                {//search list untill you find fertile female animal
                    if(terrain[i][j].animals.get(k).fertile(getSeason(season)) && terrain[i][j].animals.get(k).getGender() == 'f')
                    {
                        String name = terrain[i][j].animals.get(k).getClass().getName();
                        for(int n=0; n < terrain[i][j].animals.size(); n++)
                        {//search list untill you find fertile male animal
                            if(name.equals(terrain[i][j].animals.get(n).getClass().getName()) && terrain[i][j].animals.get(n).fertile(getSeason(season)) && terrain[i][j].animals.get(n).getGender() == 'm')
                            {
                                char gender;
                                Animal a;
                                if(rand.nextInt(2)==0)//choose gender randomly
                                    gender = 'm';
                                else
                                    gender = 'f';
                                switch(name)
                                {// create aproprite offspring
                                    case "Life.Groundhog":
                                        a = new Groundhog(i, j, 'H', gender, false);
                                        terrain[i][j].addAnimal(a);
                                        break;
                                    case "Life.Rabbit":
                                        a = new Rabbit(i, j, 'H', gender, false);
                                        terrain[i][j].addAnimal(a);
                                        break;
                                    case "Life.Wolf":
                                        a = new Wolf(i, j, 'C', gender, false);
                                        terrain[i][j].addAnimal(a);
                                        break;
                                }
                            }    
                        }    
                    }
                }
            }
    }
}