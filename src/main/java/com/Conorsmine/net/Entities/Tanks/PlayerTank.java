package com.Conorsmine.net.Entities.Tanks;

import java.awt.*;

public class PlayerTank extends Tanks {

    @Override
    public void tick() {
        this.setY(40);
        this.setX(40);
//        this.addX(2);
    }

    @Override
    public void renderTurret(Graphics2D g2d) {
//        g2d.rotate();
//        g2d.setColor(Color.pink);
//        g2d.fillRect(this.getX(), 40, 40, 40);
    }
}
