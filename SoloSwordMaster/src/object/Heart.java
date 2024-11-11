package object;


import entity.Entity;
import main.GamePanel;

public class Heart extends Entity {
	
	public Heart(GamePanel gp) {

		super(gp); 
		name = "Heart";
		image1 = setup("/objects/bar_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/bar_setengah", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/bar_blank", gp.tileSize, gp.tileSize);
		image4 = setup("/objects/logo", gp.tileSize, gp.tileSize);

	}
}
