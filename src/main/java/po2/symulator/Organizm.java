package po2.symulator;


import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;
import po2.symulator.rosliny.Trawa;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected int  wiek;
    protected char znak;
    protected int step;
    protected boolean wykonalRuch = true;
    protected COORDINATES pozycja;
    protected Swiat swiat;


    protected void NormalnaKolizja(Zwierze atakujacy, DIRECTION dir){
        if (sila != atakujacy.GetSila()) //sily sa rozne --> wygyrwa silniejszy
        {
            if (sila > atakujacy.GetSila())
                ObroncaWygral(atakujacy,dir);
            else
                AtakujacyWygral(atakujacy, dir);
        }
        else //sily sa rowne --> wygrywa atakujacy
            AtakujacyWygral(atakujacy, dir);
    }

    protected void AtakujacyWygral(Zwierze atakujacy, DIRECTION dir){
        System.out.println(atakujacy.GetZnak() + " ATT wygral z " + this.GetZnak() /*+ " na polu " + pozycja.x + " " + pozycja.y*/);

        //ustawianie trawy na poprzednim polu atakujacego
        COORDINATES starecoor = ObliczStareCoor(atakujacy, dir);
        swiat.SetPole(starecoor, new Trawa(swiat, starecoor, 0));

        //aktualne pole zajmowane przez atakujacego
        swiat.SetPole(pozycja, atakujacy);
        atakujacy.SetPozycja(pozycja);

    }

    protected void ObroncaWygral(Zwierze atakujacy, DIRECTION dir){

        System.out.println(this.GetZnak() + " DEF Wygral na poz " + pozycja.x + " " + pozycja.y);

        COORDINATES stareCoorAtak = ObliczStareCoor(atakujacy, dir);
        atakujacy = null;
        System.out.println("trawa na " + stareCoorAtak.x + " " + stareCoorAtak.y);
        swiat.SetPole(stareCoorAtak,new Trawa(swiat,stareCoorAtak,0)); // na polu z ktorego ruszyl sie atakujacy ustawiana jest trawa
    }

    protected COORDINATES ObliczStareCoor(Zwierze atakujacy, DIRECTION dir) {
        COORDINATES coor = atakujacy.GetPozycja();
        int atakStep = atakujacy.GetStep();

        switch (dir) {
            case UP: {
                coor.y += atakStep;
            }break;
            case DOWN: {
                coor.y -= atakStep;
            }break;
            case LEFT: {
                coor.x += atakStep;
            }break;
            case RIGHT: {
                coor.x -= atakStep;
            }break;
        }
        return coor;
    }

    /* In Java, all non-static methods are by default "virtual functions." Only methods marked with the keyword final, which cannot be overridden, along with private methods, which are not inherited, are non-virtual.*/

    public Organizm( int sila, int inicjatywa, char znak, int step){
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.znak = znak;
        this.step = step;
    };
    public Organizm(Swiat swiat, COORDINATES pos, int wiek, int sila, int inicjatywa, char znak, int step){
        this.wiek = wiek;
        this.swiat = swiat;
        this.pozycja = pos;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.znak = znak;
        this.step = step;
    };

    public abstract void Akcja();
    public void Kolizja(Zwierze atakujacy, DIRECTION dir) {
        NormalnaKolizja(atakujacy, dir);
    }


    public char GetZnak() {
        return znak;
    }
    public int GetSila() {
        return sila;
    }
    public void SetSila(int sila) {
        this.sila = sila;
    }

    public int GetInicjatywa() {
        return inicjatywa;
    }

    public COORDINATES GetPozycja() {
        return pozycja;
    }
    public void SetPozycja(COORDINATES pos) {
        this.pozycja = pos;
    }

    public int GetWiek() {
        return wiek;
    }
    public void SetWiek(int value){
            this.wiek = value;
    }
    public Swiat GetSwiat() {
        return swiat;
    }

    public void SetWykonalRuch(boolean value){
        this.wykonalRuch = value;
    }
    boolean GetWykonalRuch(){
        return wykonalRuch;
    }

    public void SetSwiat(Swiat s){
        this.swiat=s;
    }

    @Override
    public String toString() {
        return znak + " inicj= " + inicjatywa + " wiek " + wiek +  " sila " + sila +" na poz= " + pozycja.x + " " + pozycja.y;
    }



}
