package po2.symulator.zwierzeta;

import po2.symulator.Zwierze;
import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;

import static po2.symulator.defines.GATUNKI.*;

public class Wilk extends Zwierze {

    public Wilk(){
        super(9,5,WILK,1);
    };
    public Wilk(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,9,5,WILK,1);
    };
}
