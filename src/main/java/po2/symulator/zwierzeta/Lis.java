package po2.symulator.zwierzeta;

import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;

import java.util.Random;

import static po2.symulator.defines.DIRECTION.*;

import static po2.symulator.defines.GATUNKI.LIS;

public class Lis extends Zwierze {

    public Lis(){
        super(3,7,LIS,1);
    };
    public Lis(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,3,7,LIS,1);
    };

    @Override
    public DIRECTION ZrobRuch(){
        DIRECTION dir = NO_CHANGE;
        int SprawdzoneMozliwosci = 0;
        boolean KoloLisaJestSilniejszyOrg = false;

        while (dir == NO_CHANGE) {
            final int iloscProbLosowychRuchow = 10;
            COORDINATES coor = pozycja;
            int min = 0;
            int max = 3;
            Random rand = new Random();
            int random = rand.nextInt((max - min) + 1) + min;
            //System.out.println("random move: " + random);

            if (SprawdzoneMozliwosci < iloscProbLosowychRuchow) SprawdzoneMozliwosci++;
            else break; //jesli nie ma gdzie sie ruszyc, lis zostaje w miejscu

            switch (random) {
                // lis nigdy nie ruszy sie na ple zajmowae prez organizm silniejszy niz on
                case 0: { //up
                    if (pozycja.y - step >= 0){
                        coor.y -= step;
                        if (swiat.GetPole(coor).GetSila() < sila) {
                            System.out.println("Up");
                            pozycja = coor;
                            dir = UP;
                        }
                    else {
                            KoloLisaJestSilniejszyOrg = true;
                            coor = pozycja;
                        }
                    }
                }break;
                case 1: { //down
                    if (pozycja.y + step < swiat.GetWysokosc()) {
                        coor.y += step;
                        if (swiat.GetPole(coor).GetSila()< sila) {
                            System.out.println("Down");
                            pozycja = coor;
                            dir = DOWN;
                        }
                    else {
                            KoloLisaJestSilniejszyOrg = true;
                            coor = pozycja;
                        }
                    }
                }break;
                case 2: { //left
                    if (pozycja.x - step >= 0) {
                        coor.x -= step;
                        if (swiat.GetPole(coor).GetSila() < sila) {
                            System.out.println("Left");
                            pozycja = coor;
                            dir = LEFT;
                        }
                    else {
                            KoloLisaJestSilniejszyOrg = true;
                            coor = pozycja;
                        }
                    }
                }break;
                case 3: { //right
                    if (pozycja.x + step < swiat.GetSzerokosc()) {
                        coor.x += step;
                        if (swiat.GetPole(coor).GetSila() < sila) {
                            System.out.println("Right");
                            pozycja = coor;
                            dir = RIGHT;
                        }
                    else {
                            KoloLisaJestSilniejszyOrg = true;
                            coor = pozycja;
                        }
                    }
                }break;
            }
        }

        if(KoloLisaJestSilniejszyOrg)
            System.out.println("kolo lisa znajduje sie silniejszy organizm!");

        if (dir != NO_CHANGE)
            System.out.println("nowa pozycja " + znak + " " + pozycja.x + " " + pozycja.y);
        else
            System.out.println("pozycja " + znak + " nie zmienila sie");

        return dir;
    }
}
