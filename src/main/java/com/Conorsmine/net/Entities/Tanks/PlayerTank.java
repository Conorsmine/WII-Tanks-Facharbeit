package com.Conorsmine.net.Entities.Tanks;

import java.awt.*;

public class PlayerTank extends Tanks {

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.pink);
        g.fillRect(this.getX(), 40, 40, 40);
    }

    @Override
    public void tick() {
        this.addX(2);
    }
}
