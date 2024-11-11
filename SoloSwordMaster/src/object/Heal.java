package object;

import entity.Entity;
import main.GamePanel;

public class Heal extends Entity {

	public Heal(GamePanel gp) {
		super(gp);
		name = "Heal";
		down1 = setup("/objects/heal", gp.tileSize, gp.tileSize);
		collision = true;
	}

}
