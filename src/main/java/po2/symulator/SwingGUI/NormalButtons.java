package po2.symulator.SwingGUI;

import javax.swing.*;
import java.awt.*;

public class NormalButtons extends JButton{

        private final int LENGTH = 50;
        private final int WIDTH = 50;
        private final int HorlDist = 10;
        private final int VerlDist = 10;

        public NormalButtons(int col, int row, char gatunek) {

            setPreferredSize(new Dimension(WIDTH, LENGTH));

            setText(Character.toString(gatunek));
            setBounds(col*(HorlDist + WIDTH),row*(VerlDist+LENGTH),WIDTH,LENGTH);
        }


}
