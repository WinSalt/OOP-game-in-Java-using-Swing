package po2.symulator.SwingGUI;

import javax.swing.*;
import java.awt.*;

class HexagonButton extends JButton {
    private static final long serialVersionUID = 1L;
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    public static final int HEIGHT = 95;
    public static final int WIDTH = 105;

    public HexagonButton(String name) {

        setContentAreaFilled(false);
        setFocusPainted(true);
        setBorderPainted(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        setText(name);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon hex = new Polygon();
        for (int i = 0; i < SIDES; i++) {
            hex.addPoint((int) (SIDE_LENGTH + SIDE_LENGTH * Math.cos(i * 2 * Math.PI / SIDES)), //calculation for side
                    (int) (SIDE_LENGTH + SIDE_LENGTH * Math.sin(i * 2 * Math.PI / SIDES)));   //calculation for side
        }
        g.drawPolygon(hex);
    }

    public int getWidth() {
        return WIDTH;
    }
    public int getHeight(){
        return HEIGHT;
    }
}