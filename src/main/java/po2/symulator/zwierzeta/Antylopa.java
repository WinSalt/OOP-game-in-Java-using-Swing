package po2.symulator.zwierzeta;

import po2.symulator.Swiat;
import po2.symulator.Zwierze;
import po2.symulator.defines.COORDINATES;

import static po2.symulator.defines.GATUNKI.ANTYLOPA;

public class Antylopa extends Zwierze {

    public Antylopa(){
        super(4,4,ANTYLOPA,2);
    };
    public Antylopa(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,4,4,ANTYLOPA,2);
    };
}
