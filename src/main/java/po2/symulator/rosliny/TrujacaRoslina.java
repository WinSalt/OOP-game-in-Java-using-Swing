package po2.symulator.rosliny;


import po2.symulator.Roslina;
import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;


public abstract class TrujacaRoslina extends Roslina {
    public TrujacaRoslina(int sila, char znak){
        super(sila, znak);
    }

    public TrujacaRoslina(Swiat swiat, COORDINATES pos, int wiek, int sila, char znak){
        super(swiat,pos,wiek,sila,znak);
    };

    protected void ZabijAtakujacego(Zwierze atakujacy, DIRECTION dir){
        System.out.println(atakujacy.GetZnak() + " wygral, ale ten orgnaizm jest roslina trujaca i zabija atakujacego");
        swiat.SetPole(pozycja, new Trawa(swiat, pozycja, 0));
    }
}
