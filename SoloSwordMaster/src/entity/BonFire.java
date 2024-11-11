package entity;

import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BonFire extends Entity {
    
    private BufferedImage[] bonfireImages;
    private int spriteCounter = 0;
    private int spriteNum = 0;

    public BonFire(GamePanel gp) {
        super(gp);
        name = "BonFire";
        
        // Load the sprite sheet images
        bonfireImages = new BufferedImage[2];
        bonfireImages[0] = setup("/objects/tile470", gp.tileSize, gp.tileSize);
        bonfireImages[1] = setup("/objects/tile471", gp.tileSize, gp.tileSize);
        
        collision = true;

        // Set the collision area (optional, adjust as needed)
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void update() {
        spriteCounter++;
        if (spriteCounter > 10) { // Change the number to control the animation speed
            spriteNum = (spriteNum + 1) % bonfireImages.length;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
		// STOP MOVING CAMERA
		  if(gp.player.worldX < gp.player.screenX) {
		   screenX = worldX;
		  }
		  if(gp.player.worldY < gp.player.screenY) {
		   screenY = worldY;
		  }   
		  int rightOffset = gp.screenWidth - gp.player.screenX;      
		  if(rightOffset > gp.worldWidth - gp.player.worldX) {
		   screenX = gp.screenWidth - (gp.worldWidth - worldX);
		  } 
		  int bottomOffset = gp.screenHeight - gp.player.screenY;
		  if(bottomOffset > gp.worldHeight - gp.player.worldY) {
		   screenY = gp.screenHeight - (gp.worldHeight - worldY);
		  }

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(bonfireImages[spriteNum], screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
