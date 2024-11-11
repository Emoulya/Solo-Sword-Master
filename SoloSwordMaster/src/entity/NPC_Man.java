package entity;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_Man extends Entity {
	
	public NPC_Man(GamePanel gp) {
		super(gp);

		type = 1;
		direction = "down";
		speed = 1;

		getImage();
		setDialogue();
		setNpcFaceImage();
	}
	
	public void getImage() {

		up1 = setup("/npc/man_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/man_up2", gp.tileSize, gp.tileSize);
		up3 = setup("/npc/man_up3", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/man_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/man_down2", gp.tileSize, gp.tileSize);
		down3 = setup("/npc/man_down3", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/man_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/man_left2", gp.tileSize, gp.tileSize);
		left3 = setup("/npc/man_left3", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/man_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/man_right2", gp.tileSize, gp.tileSize);
		right3 = setup("/npc/man_right3", gp.tileSize, gp.tileSize);

	}

	public void setDialogue() {
		
	    dialogues[0][0] = "syukurlah, akhirnya kau bangun \njuga.";
	    dialogues[0][1] = "kau sudah tidak sadarkan diri \nselama 5 hari";
	    dialogues[0][2] = "kami menemukanmu tergeletak \ndipinggir sungai, \napa yang telah terjadi?";
	    dialogues[0][3] = "kenapa kau hanya diam saja?";
	    dialogues[0][4] = "oh, ada kesatria yang menyerangmu?";
	    dialogues[0][5] = "Armor emas, kupikir hanya ada satu \norang yang memakai Armor emas \ndi kerajaan ini";
	    dialogues[0][6] = "dia adalah jenderal Samsul \npanglima terkuat kerajaan";
	    dialogues[0][7] = "tadi aku sempat melihatnya \nsaat berkeliling daerah ini";
	    dialogues[0][8] = "dia berada di balik hutan ini,\ntapi berhati hatilah dibalik hutan \nini ada sekumpulan monster slime";
	    
	}
	
    public void setNpcFaceImage() {
        try {
			npcFaceImage = ImageIO.read(getClass().getResourceAsStream("/npcFace/faceMan.png"));
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
