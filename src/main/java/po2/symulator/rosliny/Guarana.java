package po2.symulator.rosliny;

import po2.symulator.Roslina;
import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;

import static po2.symulator.defines.GATUNKI.GUARANA;

public class Guarana extends Roslina {
    private final int wzmocenienieSilyAtakujacego = 3;
    public Guarana(){
        super(0,GUARANA);
    };
    public Guarana(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,0,GUARANA);
    };

    @Override
    public void Kolizja(Zwierze atakujacy, DIRECTION dir){

        if (sila != atakujacy.GetSila()) //sily sa rozne --> wygyrwa silniejszy
        {
            if (sila > atakujacy.GetSila())
            {
                ObroncaWygral(atakujacy, dir);
            }
            else
            {
                wzmocnijAakujacego(atakujacy, dir);
            }
        }

        else //jesli sily sa rowne -> wygrywa atakujacy
        {
            wzmocnijAakujacego(atakujacy, dir);
        }
    }

    private void wzmocnijAakujacego(Zwierze atakujacy, DIRECTION dir){
        AtakujacyWygral(atakujacy, dir);
        int obecnaSilaAtak = atakujacy.GetSila();
        atakujacy.SetSila(obecnaSilaAtak + wzmocenienieSilyAtakujacego);
        System.out.println(atakujacy.GetZnak() + " zostal wzmocniony przez Guarane. Nowa sila wynosi: " + atakujacy.GetSila());
    }

}
