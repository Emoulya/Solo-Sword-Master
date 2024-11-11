package main;

import enemy.Jenderal;
import enemy.Knight;
import enemy.slime;
import entity.BonFire;
import entity.NPC_Girl;
import entity.NPC_Frog;
import entity.NPC_Man;
import entity.NPC_Dog;
import object.Heal;
import object.Boots;
import object.Chest;
import object.Door;
import object.Key;


public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new Key(gp);
		gp.obj[0].worldX = gp.tileSize * 10;
		gp.obj[0].worldY = gp.tileSize * 43;
		
		gp.obj[1] = new Door(gp);
		gp.obj[1].worldX = gp.tileSize * 4;
		gp.obj[1].worldY = gp.tileSize * 36;
		
		gp.obj[2] = new Key(gp);
		gp.obj[2].worldX = gp.tileSize * 20;
		gp.obj[2].worldY = gp.tileSize * 21;
		
		gp.obj[3] = new BonFire(gp);
		gp.obj[3].worldX = gp.tileSize * 11;
		gp.obj[3].worldY = gp.tileSize * 44;
		
		gp.obj[4] = new Chest(gp);
		gp.obj[4].worldX = gp.tileSize * 28;
		gp.obj[4].worldY = gp.tileSize * 22;
		
		gp.obj[5] = new Boots(gp);
		gp.obj[5].worldX = gp.tileSize * 6;
		gp.obj[5].worldY = gp.tileSize * 43;
		
		gp.obj[6] = new Heal(gp);
		gp.obj[6].worldX = gp.tileSize * 1;
		gp.obj[6].worldY = gp.tileSize * 33;

		gp.obj[7] = new Heal(gp);
		gp.obj[7].worldX = gp.tileSize * 15;
		gp.obj[7].worldY = gp.tileSize * 18;
		
		gp.obj[8] = new Heal(gp);
		gp.obj[8].worldX = gp.tileSize * 48;
		gp.obj[8].worldY = gp.tileSize * 44;
		
		gp.obj[9] = new Heal(gp);
		gp.obj[9].worldX = gp.tileSize * 46;
		gp.obj[9].worldY = gp.tileSize * 31;
		
		gp.obj[10] = new Door(gp);
		gp.obj[10].worldX = gp.tileSize * 11;
		gp.obj[10].worldY = gp.tileSize * 30;
		
		gp.obj[11] = new Key(gp);
		gp.obj[11].worldX = gp.tileSize * 31;
		gp.obj[11].worldY = gp.tileSize * 44;
		
		gp.obj[12] = new Heal(gp);
		gp.obj[12].worldX = gp.tileSize * 15;
		gp.obj[12].worldY = gp.tileSize * 27;
		
		gp.obj[13] = new Heal(gp);
		gp.obj[13].worldX = gp.tileSize * 28;
		gp.obj[13].worldY = gp.tileSize * 27;
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC_Girl(gp);
		gp.npc[0].worldX = gp.tileSize * 13;
		gp.npc[0].worldY = gp.tileSize * 44;
		
		gp.npc[1] = new NPC_Man(gp);
		gp.npc[1].worldX = gp.tileSize * 14;
		gp.npc[1].worldY = gp.tileSize * 42;
		
		gp.npc[2] = new NPC_Frog(gp);
		gp.npc[2].worldX = gp.tileSize * 37;
		gp.npc[2].worldY = gp.tileSize * 7;
		
		gp.npc[3] = new NPC_Dog(gp);
		gp.npc[3].worldX = gp.tileSize * 42;
		gp.npc[3].worldY = gp.tileSize * 7;
	}
	
	public void setMonster() {
		
		gp.monster[0] = new Knight(gp);
		gp.monster[0].worldX = gp.tileSize * 19;
		gp.monster[0].worldY = gp.tileSize * 20;
		
		gp.monster[1] = new Knight(gp);
		gp.monster[1].worldX = gp.tileSize * 19;
		gp.monster[1].worldY = gp.tileSize * 24;
		
		gp.monster[2] = new Jenderal(gp);
		gp.monster[2].worldX = gp.tileSize * 23;
		gp.monster[2].worldY = gp.tileSize * 21;
		
		gp.monster[3] = new slime(gp);
		gp.monster[3].worldX = gp.tileSize * 13;
		gp.monster[3].worldY = gp.tileSize * 34;
		
		gp.monster[4] = new slime(gp);
		gp.monster[4].worldX = gp.tileSize * 7;
		gp.monster[4].worldY = gp.tileSize * 35;
		
		gp.monster[5] = new slime(gp);
		gp.monster[5].worldX = gp.tileSize * 11;
		gp.monster[5].worldY = gp.tileSize * 36;
		
		gp.monster[6] = new Knight(gp);
		gp.monster[6].worldX = gp.tileSize * 24;
		gp.monster[6].worldY = gp.tileSize * 24;
		
		gp.monster[7] = new Knight(gp);
		gp.monster[7].worldX = gp.tileSize * 41;
		gp.monster[7].worldY = gp.tileSize * 36;
		
		gp.monster[8] = new Knight(gp);
		gp.monster[8].worldX = gp.tileSize * 44;
		gp.monster[8].worldY = gp.tileSize * 35;
		
		gp.monster[9] = new Knight(gp);
		gp.monster[9].worldX = gp.tileSize * 48;
		gp.monster[9].worldY = gp.tileSize * 35;
		
		gp.monster[10] = new slime(gp);
		gp.monster[10].worldX = gp.tileSize * 46;
		gp.monster[10].worldY = gp.tileSize * 38;
	
		gp.monster[11] = new slime(gp);
		gp.monster[11].worldX = gp.tileSize * 44;
		gp.monster[11].worldY = gp.tileSize * 40;
		
		gp.monster[12] = new slime(gp);
		gp.monster[12].worldX = gp.tileSize * 29;
		gp.monster[12].worldY = gp.tileSize * 43;
		
		gp.monster[13] = new slime(gp);
		gp.monster[13].worldX = gp.tileSize * 48;
		gp.monster[13].worldY = gp.tileSize * 41;
	}
}
