package po2.symulator.zwierzeta;

import po2.symulator.Zwierze;
import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;
import po2.symulator.defines.DIRECTION;

import java.util.Random;

import static po2.symulator.defines.GATUNKI.*;
public class Zolw extends Zwierze {

    public Zolw(){
        super(2,1,ZOLW,1);
    };
    public Zolw(Swiat swiat, COORDINATES pos, int wiek){
        super(swiat,pos,wiek,2,1,ZOLW,1);
    };

    @Override
    public void Akcja(){

        int min = 0;
        int max = 3;
        Random rand = new Random();
        int SzansaNaRuch = rand.nextInt((max - min) + 1) + min;

        if (SzansaNaRuch == 0) {
            DIRECTION dir = ZrobRuch();
            CzyOdbilAtak(dir);
        }

    }
}
