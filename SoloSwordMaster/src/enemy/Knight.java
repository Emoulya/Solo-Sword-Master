package enemy;

import java.util.Random;
import entity.Entity;
import main.GamePanel;

public class Knight extends Entity {

    GamePanel gp;
    private boolean chasingPlayer = false;
    private int chaseDuration = 400; // durasi pengejaran dalam frame
    private int chaseCounter = 0;

    public Knight(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 3;
        name = "knight";
        speed = 2;
        maxLife = 4;
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
        up1 = setup("/enemy/up1", gp.tileSize, gp.tileSize);
        up2 = setup("/enemy/up2", gp.tileSize, gp.tileSize);
        up3 = setup("/enemy/up1", gp.tileSize, gp.tileSize);
        down1 = setup("/enemy/down1", gp.tileSize, gp.tileSize);
        down2 = setup("/enemy/down2", gp.tileSize, gp.tileSize);
        down3 = setup("/enemy/down1", gp.tileSize, gp.tileSize);
        left1 = setup("/enemy/left1", gp.tileSize, gp.tileSize);
        left2 = setup("/enemy/left2", gp.tileSize, gp.tileSize);
        left3 = setup("/enemy/left1", gp.tileSize, gp.tileSize);
        right1 = setup("/enemy/right1", gp.tileSize, gp.tileSize);
        right2 = setup("/enemy/right2", gp.tileSize, gp.tileSize);
        right3 = setup("/enemy/right1", gp.tileSize, gp.tileSize);
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
}
