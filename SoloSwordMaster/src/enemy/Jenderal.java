package enemy;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;
import entity.Entity;
import main.GamePanel;

public class Jenderal extends Entity {

    GamePanel gp;
    private boolean chasingPlayer = false;
    private int chaseDuration = 600; // durasi pengejaran dalam frame
    private int chaseCounter = 0;

    public Jenderal(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 4;
        name = "Jenderal";
        speed = 3;
        maxLife = 6;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
    	
        up1 = setup("/enemy/Jup1", gp.tileSize, gp.tileSize);
        up2 = setup("/enemy/Jup2", gp.tileSize, gp.tileSize);
        up3 = setup("/enemy/Jup1", gp.tileSize, gp.tileSize);
        down1 = setup("/enemy/Jdown1", gp.tileSize, gp.tileSize);
        down2 = setup("/enemy/Jdown2", gp.tileSize, gp.tileSize);
        down3 = setup("/enemy/Jdown1", gp.tileSize, gp.tileSize);
        left1 = setup("/enemy/Jleft1", gp.tileSize, gp.tileSize);
        left2 = setup("/enemy/Jleft2", gp.tileSize, gp.tileSize);
        left3 = setup("/enemy/Jleft1", gp.tileSize, gp.tileSize);
        right1 = setup("/enemy/Jright1", gp.tileSize, gp.tileSize);
        right2 = setup("/enemy/Jright2", gp.tileSize, gp.tileSize);
        right3 = setup("/enemy/Jright1", gp.tileSize, gp.tileSize);
    }

    @Override
    public void setAction() {
        if (chasingPlayer) {
            chasePlayer();
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    private void chasePlayer() {
        int playerX = gp.player.worldX;
        int playerY = gp.player.worldY;

        if (worldX < playerX) {
            direction = "right";
        } else if (worldX > playerX) {
            direction = "left";
        }

        if (worldY < playerY) {
            direction = "down";
        } else if (worldY > playerY) {
            direction = "up";
        }

        chaseCounter++;
        if (chaseCounter >= chaseDuration) {
            chasingPlayer = false;
            chaseCounter = 0;
        }
    }

    public void hitByPlayer() {
        chasingPlayer = true;
        chaseCounter = 0;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = worldX - gp.player.worldX + gp.player.screenX;
        int tempScreenY = worldY - gp.player.worldY + gp.player.screenY;

        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
                if (spriteNum == 3) image = up3;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
                if (spriteNum == 3) image = down3;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
                if (spriteNum == 3) image = left3;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
                if (spriteNum == 3) image = right3;
                break;
        }

        // Set the scale factor
        double scaleFactor = 1.5; // Change this to make the image larger or smaller

        // Apply scaling using AffineTransform
        AffineTransform at = new AffineTransform();
        at.scale(scaleFactor, scaleFactor);

        // Calculate the new positions to center the scaled image
        int newWidth = (int) (gp.tileSize * scaleFactor);
        int newHeight = (int) (gp.tileSize * scaleFactor);
        int x = tempScreenX - (newWidth - gp.tileSize) / 2;
        int y = tempScreenY - (newHeight - gp.tileSize) / 2;

        // Apply translation to the AffineTransform
        at.translate(x / scaleFactor, y / scaleFactor);

        // Create the AffineTransformOp
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        if(invincible == true) {
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        // Draw the transformed image
        g2.drawImage(image, op, 0, 0);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}