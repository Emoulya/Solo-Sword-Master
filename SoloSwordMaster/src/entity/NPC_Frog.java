package entity;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_Frog extends Entity {

	public NPC_Frog(GamePanel gp) {
		super(gp);

		type = 1;
		direction = "down";
		speed = 1;

		getImage();
		setDialogue();
		setNpcFaceImage();
	}

	public void getImage() {

		up1 = setup("/npc/frog_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/frog_up2", gp.tileSize, gp.tileSize);
		up3 = setup("/npc/frog_up3", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/frog_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/frog_down2", gp.tileSize, gp.tileSize);
		down3 = setup("/npc/frog_down3", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/frog_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/frog_left2", gp.tileSize, gp.tileSize);
		left3 = setup("/npc/frog_left3", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/frog_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/frog_right2", gp.tileSize, gp.tileSize);
		right3 = setup("/npc/frog_right3", gp.tileSize, gp.tileSize);

	}
	
	public void setDialogue() {
		
	    dialogues[0][0] = "Hiii, jangan mendekat !!!";
	    dialogues[0][1] = "HIIIIIII !!!!";
	}
	
    public void setNpcFaceImage() {
        try {
			npcFaceImage = ImageIO.read(getClass().getResourceAsStream("/npcFace/faceFrog.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void speak() {
		facePlayer();
		StartDialogue(this,dialogueSet);
	}
}
