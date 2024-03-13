package src.main.java.po2.symulator;

import src.main.java.po2.symulator.defines.COORDINATES;
import src.main.java.po2.symulator.rosliny.*;
import src.main.java.po2.symulator.zwierzeta.*;

import src.main.java.io.*;
import java.util.*;

import static src.main.java.po2.symulator.defines.GATUNKI.*;

public class Swiat {

    private int wysokosc;
    private int szerokosc;
    private int tura = 0;
    private int iloscOrgNaPoczatku = 20;
    private Organizm[][] plansza;

    private String ZapiszPoczatkowySwiat(){
        StringBuilder data= new StringBuilder();
        data.append("\nOTO SWIAT:\n  ");
        for (int i = 0; i < szerokosc; i++)
            data.append(i).append(" ");
        data.append('\n');

        for (int i = 0; i < wysokosc; i++) {
            data.append(i).append(" ");
            for (int j = 0; j < szerokosc; j++)
                data.append(plansza[j][i].GetZnak()).append(" ");
            data.append('\n');
        }

        if (GetHuman() != null) {
            Czlowiek H = (Czlowiek)GetHuman();
            data.append("Czlowiek: ").append(H.GetPozycja().x).append(" ").append(H.GetPozycja().y).append(" sila: ").append(H.GetSila()).append('\n');
            if (H.GetUmiejetnoscAktywnaPrzez() > 0) { data.append("Specjalna umiejetnosc aktywna przez: ").append(H.GetUmiejetnoscAktywnaPrzez()).append('\n'); }
            if (H.GetUmiejetnoscOdnawianaPrzez() > 0) { data.append("Specjalna umiejetnosc odnawiana przez: ").append(H.GetUmiejetnoscOdnawianaPrzez()).append('\n'); }
        }
        else
            data.append("Czlowiek nie istnieje\n");

        return data.toString();
    }
    private Organizm StworzOrganizmTegoGatunku(char gatunek, COORDINATES coor, int wiek){
        Organizm Org = null;

        switch (gatunek) {
            case ANTYLOPA: { Org = new Antylopa(this, coor, wiek); }break;
            case LIS: { Org = new Lis(this, coor, wiek); }break;
            case OWCA: { Org = new Owca(this, coor, wiek); }break;
            case WILK: {  Org = new Wilk(this, coor, wiek); }break;
            case ZOLW: { Org = new Zolw(this, coor, wiek); }break;
            case BARSZCZ: { Org = new BarszczSosnowskiego(this, coor, wiek); }break;
            case GUARANA: { Org = new Guarana(this, coor, wiek); }break;
            case MLECZ: { Org = new Mlecz(this, coor, wiek); }break;
            case TRAWA: { Org = new Trawa(this, coor, wiek); }break;
            case WILCZEJAGODY: { Org = new WilczeJagody(this, coor, wiek); }break;
            case CZLOWIEK: { Org = new Czlowiek(this, coor, wiek); }break;
            case CYBEROWCA: { Org = new Cyberowca(this, coor, wiek); }break;
        }
        return Org;
    }
    private void WczytajOrganizmyZPliku(Scanner myReader){
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.charAt(0) != '/') //komentarz
            {
                String[] strArr = data.split("\\s+");

                char gatunek = data.charAt(0);

                int x = Integer.parseInt(strArr[1]);
                int y = Integer.parseInt(strArr[2]);
                int wiek = Integer.parseInt(strArr[3]);

                if (x >= szerokosc || y >= wysokosc) {
                    System.out.println("niepoprawna pozycja ");
                    if (x >= szerokosc) System.out.println("x ");
                    if (y >= wysokosc)  System.out.println("y ");
                    System.out.println("ktoregos orgaznimu!");
                    System.exit(1);
                }

                COORDINATES coor = new COORDINATES(x,y);

                Organizm Org = StworzOrganizmTegoGatunku(gatunek, coor, wiek);
                System.out.println("wczytano org: " + gatunek + " " + coor.x +" "+ coor.y + " " + wiek );
                if (Org == null) {
                    System.out.println("niepoprawna nazwa organizmu!");
                    System.exit(1);
                }
                else {

                    if (plansza[x][y].GetZnak() != TRAWA)
                        System.out.println("Pole " + x + " " + y + " jest juz zajete przez " + plansza[x][y].GetZnak());
                    else {

                        if (GetHuman() != null && Org.GetZnak() == CZLOWIEK)
                            System.out.println( "Na swiecie moze istniec tylko jeden czlowiek!");
                        else {
                            System.out.println("Nowy organizm: " + gatunek + "  pozycja: " + x + " " + y + "  wiek: " + wiek);
                            SetPole(coor, Org);
                        }
                    }
                }
            }
        }
    }
    private void WczytajInfoOSwiecieZPliku(Scanner myReader){
        String data = myReader.nextLine();

        String[] strArr = data.split("\\s+");

        szerokosc = Integer.parseInt(strArr[0]);
        wysokosc = Integer.parseInt(strArr[1]);
        tura =  Integer.parseInt(strArr[2]);
        if(tura <= 0)
            tura = 1;
        iloscOrgNaPoczatku = Integer.parseInt(strArr[3]);

        System.out.println("wczytano swiat: " + szerokosc + " " + wysokosc + " " + tura + " " + iloscOrgNaPoczatku);
    }
    private void UtworzPlanszeZPliku(){
        System.out.println("Swiat: szer= " + szerokosc + " " + " wys= " + wysokosc + " tura= " + tura);

        plansza = new Organizm [szerokosc][wysokosc];

        //cala plansza to trawa, potem sie wstawia zwierzeta
        for (int j = 0; j < szerokosc; j++) {
            for (int i = 0; i < wysokosc; i++) {
                COORDINATES coor = new COORDINATES( j,i );
                SetPole(coor, new Trawa(this, coor, 0));
            }
        }
    }


    private boolean SprawdzCzySaAktywneOrganizmy(){
        for (int i = 0; i < wysokosc; i++)
            for (int j = 0; j < szerokosc; j++)
                if (!plansza[j][i].GetWykonalRuch())
        return true;

        return false;
    }

    private LinkedList<Organizm> wezWszystkieOrganizmy(){
        LinkedList<Organizm> res = new LinkedList<>();
        for (int i = 0; i < szerokosc; i++) {
            for (int j = 0; j < wysokosc; j++) {
                COORDINATES coor = new COORDINATES(i,j);
                    res.add(GetPole(coor));
            }
        }
        return res;
    }
    private LinkedList<Organizm> wezWszystkieOrganizmyTylkoAktywne(){
        LinkedList<Organizm> res = new LinkedList<>();
        for (int i = 0; i < szerokosc; i++) {
            for (int j = 0; j < wysokosc; j++) {
                COORDINATES coor = new COORDINATES(i,j);

                if(!GetPole(coor).GetWykonalRuch())
                    res.add(GetPole(coor));
            }
        }
        return res;
    }
    private void PrzygotujKolejnaRunde(){

        LinkedList<Organizm> w = wezWszystkieOrganizmy();
        for (Organizm org : w) {
            org.SetWykonalRuch(false);
        }

        System.out.println("---------koniec tury----------");

        Czlowiek Human = (Czlowiek)GetHuman();

        if (Human == null) {
            System.out.println( "GAME OVER");
        }
        else {
            if (Human.GetUmiejetnoscAktywnaPrzez() > 0) System.out.println("aktywnosc aktywna przez:" + Human.GetUmiejetnoscAktywnaPrzez() );
            if (Human.GetUmiejetnoscOdnawianaPrzez() > 0)  System.out.println("aktywnosc odnawiana przez:" + Human.GetUmiejetnoscOdnawianaPrzez() );
        }
    }

    private void aktualizujSwiatWOrganizmach(Organizm o){
        LinkedList<Organizm> w = wezWszystkieOrganizmy();
        for (Organizm o_ptr : w)
            o_ptr.SetSwiat(o.GetSwiat());
    }

    private void pokazCoord(){
        LinkedList<Organizm> w = wezWszystkieOrganizmy();
        for (Organizm org : w){
            if(org.GetZnak() != TRAWA)
                System.out.println(org.GetZnak() + " na poz= " + org.GetPozycja().x + " " + org.GetPozycja().y );
        }
    }


    private static void sortujOrganizmy(List<Organizm> orgaznizmy) {
        Comparator<Organizm> comparator = Comparator.comparing(Organizm::GetInicjatywa).thenComparingInt(Organizm::GetWiek);
        orgaznizmy.sort(comparator.reversed());
    }

    public void ZapiszSwiatDoPliku(String nazwaPliku){
        nazwaPliku += ".txt";
        try {
            FileWriter myObj = new FileWriter(nazwaPliku);
            myObj.write(szerokosc + " " + wysokosc + " "+ tura + " " + iloscOrgNaPoczatku + '\n');
            LinkedList<Organizm> w =  wezWszystkieOrganizmy();

            for (Organizm org : w) {
                if (org.GetZnak() != TRAWA) {
                    String save = org.GetZnak() +  " " +  org.GetPozycja().x +  " " +  org.GetPozycja().y +  " " +  org.GetWiek() + '\n';

                    myObj.write(save);
                    System.out.print("zapisano " + save );
                }
            }

            myObj.close();
            System.out.println("Pomyslnie zapisano gre w pliku o nazwie " + nazwaPliku);
        } catch (IOException e) {
            System.out.println("An error during creating a file " + nazwaPliku + " occurred.");
            e.printStackTrace();
            System.exit(1);
        }
    }


    public Organizm GetHuman(){
        LinkedList<Organizm> w = wezWszystkieOrganizmy();

        for (Organizm o_ptr : w) {
            if (o_ptr.GetZnak() == CZLOWIEK)
                return o_ptr;
        }
        return null;
    }
    public boolean czyJestBarszcz(){
        LinkedList<Organizm> w = wezWszystkieOrganizmy();

        for (Organizm org : w) {
            if(org instanceof BarszczSosnowskiego)
                return true;
        }

        return false;
    }

    public void WykonajTure(){
        String StarySwiat = ZapiszPoczatkowySwiat();

        tura++;
        System.out.println("Wykonywanie " + tura + " tury:");

        LinkedList<Organizm> w = wezWszystkieOrganizmy();
        sortujOrganizmy(w);
        for (Organizm o_ptr : w)
            o_ptr.SetWykonalRuch(false);


        while(true){
            Organizm org = w.get(0);
            if (!org.GetWykonalRuch()) {

                COORDINATES coor = org.GetPozycja();
                if(org.GetZnak() != TRAWA) // dal trawy nie pokazuj
                    System.out.println(org + " wykonuje akcje:");

                org.Akcja();

                org.SetWiek(org.GetWiek() + 1);
                org.SetWykonalRuch(true);


                if(org.GetZnak() != TRAWA)
                    RysujSwiat();
            }

            aktualizujSwiatWOrganizmach(org);

            if (SprawdzCzySaAktywneOrganizmy()) {
                w = wezWszystkieOrganizmyTylkoAktywne(); // aktualizacja vektora
                sortujOrganizmy(w);
            }
            else
                break;
        }

        System.out.println("\n\n\n\tSTARY SWIAT:" + '\n' + StarySwiat);

        System.out.println("\n\tNOWY SWIAT:");
        RysujSwiat();

        PrzygotujKolejnaRunde();

    }

    public void RysujSwiat(){
        System.out.println("\nOTO SWIAT:");
        System.out.print("  ");
        for (int i = 0; i < szerokosc; i++)
            System.out.print( i + " ");
        System.out.println("");

        for (int i = 0; i < wysokosc; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < szerokosc; j++)
                System.out.print( plansza[j][i].GetZnak() + " ");
            System.out.println("");
        }

        if (GetHuman() != null) {
            Czlowiek H = (Czlowiek)GetHuman();
            System.out.println("Czlowiek: " + H.GetPozycja().x + " " + H.GetPozycja().y + " sila: " + H.GetSila() );
            if (H.GetUmiejetnoscAktywnaPrzez() > 0) { System.out.println( "Specjalna umiejetnosc aktywna przez: " + H.GetUmiejetnoscAktywnaPrzez() ); }
            if (H.GetUmiejetnoscOdnawianaPrzez() > 0) { System.out.println( "Specjalna umiejetnosc odnawiana przez: " + H.GetUmiejetnoscOdnawianaPrzez()); }

        }
        else
            System.out.println("Czlowiek nie istnieje");
    }

    public Swiat(int width, int height, int round, int proc){
        this.szerokosc = width;
        this.wysokosc = height;
        int rozmiar = width * height;
        this.tura=round;
        this.iloscOrgNaPoczatku = proc;
        this.plansza = new Organizm[szerokosc][wysokosc];
        System.out.println("utworzono swiat o rozmiarach: szer = " + szerokosc + " wys= " + wysokosc);

        ArrayList<Organizm> Y = new ArrayList<>();
        COORDINATES coor1 = new COORDINATES(0,0);
        Y.add(new Czlowiek(this,coor1,0)); // tylko jeden czlowiek

        int iloscOrg = rozmiar * iloscOrgNaPoczatku / 100;
        System.out.println("Swiat bedzie mial " + iloscOrg + " organizmow, co stanowi " + iloscOrgNaPoczatku + "% ilosci pol.");
        int iterator = 1;
        while(iloscOrg > iterator){
            int i = iterator % ILOSC_GATUNKOW_BEZ_CZLOWIEKA;
            Organizm Org = new Wilk(); //po porstu jakies zwierze, zeby nie wyskakiwal blad
            switch (i) {
                case 0: { Org = new Wilk(); }break;
                case 1: { Org = new Antylopa(); }break;
                case 2: { Org = new Lis(); }break;
                case 3: { Org = new Owca(); }break;
                case 4: { Org = new Zolw(); }break;
                case 5: { Org = new WilczeJagody(); }break;
                case 6: { Org = new Cyberowca(); }break;
                case 7: { Org = new Guarana(); }break;
                case 8: { Org = new Mlecz(); }break;
                case 9: { Org = new Trawa(); }break;
                case 10: { Org = new BarszczSosnowskiego(); }break;
            }
            Y.add(Org);
            iterator++;
        }

        while (Y.size() < rozmiar)
            Y.add(new Trawa());

        Collections.shuffle(Y);

        for (int x = 0; x < szerokosc; x++) {
            for (int y = 0; y < wysokosc; y++) {
                int id = x * wysokosc + y;
                COORDINATES coor = new COORDINATES(x,y);

                char gatunek = Y.get(id).GetZnak();
                int wiek = 0;
                Organizm Org = StworzOrganizmTegoGatunku(gatunek, coor, wiek);
                SetPole(coor, Org);
            }
        }

        RysujSwiat();
    }

    public Swiat(){
        System.out.println("Witaj nowy swiecie!");
        this.szerokosc=0;
        this.wysokosc=0;
        this.tura=0;
    }

    public void WczytajSwiatZPliku(String nazwaPliku){
        try {
            File myObj = new File(nazwaPliku+".txt");
            Scanner myReader = new Scanner(myObj);

            WczytajInfoOSwiecieZPliku(myReader);
            if (wysokosc > 0 && szerokosc > 0 && tura > 0) {
                UtworzPlanszeZPliku();
                WczytajOrganizmyZPliku(myReader);
            }
            System.out.println("POMYSLNIE WCZYTANO SWIAT:");
            RysujSwiat();

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error during opening file " + nazwaPliku +" occurred.");
            e.printStackTrace();
        }
    }

    public int GetWysokosc(){ return wysokosc; }
    public int GetSzerokosc(){
        return szerokosc;
    }
    public int GetTura(){
        return tura;
    }

    public void SetPole(COORDINATES coor, Organizm org){
        plansza[coor.x][coor.y] = org;
    }
    public Organizm GetPole(COORDINATES coor){
        return plansza[coor.x][coor.y];
    }

}
