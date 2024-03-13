package src.main.java.po2.symulator.rosliny;

import src.main.java.po2.symulator.Swiat;
import src.main.java.po2.symulator.Zwierze;
import src.main.java.po2.symulator.defines.COORDINATES;
import src.main.java.po2.symulator.defines.DIRECTION;
import src.main.java.po2.symulator.zwierzeta.Cyberowca;

import static po2.symulator.defines.GATUNKI.*;

public class BarszczSosnowskiego extends TrujacaRoslina {
    private void zabijSasiada(COORDINATES coor) {
        Swiat.SetPole(coor, new Trawa(Swiat, coor, 0));
    }

    public BarszczSosnowskiego(){
       super(10,BARSZCZ);
    };
    public BarszczSosnowskiego(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,10,BARSZCZ);
    };

    @Override
    public void Kolizja(Zwierze atakujacy, DIRECTION dir) {
        if (atakujacy instanceof Cyberowca)
            AtakujacyWygral(atakujacy, dir);
        else {
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
    @Override
    public void Akcja(){
       System.out.println("teraz barszcz niszczy sasiadow");
        //System.out.println("przed");
        //swiat.RysujSwiat();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                int X = pozycja.x - 1 + j;
                int Y = pozycja.y - 1 + i;
                COORDINATES coor = new COORDINATES(X,Y);

                if (	coor.x < swiat.GetSzerokosc() && coor.y < swiat.GetWysokosc() &&
                        coor.x >= 0 &&					  coor.y >= 0 &&
                        !(coor.x == pozycja.x &&		  coor.y == pozycja.y)
                )
                {

                    if (swiat.GetPole(coor).GetZnak() != CYBEROWCA)
                    zabijSasiada(coor);
                }
            }

       // System.out.println("po:");
       // swiat.RysujSwiat();
    }


}
