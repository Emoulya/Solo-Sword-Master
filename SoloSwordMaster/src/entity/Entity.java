package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	GamePanel gp;

	public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
	public BufferedImage atkup1, atkup2, atkup3, atkdown1, atkdown2, atkdown3, atkleft1, atkleft2, atkleft3, atkright1, atkright2, atkright3;
	public BufferedImage image1, image2, image3, image4;
	public BufferedImage npcFaceImage;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public String dialogues[] []= new String[20][20];
	
	// state
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public boolean collision = false;
	public boolean invincible = false;
	boolean attacking = false;
	
	// counter
	public int spriteCounter = 0;
	public int actionLockCounter = 0;

	public int invincibleCounter = 0;
	public int dialogueSet = 0;
	public int dialogueIndex = 0;
	
	//Char Status
	public String name;
	public int maxLife;
	public int life;
	public int type;
	public int speed;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}

	public void speak() {
		StartDialogue(this,dialogueSet);

	}
	
	public void facePlayer() {

		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
		
	}
	public void StartDialogue(Entity entity, int setNum) {
		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		dialogueSet = setNum;
	}

	public void update() {
	    setAction();

	    collisionOn = false;
	    gp.cChecker.chekTile(this);
	    gp.cChecker.checkObject(this, false);
	    gp.cChecker.checkEntity(this, gp.npc);
	    gp.cChecker.checkEntity(this, gp.monster);
	    boolean contactPlayer = gp.cChecker.checkPlayers(this);

	    if(contactPlayer == true) {
	        if(this.type == 2 && gp.player.invincible == false) {
	            gp.player.life -= 1;
	            gp.player.invincible = true;
	        } else if(this.type == 3 && gp.player.invincible == false) {
	            gp.player.life -= 2;
	            gp.player.invincible = true;
	        } else if(this.type == 4 && gp.player.invincible == false) {
	            gp.player.life -= 3;
	            gp.player.invincible = true;
	        }
	    }

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

	    spriteCounter++;
	    if (spriteCounter > 15) {
	        spriteNum++;
	        if (spriteNum > 3) {
	            spriteNum = 1;
	        }
	        spriteCounter = 0;
	    }

	    // Reset invincibility
	    if (invincible) {
	        invincibleCounter++;
	        if (invincibleCounter > 60) { // You can adjust the value 60 as needed
	            invincible = false;
	            invincibleCounter = 0;
	        }
	    }
	}


	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
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

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

	        switch (direction) {
	        case "up":
	            if (spriteNum == 1) {
	                image = up1;
	            } else if (spriteNum == 2) {
	                image = up2;
	            } else {
	                image = up3;
	            }
	            break;
	        case "down":
	            if (spriteNum == 1) {
	                image = down1;
	            } else if (spriteNum == 2) {
	                image = down2;
	            } else {
	                image = down3;
	            }
	            break;
	        case "left":
	            if (spriteNum == 1) {
	                image = left1;
	            } else if (spriteNum == 2) {
	                image = left2;
	            } else {
	                image = left3;
	            }
	            break;
	        case "right":
	            if (spriteNum == 1) {
	                image = right1;
	            } else if (spriteNum == 2) {
	                image = right2;
	            } else {
	                image = right3;
	            }
	            break;
	        }
	        if(invincible == true) {
	        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
	        }
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {

			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}
}
