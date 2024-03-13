package po2.symulator.rosliny;

import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;

import static po2.symulator.defines.GATUNKI.*;

public class WilczeJagody extends TrujacaRoslina{

    public WilczeJagody(){
        super(99,WILCZEJAGODY);
    };
    public WilczeJagody(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,99,WILCZEJAGODY);
    };

    @Override
    public void Kolizja(Zwierze atakujacy, DIRECTION dir) {
        if (sila != atakujacy.GetSila()) //sily sa rozne --> wygyrwa silniejszy
        {
            if (sila > atakujacy.GetSila()) //wygrywa obronca
            {
                ObroncaWygral(atakujacy, dir);
            }
            else //wygrywa atakujacy
            {
                ZabijAtakujacego(atakujacy, dir);
            }
        }

        else //jesli sily sa rowne --> wygrywa atakujacy
        {
            ZabijAtakujacego(atakujacy, dir);
        }

    }

}
