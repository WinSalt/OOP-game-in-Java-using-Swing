package po2.symulator.zwierzeta;

import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;
import po2.symulator.rosliny.Trawa;

import static po2.symulator.defines.DIRECTION.*;


import static po2.symulator.defines.GATUNKI.*;

public class Czlowiek extends Zwierze {
    private final int UmiejetnoscOdnawianaTury = 5;
    private final int UmiejetnoscAktywnaTury = 5;
    private int UmiejAktywnaPrzez;
    private int UmiejOdnawianaPrzez;
    private int KierunekRuchuCzlowieka;
    private void KolizjaZAktywnaUmiejetnoscia(Zwierze atakujacy, DIRECTION dir){
        boolean RuchMozliwy = false;
        COORDINATES starecoor = ObliczStareCoor(atakujacy, dir);
        COORDINATES noweCoorAtakujacego = new COORDINATES(pozycja.x, pozycja.y);
        int i = 0;
        while (i < 4) {
            switch (i) {
                case 0: {
                    if (pozycja.y > 0) {
                        System.out.println("up");
                        noweCoorAtakujacego.y--;
                        RuchMozliwy = true;
                    }
                }break;
                case 1: {
                    if (pozycja.y < swiat.GetWysokosc() - 1) {
                        System.out.println("down");
                        noweCoorAtakujacego.y++;
                        RuchMozliwy = true;
                    }
                }break;
                case 2: {
                    if (pozycja.x > 0) {
                        System.out.println("left");
                        noweCoorAtakujacego.x--;
                        RuchMozliwy = true;
                    }
                }break;
                case 3: {
                    if (pozycja.x < swiat.GetSzerokosc() - 1) {
                        System.out.println("right");
                        noweCoorAtakujacego.x++;
                        RuchMozliwy = true;
                    }
                }break;
            }
            if(RuchMozliwy)
                break;

            i++;
        }

        if (!RuchMozliwy) {
            System.out.println("Atakujacy nie ma sie gdzie ruszyc, " + atakujacy.GetZnak() + " ginie");
            ObroncaWygral(atakujacy, dir);
        }

        else {
            System.out.println("SPECJALNA UMIEJETNOSC ZADZIALALA HURRA!!!");
            System.out.println( atakujacy.GetZnak() + " zostal przesuniety na pole " + noweCoorAtakujacego.x + " " + noweCoorAtakujacego.y);
            atakujacy.SetPozycja(noweCoorAtakujacego);
            swiat.SetPole(noweCoorAtakujacego, atakujacy);
            //swiat.SetPole(pozycja, this);
            if (!(starecoor.x == noweCoorAtakujacego.x && starecoor.y == noweCoorAtakujacego.y)) {
                System.out.println("Atakujacy zajal nowe pole (inne od poprzedniego), wiec na starym polu atakujacego ustawiana jest trawa");
                swiat.SetPole(starecoor, new Trawa(swiat, starecoor, 0));
            }
        }
    }

    @Override
    public DIRECTION ZrobRuch(){
        System.out.println("KierunekRuchuCzlowieka w zrobruch: " + KierunekRuchuCzlowieka);
        DIRECTION dir = NO_CHANGE;
        switch (KierunekRuchuCzlowieka) {
            case 0: {
                System.out.println("Up");
                if (pozycja.y > 0) {
                    this.pozycja.y--;
                    dir = UP;
                }
            }break;
            case 1: {
                System.out.println("Down");
                if (pozycja.y < swiat.GetWysokosc() - 1) {

                    this.pozycja.y++;
                    dir = DOWN;
                }
            }break;
            case 2: {
                System.out.println("Left");
                if (pozycja.x > 0) {
                    this.pozycja.x--;
                    dir = LEFT;
                }
            }break;
            case 3: {
                System.out.println("Right");
                if (pozycja.x < swiat.GetSzerokosc() - 1) {
                    this.pozycja.x++;
                    dir = RIGHT;
                }
            }break;
            case 4: {
                System.out.println("No_change");
                if (UmiejAktywnaPrzez == 5)
                    System.out.println("W tym ruchu zostala uruchomiona Tarcza Alzura");
                else
                    System.out.println("Wcisnales zly klawisz");
            }break;
        }


        System.out.println("nowa pozycja: ");
        if (dir == NO_CHANGE)
            System.out.println("NO change");
        else {
            System.out.println(pozycja.x + " " + pozycja.y);
        }
        return dir;
    }

    public Czlowiek(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,5,4,CZLOWIEK,1);
    };

    public int GetUmiejetnoscAktywnaPrzez(){
        return UmiejAktywnaPrzez;
    }
    public void SetUmiejetnoscAktywnaPrzez(int value){
        this.UmiejAktywnaPrzez = value;
    }
    public int GetUmiejetnoscOdnawianaPrzez(){
        return UmiejOdnawianaPrzez;
    }
    public void SetKierunekRuchuCzlowieka(int dir){
        this.KierunekRuchuCzlowieka = dir;
    }


    @Override
    public void Akcja(){
        DIRECTION dir = ZrobRuch();
        if(dir != NO_CHANGE)
            CzyOdbilAtak(dir);

        if (UmiejAktywnaPrzez > 0 || UmiejOdnawianaPrzez > 0) {
            if (UmiejAktywnaPrzez > 0)
                UmiejAktywnaPrzez--;

            if (UmiejAktywnaPrzez == 0 && UmiejOdnawianaPrzez == 0)
                UmiejOdnawianaPrzez = UmiejetnoscOdnawianaTury;
            else if (UmiejOdnawianaPrzez > 0)
                UmiejOdnawianaPrzez--;
        }
    }
    @Override
    public void Kolizja(Zwierze atakujacy, DIRECTION dir){
        if (UmiejAktywnaPrzez > 0)
            KolizjaZAktywnaUmiejetnoscia(atakujacy, dir);
        else
            NormalnaKolizja(atakujacy, dir);
    }

    public int getUmiejetnoscAktywnaTury(){
        return UmiejetnoscAktywnaTury;
    }
}
