package po2.symulator.rosliny;

import po2.symulator.Roslina;
import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;

import static po2.symulator.defines.GATUNKI.MLECZ;

public class Mlecz extends Roslina {
    public Mlecz(){
        super(0, MLECZ);
    };
    public Mlecz(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,0, MLECZ);
    };

    @Override
    public void Akcja(){
        int iloscProbRozmnazania = 3;
        for (int i = 0; i < iloscProbRozmnazania; i++)
            NormalnaAkcjaRoslin();
    }
}
