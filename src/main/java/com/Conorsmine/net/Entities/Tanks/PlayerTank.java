package com.Conorsmine.net.Entities.Tanks;

import com.Conorsmine.net.Entities.GameObjects;

import java.awt.*;
import java.time.Instant;

public class PlayerTank extends GameObjects {

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
