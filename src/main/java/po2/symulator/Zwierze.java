package po2.symulator;
import po2.symulator.defines.*;
import po2.symulator.zwierzeta.*;

import java.util.Random;

import static po2.symulator.defines.DIRECTION.*;
import static po2.symulator.defines.GATUNKI.*;

public class Zwierze extends Organizm {

    protected void CzyOdbilAtak(DIRECTION dir){
        final int silaGranicznaPrzyOdbiciu = 5;

        Organizm Def = swiat.GetPole(pozycja);
        Zwierze Att = this;

        if (Def.GetZnak() == Att.GetZnak()) {
            System.out.println("ten sam gatunek stoi na tym polu");
            RozmazanieZwierzat((Zwierze)Def,dir);
        }
        else {
            // jesli organizm ma sile mniejsza niz 5 --> zolw odeprze jego atak
            if (sila < silaGranicznaPrzyOdbiciu && Def.GetZnak() == ZOLW) {
                NastapiloOdbicie(dir);
            }
            else {
                System.out.println("Kolizja Att=" + Att.GetZnak() + " z Def=" + Def.GetZnak() + " ");
                Def.Kolizja(Att, dir);
            }
        }
    }
    protected void NastapiloOdbicie(DIRECTION dir){
        System.out.println("Odbicie!");
        switch (dir) { // organizm sie cofa
            case UP: {
                pozycja.y += step;
            }break;
            case DOWN: {
                pozycja.y -= step;
            }break;
            case LEFT: {
                pozycja.x += step;
            }break;
            case RIGHT: {
                pozycja.x -= step;
            }break;
        }
        System.out.println("powrot na pozycje " + pozycja.x + " " + pozycja.y);
    }

    protected void RozmazanieZwierzat(Zwierze Obronca, DIRECTION dir){
        NastapiloOdbicie(dir);
        Zwierze Att = this;

        boolean CzyUdaloSieRozmnozyc = false;

        CzyUdaloSieRozmnozyc = ZnajdzPoleDoRozmnazania(Att, Obronca);

        if(!CzyUdaloSieRozmnozyc)
            CzyUdaloSieRozmnozyc = ZnajdzPoleDoRozmnazania(Obronca, Att);

        if (!CzyUdaloSieRozmnozyc)
            System.out.println("Zwierze " + znak + " nie rozmnozylo sie");
    }

    protected boolean ZnajdzPoleDoRozmnazania(Zwierze org1, Zwierze org2) {
        boolean rozmozone = false;
        COORDINATES pozycja = org1.GetPozycja();
        COORDINATES pozycja2 = org2.GetPozycja();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                int Xpos = pozycja.x - 1 + j;
                int Ypos = pozycja.y - 1 + i;
                COORDINATES coor = new COORDINATES(Xpos, Ypos);

                if (
                        coor.x < swiat.GetSzerokosc() && coor.y < swiat.GetWysokosc() &&
                                coor.x >= 0 && coor.y >= 0 &&
                                !(coor.x == pozycja.x && coor.y == pozycja.y) &&
                                !(coor.x == pozycja2.x && coor.y == pozycja2.y)
                )
                    if(probaRozmnazania(coor)) {
                    System.out.println("koniec rozmazania ");
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean probaRozmnazania(COORDINATES coor){
        if (swiat.GetPole(coor).GetZnak() == TRAWA) {
            System.out.println(" gatunek " + znak + " rozmnozyl sie na polu " +  coor.x +  " " +  coor.y );

            switch (znak) {
                case ANTYLOPA: {
                    swiat.SetPole(coor, new Antylopa(swiat, coor, 0));
                }break;
                case LIS: {
                    swiat.SetPole(coor, new Lis(swiat, coor, 0));
                }break;
                case OWCA: {
                    swiat.SetPole(coor, new Owca(swiat, coor, 0));
                }break;
                case WILK: {
                    swiat.SetPole(coor, new Wilk(swiat, coor, 0));
                }break;
                case ZOLW: {
                    swiat.SetPole(coor, new Zolw(swiat, coor, 0));
                }break;
                case CYBEROWCA: {
                    swiat.SetPole(coor, new Cyberowca(swiat, coor, 0));
                }break;
            }
            return true;
        }
        return false;
    }
    protected DIRECTION ZrobRuch(){
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

    public int GetStep(){
        return step;
    }

    @Override
    public void Akcja(){
        DIRECTION dir = ZrobRuch();

        if (dir != NO_CHANGE)
            CzyOdbilAtak(dir);
    }

    public Zwierze(int sila, int inicjatywa, char znak, int step){
        super(sila,inicjatywa,znak,step);
    };
    public Zwierze(Swiat swiat, COORDINATES pos, int wiek, int sila, int inicjatywa, char znak, int step){
        super(swiat,pos,wiek,sila,inicjatywa,znak,step);
    };


}
