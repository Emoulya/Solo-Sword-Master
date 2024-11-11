package entity;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_Dog extends Entity {
	
	public NPC_Dog(GamePanel gp) {
		super(gp);

		type = 1;
		direction = "down";
		speed = 1;

		getImage();
		getImage();
		setDialogue();
		setNpcFaceImage();
	}
	
	public void getImage() {

		up1 = setup("/npc/dog_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/dog_up2", gp.tileSize, gp.tileSize);
		up3 = setup("/npc/dog_up3", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/dog_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/dog_down2", gp.tileSize, gp.tileSize);
		down3 = setup("/npc/dog_down3", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/dog_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/dog_left2", gp.tileSize, gp.tileSize);
		left3 = setup("/npc/dog_left3", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/dog_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/dog_right2", gp.tileSize, gp.tileSize);
		right3 = setup("/npc/dog_right3", gp.tileSize, gp.tileSize);

	}
	
	public void setDialogue() {
		
	    dialogues[0][0] = "Haii!, Kamu Sepertinya baru \ndisini??";
	    dialogues[0][1] = "Semoga hari mu indah selalu";
	}
	
    public void setNpcFaceImage() {
        try {
			npcFaceImage = ImageIO.read(getClass().getResourceAsStream("/npcFace/faceDog.png"));
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
