package enemy;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class slime extends Entity{
	
	GamePanel gp;
	
	public slime(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = 2;
		name = "slime";
		speed = 1;
		maxLife = 2;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y =18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getImage();
				
	}
	
	public void getImage() {
		up1 = setup("/enemy/down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/enemy/down_2", gp.tileSize, gp.tileSize);
		up3 = setup("/enemy/down_3", gp.tileSize, gp.tileSize);
		down1 = setup("/enemy/down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/enemy/down_2", gp.tileSize, gp.tileSize);
		down3 = setup("/enemy/down_3", gp.tileSize, gp.tileSize);
		left1 = setup("/enemy/down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/enemy/down_2", gp.tileSize, gp.tileSize);
		left3 = setup("/enemy/down_3", gp.tileSize, gp.tileSize);
		right1 = setup("/enemy/down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/enemy/down_2", gp.tileSize, gp.tileSize);
		right3 = setup("/enemy/down_3", gp.tileSize, gp.tileSize);
	}
	public void setAction() {
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
