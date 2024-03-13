package po2.symulator.rosliny;

import po2.symulator.Roslina;
import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;

import static po2.symulator.defines.GATUNKI.TRAWA;

public class Trawa extends Roslina {

    public Trawa(){
        super(0, TRAWA);
    };
    public Trawa(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,0,TRAWA);
    };


}
