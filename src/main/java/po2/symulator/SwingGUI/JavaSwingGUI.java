package po2.symulator.SwingGUI;

import po2.symulator.Swiat;
import po2.symulator.defines.COORDINATES;
import po2.symulator.zwierzeta.*;
import po2.symulator.rosliny.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;


import static po2.symulator.defines.GATUNKI.*;


public class JavaSwingGUI implements KeyListener {
    JButton[][] plansza;
    JLabel kierunekRuchu;
    JFrame Frame;

    int szer=0, wys=0;
    Swiat swiat = new Swiat();
    boolean hex;

    public static void main(String[] args) {
        new JavaSwingGUI();
    }

    public JavaSwingGUI(){

        setFrame(400,420);

        int Xpos=100;
        int Ypos=75;
        int W = 200;
        int H = 50;
        JButton load = new JButton("Wczytaj z pliku");
        load.setBounds(Xpos,Ypos,W,H);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.dispose();
                WczytajGreZPliku();
            }
        });

        JButton createNewGame = new JButton("Stworz nowa gre");
        createNewGame.setBounds(Xpos,2*Ypos,W,H);
        createNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.dispose();
                WpiszInfoOSwiecie();
            }
        });

        addExitButton(Xpos,3*Ypos,W,H);

        Frame.add(load);
        Frame.add(createNewGame);
        Frame.repaint();
    }

    private void setFrame(int width, int height){
        Frame = new JFrame("Projekt PO nr 2, Autor: Krzysztof Nazar 184698");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setVisible(true);
        Frame.setFocusable(true);
        Frame.setSize(width, height);
        Frame.setLayout(null);
    }

    private void addExitButton(int x, int y, int w, int h){
        JButton exit = new JButton("exit");

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        exit.setBounds(x,y,w,h);
        Frame.add(exit);
    }

    private void WpiszInfoOSwiecie(){
        setFrame(300,400);

        ArrayList<JLabel> labels = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();

        labels.add(new JLabel("Szer"));
        labels.add(new JLabel("Wys"));
        labels.add(new JLabel("procent zaludnienia"));
        labels.add(new JLabel("nor->normlane, hex->szesciany"));


        for(JLabel lab : labels){
            int index = labels.indexOf(lab);
            lab.setBounds(50,(1+index)*50-20,200,20);
            textFields.add(new JTextField());
            textFields.get(index).setBounds(50,(1+index)*50,150,20);
            Frame.add(lab);
            Frame.add(textFields.get(index));
        }

        JButton newWorld = new JButton("save");

        newWorld.setBounds(25,250,100,50);
        addExitButton(150,250,100,50);
        newWorld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewWorld(textFields);
            }
        });

        Frame.add(newWorld);
        Frame.repaint();
    }

    private void error(String err){
        showErrorLabel(err);
        throw new IllegalArgumentException(err);
    }

    private void createNewWorld(ArrayList<JTextField> arr){
        JTextField tf1 = arr.get(0);
        JTextField tf2 = arr.get(1);
        JTextField tf3 = arr.get(2);
        JTextField tf4 = arr.get(3);
        int proc=0;
        if(!tf1.getText().equals("") && !tf2.getText().equals("") && !tf3.getText().equals("") && !tf4.getText().equals("")) {
            if ((tf4.getText().equals("nor") || tf4.getText().equals("hex"))) {
                try {
                    szer = Integer.parseInt(tf1.getText());
                    wys = Integer.parseInt(tf2.getText());
                    proc = Integer.parseInt(tf3.getText());
                    hex = tf4.getText().equals("hex");
                } catch (Exception e) {
                    showErrorLabel("Niepoprawne wartosci - wpisz tylko liczby!");
                    throw new IllegalArgumentException("Niepoprawne wartosci - wpisz tylko liczby!");
                }

                if (szer > 0 && wys > 0 && proc > 0 && proc <= 100) {
                    Frame.dispose();
                    swiat = new Swiat(szer, wys, 1, proc);
                    pokazPlansze();
                } else
                    error("Niepoprawne wartosci!");
            }
            else
                error("Wpisz nor lub hex!");
        }
        else
            error("Uzupelnij wszystkie pola!");
    }

    private void WczytajGreZPliku(){

        setFrame(300,400);

        ArrayList<JLabel> labels = new ArrayList<>();
        ArrayList<JTextField> textFields = new ArrayList<>();

        labels.add(new JLabel("Nazwa pliku (bez .txt)"));
        labels.add(new JLabel("nor->normlane, hex->szesciany"));

        for(JLabel lab : labels){
            int index = labels.indexOf(lab);
            lab.setBounds(50,(1+index)*50-20,200,20);
            textFields.add(new JTextField());
            textFields.get(index).setBounds(50,(1+index)*50,150,20);
            Frame.add(lab);
            Frame.add(textFields.get(index));
        }

        JButton loadFromFileButton = new JButton("stworz gre");
        loadFromFileButton.setBounds(25,250,100,50);
        addExitButton(150,250,100,50);

        loadFromFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf1 = textFields.get(0);
                JTextField tf2 = textFields.get(1);
                if(!tf1.getText().equals("") && !tf2.getText().equals("")){
                    if ((tf2.getText().equals("nor") || tf2.getText().equals("hex"))) {
                        swiat = new Swiat();
                        swiat.WczytajSwiatZPliku(tf1.getText());
                        szer = swiat.GetSzerokosc();
                        wys = swiat.GetWysokosc();
                        hex = tf2.getText().equals("hex");

                        Frame.dispose();
                        pokazPlansze();
                        Czlowiek Human = (Czlowiek) swiat.GetHuman();
                        if (Human != null)
                            Human.SetKierunekRuchuCzlowieka(4); //ustawanie na "no_change"
                    }
                    else
                        error("Wpisz nor lub hex!");
                }
                else
                    error("Uzupelnij wszystkie pola");
            }
        });

        Frame.add(loadFromFileButton);
        Frame.repaint();
    }

    private void showErrorLabel(String text){
        JLabel l5 = new JLabel();
        l5.setBounds(0,0,150,20);
        l5.setText(text);
        Frame.add(l5);
        Frame.repaint();
    }

    private void ZapiszGreDoPliku(){
        setFrame(300,400);

        JLabel l1 = new JLabel("Nazwa pliku (bez .txt)");
        l1.setBounds(50,30,150,20);
        JTextField tf1=new JTextField();
        tf1.setBounds(50,50,150,20);

        JButton saveToTxt = new JButton("zapisz");

        saveToTxt.setBounds(25,250,100,50);
        addExitButton(150,250,100,50);

        saveToTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!tf1.getText().equals("")){
                    swiat.ZapiszSwiatDoPliku(tf1.getText());
                    System.exit(1);
                }
                else{
                    error("Uzupelnij pole!");
                }
            }
        });

        Frame.add(l1);
        Frame.add(tf1);
        Frame.add(saveToTxt);
        Frame.repaint();
    }

    private void pokazPlansze(){
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setFocusable(true);

        if(hex)
            planszaHex();
        else
            planszaNormalna();
    }

    private void planszaHex(){
        plansza = new HexagonButton[szer][wys];

        int offsetY = 40;
        int offsetX = 0;

        for(int col = 0; col < szer; col++) {
            for(int row = 0; row < wys; row++){
                    COORDINATES coor = new COORDINATES(col,row);
                    char c = swiat.GetPole(coor).GetZnak();

                    plansza[col][row] = new HexagonButton(Character.toString(c));

                    plansza[col][row].setBounds(offsetX, offsetY, 105, 95);

                    Frame.add(plansza[col][row]);

                    if(c == TRAWA)
                        addMenuToButton(plansza[col][row], col, row);

                    offsetY += 87;
                }
            if(col%2 == 0)
                offsetY = 0;
                else
                offsetY = 40;

            offsetX += 76;
        }

        int buttW = plansza[0][0].getWidth();
        int buttH = plansza[0][0].getHeight();
        showButtonsOnBoard(90, buttW);
        adjustFrameSize(buttW, buttH);
    }

    private void showButtonsOnBoard(int distance, int buttW){
        Czlowiek Human = (Czlowiek) swiat.GetHuman();
        if (Human == null) {
            kierunekRuchu = new JLabel("Game over");
            kierunekRuchu.setBounds(szer*distance,60,300,50);
            Frame.add(kierunekRuchu);
            Frame.repaint();
        }
        else{
            addPlanszaButtons(distance);
            Frame.setFocusable(true);
            Frame.addKeyListener(this);
        }
    }

    private void planszaNormalna(){

        plansza = new NormalButtons[szer][wys];

        for(int col=0;col<szer;col++)
            for(int row=0;row<wys;row++){

                char gatunek = swiat.GetPole(new COORDINATES(col, row)).GetZnak();

                plansza[col][row] = new NormalButtons(col,row, gatunek);

                if(gatunek == TRAWA)
                    addMenuToButton(plansza[col][row], col,row);

                Frame.add(plansza[col][row]);
            }

        int buttW = plansza[0][0].getWidth();
        int buttH = plansza[0][0].getHeight();

        showButtonsOnBoard(60, buttW);

        adjustFrameSize(buttW, buttH);
    }

    private void adjustFrameSize(int buttW, int buttH){
        int x=0,y=0;
        if(wys < 5)
            x = 600;
        else
            x = (1+szer)*buttW + 200;
        if(szer < 5)
            y = 600;
        else
            y = (1+wys)*buttH + 200;

        Frame.setSize(x,y);

        Frame.setLayout(null);
        Frame.setVisible(true);
    }

    private void addMenuToButton(JButton b, int x, int y){

        JPopupMenu popupmenu = new JPopupMenu();
        LinkedList<JMenuItem> BoardButtonsMenu = new LinkedList<>();

        BoardButtonsMenu.add(new JMenuItem("antylopa"));
        BoardButtonsMenu.add(new JMenuItem("cyberowca"));
        BoardButtonsMenu.add(new JMenuItem("lis"));
        BoardButtonsMenu.add(new JMenuItem("owca"));
        BoardButtonsMenu.add(new JMenuItem("wilk"));
        BoardButtonsMenu.add(new JMenuItem("zolw"));
        BoardButtonsMenu.add(new JMenuItem("barszcz"));
        BoardButtonsMenu.add(new JMenuItem("guarana"));
        BoardButtonsMenu.add(new JMenuItem("mlecz"));
        BoardButtonsMenu.add(new JMenuItem("wilczejagody"));

        for (JMenuItem menuButton : BoardButtonsMenu ) {
            popupmenu.add(menuButton);
            menuButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HandleNewAnimal(b, menuButton, x, y);
                }
            });
        }

        b.addKeyListener(this);
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupmenu.show(Frame, b.getX(), b.getY());
            }
        });

        Frame.add(popupmenu);
        Frame.add(b);
    }

    private void HandleNewAnimal(JButton butt,JMenuItem item, int x, int y){
        COORDINATES coor = new COORDINATES(x,y );

        System.out.println("x: " + coor.x  + " y: " + coor.y);

        switch(item.getText()) {
            case "antylopa":
                swiat.SetPole(coor, new Antylopa(swiat, coor, 0));
                break;
            case "cyberowca":
                swiat.SetPole(coor, new Cyberowca(swiat, coor, 0));
                break;
            case "lis":
                swiat.SetPole(coor, new Lis(swiat, coor, 0));
                break;
            case "owca":
                    swiat.SetPole(coor, new Owca(swiat, coor, 0));
                break;
            case "wilk":
                    swiat.SetPole(coor, new Wilk(swiat, coor, 0));
                break;
            case "zolw":
                    swiat.SetPole(coor, new Zolw(swiat, coor, 0));
                break;
            case "barszcz":
                    swiat.SetPole(coor, new BarszczSosnowskiego(swiat, coor, 0));
                break;
            case "guarana":
                    swiat.SetPole(coor, new Guarana(swiat, coor, 0));
                break;
            case "mlecz":
                    swiat.SetPole(coor, new Mlecz(swiat, coor, 0));
                break;
            case"wilczejagody":
                    swiat.SetPole(coor, new WilczeJagody(swiat, coor, 0));
                break;
        }
        butt.setText(Character.toString(swiat.GetPole(coor).GetZnak()));

        Frame.repaint();
    }

    private void addPlanszaButtons(int buttWidth){

        addExitButton(szer*buttWidth,0,150,50);

        JButton saveThisGameToFile = new JButton("zapisz swiat");
        saveThisGameToFile.setBounds(szer*buttWidth,70,150,50);
        saveThisGameToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.dispose();
                ZapiszGreDoPliku();
            }
        });
        Frame.add(saveThisGameToFile);

        JButton nextRound = new JButton("Kolejna runda");
        nextRound.setBounds(szer*buttWidth,140,150,50);
        nextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.dispose();
                swiat.WykonajTure();
                pokazPlansze();

                Czlowiek Human = (Czlowiek) swiat.GetHuman();
                if (Human != null)
                    Human.SetKierunekRuchuCzlowieka(4); //ustawanie na "no_change"
            }
        });

        Frame.add(nextRound);

        Czlowiek Human = (Czlowiek) swiat.GetHuman();
        if (Human != null) {

            Human.SetKierunekRuchuCzlowieka(4);
            int akt = Human.GetUmiejetnoscAktywnaPrzez();
            int odn = Human.GetUmiejetnoscOdnawianaPrzez();
            if(akt == 0 && odn == 0) {
                JButton activateAbility = new JButton("Ability");
                activateAbility.setBounds(szer * buttWidth, 210, 150, 50);
                activateAbility.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Czlowiek Human = (Czlowiek)swiat.GetHuman();
                        if (Human != null) {
                            Human.SetKierunekRuchuCzlowieka(4); //ustawanie na "no_change"
                            Human.SetUmiejetnoscAktywnaPrzez(Human.getUmiejetnoscAktywnaTury());
                            //playing = true;
                            Frame.dispose();
                            swiat.WykonajTure();
                            pokazPlansze();
                            Human.SetKierunekRuchuCzlowieka(4); //ustawanie na "no_change"
                        }
                    }
                });
                Frame.add(activateAbility);
            }
            else{
                JLabel inf = new JLabel();
                if(akt > 0 && odn == 0)
                    inf = new JLabel("umiej akt przez: " + akt);
                else if( akt == 0 && odn > 0)
                    inf = new JLabel("umiej odn przez: " + odn);

                inf.setBounds(szer*buttWidth,210,150,50);
                Frame.add(inf);
            }

        }

        JLabel ltrua = new JLabel("tura: " + swiat.GetTura());
        ltrua.setBounds(szer*buttWidth,270,300,50);
        Frame.add(ltrua);

        kierunekRuchu = new JLabel("no chage");
        kierunekRuchu.setBounds(szer*buttWidth,300,300,50);
        Frame.add(kierunekRuchu);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        Czlowiek Human = (Czlowiek) swiat.GetHuman();

        if (Human != null) {
            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_UP:
                    Human.SetKierunekRuchuCzlowieka(0);
                    kierunekRuchu.setText("Up");
                    Frame.repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    Human.SetKierunekRuchuCzlowieka(1);
                    kierunekRuchu.setText("Down");
                    Frame.repaint();
                    break;
                case KeyEvent.VK_LEFT:
                    Human.SetKierunekRuchuCzlowieka(2);
                    kierunekRuchu.setText("Left");
                    Frame.repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    Human.SetKierunekRuchuCzlowieka(3);
                    kierunekRuchu.setText("Right");
                    Frame.repaint();
                    break;
            }
        } else {
            kierunekRuchu.setText("GAME OVER");
            Frame.repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}

