package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_Girl extends Entity {


	public NPC_Girl(GamePanel gp) {
		super(gp);

		type = 1;
		direction = "down";
		speed = 1;

		getImage();
		setDialogue();
		setNpcFaceImage();
	}

	public void getImage() {

		up1 = setup("/npc/girl_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/girl_up2", gp.tileSize, gp.tileSize);
		up3 = setup("/npc/girl_up3", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/girl_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/girl_down2", gp.tileSize, gp.tileSize);
		down3 = setup("/npc/girl_down3", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/girl_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/girl_left2", gp.tileSize, gp.tileSize);
		left3 = setup("/npc/girl_left3", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/girl_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/girl_right2", gp.tileSize, gp.tileSize);
		right3 = setup("/npc/girl_right3", gp.tileSize, gp.tileSize);

	}

	public void setDialogue() {
		
	    dialogues[0][0] = "setidaknya ucapkan terima kasih \nkepada kami yang sudah merawatmu!";
	    dialogues[0][1] = "hmppph..., aku tidak memerlukan \nucapan terima kasih darimu.";
	    dialogues[0][2] = "bo..bo...bodoh!!!!!!!";
	}
	
    public void setNpcFaceImage() {
        try {
			npcFaceImage = ImageIO.read(getClass().getResourceAsStream("/npcFace/faceGirl.png"));
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
