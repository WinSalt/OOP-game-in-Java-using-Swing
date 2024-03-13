package po2.symulator.zwierzeta;

import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;

import java.util.Random;

import static po2.symulator.defines.DIRECTION.*;

import static po2.symulator.defines.GATUNKI.*;

public class Cyberowca extends Zwierze {

    public Cyberowca(){
        super(11,4,CYBEROWCA,1);
    };
    public Cyberowca(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek, 11,4,CYBEROWCA,1);
    };

    @Override
    public DIRECTION ZrobRuch(){
            if(swiat.czyJestBarszcz())
                return ruchCyerowcy();
            else
                return normalnyRuch();
    }

    private DIRECTION normalnyRuch(){
        final int iloscProbLosowychRuchow = 10;
        DIRECTION dir = NO_CHANGE;
        int proby = 0;
        while (dir == NO_CHANGE) {
            int min = 0;
            int max = 3;
            Random rand = new Random();
            int random = rand.nextInt((max - min) + 1) + min;

            if (proby < iloscProbLosowychRuchow) proby++;
            else break;

            switch (random) {
                case 0: {
                    if (pozycja.y - step >= 0) {
                        System.out.println("Up");
                        this.pozycja.y -= step;
                        dir = UP;
                    }
                }break;
                case 1: {
                    if (pozycja.y + step < swiat.GetWysokosc()) {
                        System.out.println("Down");
                        this.pozycja.y += step;
                        dir = DOWN;
                    }
                }break;
                case 2: {
                    if (pozycja.x - step >= 0) {
                        System.out.println("Left");
                        this.pozycja.x -= step;
                        dir = LEFT;
                    }
                }break;
                case 3: {
                    if (pozycja.x + step < swiat.GetSzerokosc()) {
                        System.out.println("Right");
                        this.pozycja.x += step;
                        dir = RIGHT;
                    }
                }break;
            }
        }

        if (dir != NO_CHANGE)
            System.out.println( znak + ": " + pozycja.x + " " + pozycja.y);
        else
            System.out.println("Pozycja zwierzecia nie zmienila sie");

        return dir;
    }

    private DIRECTION ruchCyerowcy(){
        int Y = 1;
        int X = 1;
        boolean znalazlaBarszcz = false;
        COORDINATES coor = new COORDINATES(X,Y);
        while (!znalazlaBarszcz) {

            if (X > swiat.GetSzerokosc() && Y > swiat.GetWysokosc())
                break;

            for (int i = 0; i <= 2 * Y; i++) {
                for (int j = 0; j <= 2 * X; j++) {
                    int Xpos = pozycja.x - X + j;
                    int Ypos = pozycja.y - Y + i;

                    if (Xpos < 0) Xpos = 0;
                    else if (Xpos >= swiat.GetSzerokosc()) Xpos = swiat.GetSzerokosc()-1;

                    if (Ypos < 0) Ypos = 0;
                    else if (Ypos >= swiat.GetWysokosc()) Ypos = swiat.GetWysokosc()-1;

                    coor.x = Xpos;
                    coor.y = Ypos;

                    if (!(coor.x == pozycja.x && coor.y == pozycja.y) && swiat.GetPole(coor).GetZnak() == BARSZCZ) {
                        System.out.println( " X= " + Xpos + " Y= " + Ypos + " znak: " + swiat.GetPole(coor).GetZnak() );
                        znalazlaBarszcz = true;
                        break;
                    }
                }

                if (znalazlaBarszcz)
                    break;
            }
            X++;
            Y++;
        }

        DIRECTION dir = NO_CHANGE;
        if (coor.x != pozycja.x) {
            if (coor.x < pozycja.x) {
                pozycja.x--;
                dir = LEFT;
            }
            else {
                pozycja.x++;
                dir = RIGHT;
            }
        }
        else {
            if (coor.y < pozycja.y) {
                pozycja.y--;
                dir = UP;
            }
            else {
                pozycja.y++;
                dir = DOWN;
            }
        }

        if (dir != NO_CHANGE)
            System.out.println("nowa pozycja zwierzecia " + znak + ": " + pozycja.x + " " + pozycja.y );
        else
            System.out.println("pozycja zwierzecia nie zmienila sie");

        return dir;
    }


}
