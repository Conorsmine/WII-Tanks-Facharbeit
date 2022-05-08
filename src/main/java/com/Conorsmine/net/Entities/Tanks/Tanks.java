package com.Conorsmine.net.Entities.Tanks;

import com.Conorsmine.net.Entities.GameObjects;
import com.Conorsmine.net.Game.Game;
import com.sun.javafx.geom.Vec2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public abstract class Tanks extends GameObjects {

    private final BufferedImage body;
//    private final BufferedImage turret;
    private double turretRot = 0;

    public Tanks(UUID id) {
        super(id);
        this.body = createBody();
    }

    public Tanks() {
        this(UUID.randomUUID());
    }

    public double getTurretRot() {
        return turretRot % 360;
    }

    public void setTurretRot(Vec2d dir) {
        if (dir.x == 0) {
            this.turretRot = 0;
            return;
        }
        this.turretRot = Math.toDegrees(Math.atan(dir.y / dir.x));
    }

    public void setTurretRot(double degrees) {
        this.turretRot = degrees % 360;
    }

    @Override
    public void render(Graphics2D g2d) {
        renderBody(g2d);
        renderTurret(g2d);
    }

    public void renderBody(Graphics2D g2d) {
        double rot = Math.toRadians(this.getRot());
        g2d.rotate(rot);
//        System.out.println(this.body);
        g2d.drawImage(this.body, this.getX(), this.getY(), Game.GAME.getFrame());
        g2d.rotate(-rot);
    }

    public abstract void renderTurret(Graphics2D g2d);

    private BufferedImage createBody() {
        BufferedImage body = new BufferedImage(7, 9, BufferedImage.TYPE_INT_ARGB);

        try {
            BufferedImage full = ImageIO.read(new File("src/main/resources/Tank_Player_1.png"));

            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 7; x++) {
                    body.setRGB(x, y, full.getRGB(x, y));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }
}
