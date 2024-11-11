package object;

import entity.Entity;
import main.GamePanel;

public class Chest extends Entity {
	
	public Chest(GamePanel gp) {
		super(gp);
		name = "Chest";
		down1 = setup("/objects/chest (OLD)", gp.tileSize, gp.tileSize);
		collision = true;
		
		setDialogue();
	}
	
	public void setDialogue() {
		
	    dialogues[0][0] = "Selamat, kau berhasil mengambil \nkekuatanmu kembali.";
	    dialogues[0][1] = "Akhirnya, kekuatanmu yang \ndiambil telah kembali ke dalam \ngenggamanmu!";
	    dialogues[0][2] = "Sudah saatnya diimu menggila \nkembali seperti dahulu kala";
	    dialogues[0][3] = "Apa rasanya seperti ada yang \nberbeda??";
	    dialogues[0][4] = "Tentu, Sekarang kekuatanmu telah \ndilipat gandakan. \nsekarang kau tak terkalahkan";
	    
	}
}
