package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import enemy.Jenderal;
import enemy.Knight;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    private Entity lastMonsterThatHitPlayer;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 14;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAtkImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 45;
        speed = 3;
        direction = "down";
        hasKey = 0;

        //player status
        maxLife = 8;
        life = maxLife;
    }

    public void getPlayerImage() {

        up1 = setup("/player/up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/up_2", gp.tileSize, gp.tileSize);
        up3 = setup("/player/up_3", gp.tileSize, gp.tileSize);
        down1 = setup("/player/down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/down_2", gp.tileSize, gp.tileSize);
        down3 = setup("/player/down_3", gp.tileSize, gp.tileSize);
        left1 = setup("/player/left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/left_2", gp.tileSize, gp.tileSize);
        left3 = setup("/player/left_3", gp.tileSize, gp.tileSize);
        right1 = setup("/player/right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/right_2", gp.tileSize, gp.tileSize);
        right3 = setup("/player/right_3", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAtkImage() {

        atkup1 = setup("/player/attatas1", gp.tileSize, gp.tileSize * 2);
        atkup2 = setup("/player/attatas2", gp.tileSize, gp.tileSize * 2);
        atkup3 = setup("/player/attatas2", gp.tileSize, gp.tileSize * 2);
        atkdown1 = setup("/player/attbawah1", gp.tileSize, gp.tileSize * 2);
        atkdown2 = setup("/player/attbawah2", gp.tileSize, gp.tileSize * 2);
        atkdown3 = setup("/player/attbawah2", gp.tileSize, gp.tileSize * 2);
        atkleft1 = setup("/player/attkiri1", gp.tileSize * 2, gp.tileSize);
        atkleft2 = setup("/player/attkiri2", gp.tileSize * 2, gp.tileSize);
        atkleft3 = setup("/player/attkiri2", gp.tileSize * 2, gp.tileSize);
        atkright1 = setup("/player/attkanan1", gp.tileSize * 2, gp.tileSize);
        atkright2 = setup("/player/attkanan2", gp.tileSize * 2, gp.tileSize);
        atkright3 = setup("/player/attkanan2", gp.tileSize * 2, gp.tileSize);
    }

    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return image;
    }

    public void setDefaultPositions() {

        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 45;
        direction = "down";
    }

    public void restoreLife() {

        life = maxLife;
        invincible = false;
    }

    public void update() {

        boolean moving = false;


        // Jangan izinkan pergerakan jika pemain sedang menyerang
        if (!attacking) {
            // Mengatur arah pemain berdasarkan input dari keyboard
            if (keyH.upPressed) {
                direction = "up";
                moving = true;
            } else if (keyH.downPressed) {
                direction = "down";
                moving = true;
            } else if (keyH.leftPressed) {
                direction = "left";
                moving = true;
            } else if (keyH.rightPressed) {
                direction = "right";
                moving = true;
            }
        }

        // Jika pemain bergerak, periksa kolisi dan gerakkan pemain
        if (moving) {
            collisionOn = false;
            gp.cChecker.chekTile(this);

            // Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // Mengatur animasi sprite pemain, animasi berjalan terus menerus
            spriteCounter++;
            if (spriteCounter > 15) {
                spriteNum++;
                if (spriteNum > 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1; // Reset ke frame awal saat berhenti
        }

        // Menjalankan animasi serangan
        if (attacking) {
            attacking();
        }

        // Menangani invincibility
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        // Jika tombol Enter ditekan, periksa interaksi dengan NPC
        if (keyH.enterPressed) {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            keyH.enterPressed = false; // Reset enterPressed setelah interaksi
        }

        // Periksa jika nyawa habis
        if (life <= 0) {
            if (lastMonsterThatHitPlayer instanceof Jenderal) {
                gp.csManager.sceneNum = 2;
                gp.csManager.scenePhase = 0; // Reset the scene phase
                gp.gameState = gp.cutsceneState; // Change the game state to cutscene
            } else {
                gp.gameState = gp.gameOverState;
            }
        }
    }

    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        } else if (spriteCounter <= 15) {
            spriteNum = 2;
        } else if (spriteCounter <= 25) {
            spriteNum = 3;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaheight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaheight;
        } else {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
          boolean JenderalMati;
          int j;
          String objectName = (this.gp.obj[i]).name;
          String str1;
          switch ((str1 = objectName).hashCode()) {
            case 75327:
              if (!str1.equals("Key"))
                break; 
              this.hasKey++;
              this.gp.obj[i] = null;
              System.out.println("Key: " + this.hasKey);
              break;
            case 2136014:
              if (!str1.equals("Door"))
                break; 
              if (this.hasKey > 0) {
                this.gp.obj[i] = null;
                this.hasKey--;
              } 
              System.out.println("Key: " + this.hasKey);
              break;
            case 2245128:
              if (!str1.equals("Heal"))
                break; 
              if (this.life < this.maxLife) {
                this.life += 4;
                if (this.life > this.maxLife)
                  this.life = this.maxLife; 
                this.gp.obj[i] = null;
              } 
              this.gp.obj[i] = null;
              break;
            case 64369569:
              if (!str1.equals("Boots"))
                break; 
              this.speed += 3;
              this.gp.obj[i] = null;
              break;
            case 65074913:
              if (!str1.equals("Chest"))
                break; 
              JenderalMati = true;
              for (j = 0; j < this.gp.monster.length; j++) {
                if (this.gp.monster[j] != null && this.gp.monster[j] instanceof Jenderal && 
                  (this.gp.monster[j]).life > 0) {
                  JenderalMati = false;
                  break;
                } 
              } 
              if (JenderalMati && this.hasKey > 0) {
                this.gp.obj[i].speak();
                this.gp.obj[i] = null;
                this.hasKey--;
                this.gp.stopMusic();
                this.gp.playMusic(1);
                this.life += 20;
                this.maxLife += 20;
                
                // set semua monster life nya jadi 1
                for (j = 0; j < this.gp.monster.length; j++) {
                  if (this.gp.monster[j] != null)
                    (this.gp.monster[j]).life = 1; 
                } 
              } else {
                System.out.println("Jenderal Masih Blm mati broo!!!");
              } 
              System.out.println("Key: " + this.hasKey);
              break;
          } 
        } 
      }

    public void interactNPC(int i) {

        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } else {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i) {

        if (i != 999) {
            if (!invincible) {
                lastMonsterThatHitPlayer = gp.monster[i];
                if (gp.monster[i] instanceof Jenderal) {
                    life -= 3; // Mengurangi darah sebanyak 3 jika yang menyentuh adalah Jenderal
                } else if (gp.monster[i] instanceof Knight) {
                	life -= 2; // Mengurangi darah sebanyak 2 jika yang menyentuh adalah Knight
                } else {
                    life -= 1; // Mengurangi darah sebanyak 1 untuk monster lainnya
                }
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {

        if (i != 999) {
            if (gp.monster[i].invincible == false) {
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;
                System.out.println("hit");

                if (gp.monster[i].life <= 0) {
                    if (gp.monster[i] instanceof Jenderal) {
                        ((Jenderal) gp.monster[i]).hitByPlayer();
                        gp.csManager.sceneNum = 2;  // Mengaktifkan scene ending jika yang terkena adalah Jenderal
                    }
                    gp.monster[i] = null;
                    System.out.println("miss");
                } else {
                    if (gp.monster[i] instanceof Jenderal) {
                        ((Jenderal) gp.monster[i]).hitByPlayer();
                    } else if (gp.monster[i] instanceof Knight) {
                        ((Knight) gp.monster[i]).hitByPlayer();
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    if (spriteNum == 3) image = up3;
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = atkup1;
                    if (spriteNum == 2) image = atkup2;
                    if (spriteNum == 3) image = atkup3;
                }
                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    if (spriteNum == 3) image = down3;
                }
                if (attacking == true) {
                    if (spriteNum == 1) image = atkdown1;
                    if (spriteNum == 2) image = atkdown2;
                    if (spriteNum == 3) image = atkdown3;
                }
                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                }
                if (attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) image = atkleft1;
                    if (spriteNum == 2) image = atkleft2;
                    if (spriteNum == 3) image = atkleft3;
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                }
                if (attacking == true) {
                    if (spriteNum == 1) image = atkright1;
                    if (spriteNum == 2) image = atkright2;
                    if (spriteNum == 3) image = atkright3;
                }
                break;
        }
        if (screenX > worldX) {
            tempScreenX = worldX;
        }
        if (screenY > worldY) {
            tempScreenY = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - worldX) {
            tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > gp.worldHeight - worldY) {
            tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // reset
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
