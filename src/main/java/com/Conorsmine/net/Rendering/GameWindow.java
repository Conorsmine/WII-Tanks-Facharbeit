package com.Conorsmine.net.Rendering;

import com.Conorsmine.net.Entities.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GameWindow extends JPanel {

    private final JFrame window;
    private final GameRenderer renderer;

    public GameWindow(String title, int width, int height) {
        this.window = new JFrame(title);
        setup(width, height);

        this.renderer = new GameRenderer(this.getGraphics());
    }

    private void setup(int width, int height) {
        this.setBounds(0, 0, width, height);
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((int) d.getWidth()/2 - width/2, (int) d.getHeight()/2 - height/2);
        window.setSize(width, height);
        window.setLayout(new BorderLayout());
        window.add(this, BorderLayout.CENTER);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void drawCall(Graphics g) {
        this.paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        new HashMap<>(GameObjects.objectMap).forEach((id, obj) -> {
            obj.renderTick(g2d);
        });
        g2d.dispose();
    }

    public JFrame getWindow() {
        return window;
    }
}
