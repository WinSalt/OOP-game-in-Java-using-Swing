package po2.symulator;


import po2.symulator.rosliny.*;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.GATUNKI;

import java.util.Random;

public class Roslina extends Organizm{

    public Roslina(int sila, char znak){
        super(sila,0,znak,0);
    };
    public Roslina(Swiat swiat, COORDINATES pos, int wiek, int sila, char znak){
        super(swiat,pos,wiek,sila,0,znak,0);
    };
    protected void NormalnaAkcjaRoslin(){
        if (ObliczSzanseNaRozsianie())
            ZbadajSasiadow();
    }
    protected boolean Split(COORDINATES coor){
        if (swiat.GetPole(coor).GetZnak() == GATUNKI.TRAWA) {
            if(GetZnak() != GATUNKI.TRAWA)//dla trawy nie infromuj o rozsianiu
            System.out.println("\n gatunek " + GetZnak() + " rozsial sie na polu " + coor.x  + " " + coor.y);

            switch (znak) {
                case GATUNKI.TRAWA: {
                    swiat.SetPole(coor, new Trawa(swiat, coor, 0));
                }break;
                case GATUNKI.MLECZ: {
                    swiat.SetPole(coor, new Mlecz(swiat, coor, 0));
                }break;
                case GATUNKI.GUARANA: {
                    swiat.SetPole(coor, new Guarana(swiat, coor, 0));
                }break;
                case GATUNKI.WILCZEJAGODY: {
                    swiat.SetPole(coor, new WilczeJagody(swiat, coor, 0));
                }break;
                case GATUNKI.BARSZCZ: {
                    swiat.SetPole(coor, new BarszczSosnowskiego(swiat, coor, 0));
                }break;
            }
            return true;
        }
        return false;
    }
    protected boolean ObliczSzanseNaRozsianie(){
        int min = 0;
        int max = 100;
        Random rand = new Random();
        int szansa = rand.nextInt((max - min) + 1) + min;

        int szansaNaRozsiew = 20;
        if (szansa < szansaNaRozsiew)
            return true;
        else
            return false;
    }
    protected void ZbadajSasiadow() {
        final int zasiegRozsiewuPion = 1;
        final int zasiegRozsiewuPoziom = 1;

        boolean rozsiana = false;
        while (rozsiana == false) {
            for (int i = 0; i <= 2 * zasiegRozsiewuPion; i++) {
                for (int j = 0; j <= 2 * zasiegRozsiewuPoziom; j++) {
                    int Xpos = pozycja.x - zasiegRozsiewuPoziom + j;
                    int Ypos = pozycja.y - zasiegRozsiewuPion + i;
                    COORDINATES coor = new COORDINATES(Xpos, Ypos);

                    // cout << " X= " << Xpos << " Y= " << Ypos << endl;
                    if (
                            coor.x < swiat.GetSzerokosc() && coor.y < swiat.GetWysokosc() &&
                                    coor.x >= 0 && coor.y >= 0 &&
                                    !(coor.x == pozycja.x && coor.y == pozycja.y)
                    )
                        rozsiana = Split(coor);

                    if (rozsiana == true)
                        break;

                }

                if (rozsiana == true)
                    break;

            }
        }
    }


    @Override
    public void Akcja(){
        NormalnaAkcjaRoslin();
    }
}
