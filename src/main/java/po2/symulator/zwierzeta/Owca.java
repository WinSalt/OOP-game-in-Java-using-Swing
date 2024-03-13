package po2.symulator.zwierzeta;


import po2.symulator.Zwierze;
import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;

import static po2.symulator.defines.GATUNKI.*;

public class Owca extends Zwierze {


    public Owca(){
        super(4,4,OWCA,1);
    };
    public Owca(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,4,4,OWCA,1);
    };
}
