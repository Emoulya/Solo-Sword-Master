package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.Entity;
import object.Heart;
import object.Key;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	BufferedImage heart_full, heart_half, heart_blank, logo;
	Font arcade, pixel;
	BufferedImage keyImage;
	BufferedImage bgMenuImage;
	public boolean gameFinished = false;
	public int commandNum = 0;
	public String currentDialogue = "";
	public Entity npc;
	BufferedImage npcFaceImage;
	int subState = 0;

	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/joystix monospace.otf");
			arcade = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/PixelBookOut-Regular.ttf");
			pixel = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Key key = new Key(gp);
        keyImage = key.down1;

		try {
			bgMenuImage = ImageIO.read(getClass().getResourceAsStream("/background/bg_menu1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //new object
        Entity Heart = new Heart(gp);
        heart_full = Heart.image1;
        heart_half = Heart.image2;
        heart_blank = Heart.image3;
        logo = Heart.image4;
	}

	public void draw(Graphics2D g2) {
	    g2.setFont(arcade);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
	    g2.setColor(Color.white);
	    
	    if (gp.gameState != gp.dialogueState && gp.gameState != gp.pauseState && gp.gameState != gp.OptionsState) {
	        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
	        g2.drawString("X" + gp.player.hasKey, 75, 60);
	        
	        // Draw logo below keyImage
	        int logoX = gp.tileSize / 2; // Adjust X position as needed
	        int logoY = gp.tileSize / 2 + gp.tileSize + 10; // Place logo below keyImage
	        g2.drawImage(logo, logoX, logoY, null);
	    }
	    
	    this.g2 = g2;
	    
	    g2.setColor(Color.white);

	    // Title State
	    if (gp.gameState == gp.titleState) {
	        drawTitleScreen();
	    }
	    // Play State
	    if (gp.gameState == gp.playState) {
	        drawPlayerLife();
	    }
	    // Pause State
	    if(gp.gameState == gp.pauseState) {
	        drawPauseScreen();
	    }
	    // Dialogue State
	    if (gp.gameState == gp.dialogueState) {
	        drawDialogueScreen();
	    }
	    // Credits State
	    if (gp.gameState == gp.creditsState) {
	        drawCreditsScreen();
	    }
	    // Game Over State
	    if (gp.gameState == gp.gameOverState) {
	        drawGameOverScreen();
	    }
	    // Options State
	    if (gp.gameState == gp.OptionsState) {
	        drawOptionScreen();
	    }
	}

	
	public void drawPlayerLife() {
	    if (!gameFinished) { // Hanya gambar kehidupan pemain jika game belum selesai
	        int x = gp.tileSize / 2 + gp.tileSize;
	        int y = gp.tileSize / 2 + gp.tileSize + 10;
	        int i = 0;

	        //max life
	        while (i < gp.player.maxLife / 2) {
	            g2.drawImage(heart_blank, x, y, null);
	            i++;
	            x += gp.tileSize;
	        }

	        //reset
	        x = gp.tileSize / 2 + gp.tileSize;
	        y = gp.tileSize / 2 + gp.tileSize + 10;
	        i = 0;

	        //current life
	        while (i < gp.player.life) {
	            g2.drawImage(heart_half, x, y, null);
	            i++;
	            if (i < gp.player.life) {
	                g2.drawImage(heart_full, x, y, null);
	            }
	            i++;
	            x += gp.tileSize;
	        }
	    }
	}


	public void drawTitleScreen() {
	    // Background Image
	    g2.drawImage(bgMenuImage, 0, 0, gp.screenWidth, gp.screenHeight, null);

	    // Title Name
	    g2.setFont(arcade);
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
	    String text = "SOLO SWORD MASTER";
	    int x = gp.screenWidth - getXforRightAlignedText(text) - 10;
	    int y = gp.tileSize * 3;

	    // Shadow
	    g2.setColor(Color.BLACK);
	    g2.drawString(text, x + 8, y + 5);

	    // Main Color
	    g2.setColor(Color.red);
	    g2.drawString(text, x, y);

	    // MC Boy Image
	    x = gp.tileSize;
	    y = gp.tileSize * 6;
	    g2.drawImage(gp.player.down1, x, y, gp.tileSize * 9, gp.tileSize * 9, null);

	    // Menu
	    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

	    text = "START GAME";
	    x = gp.screenWidth - getXforRightAlignedText(text) - 10;
	    y = gp.tileSize * 6 + gp.tileSize * 2;
	    g2.drawString(text, x, y);
	    if (commandNum == 0) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    text = "CREDITS";
	    x = gp.screenWidth - getXforRightAlignedText(text) - 10;
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (commandNum == 1) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }

	    text = "EXIT";
	    x = gp.screenWidth - getXforRightAlignedText(text) - 10;
	    y += gp.tileSize;
	    g2.drawString(text, x, y);
	    if (commandNum == 2) {
	        g2.drawString(">", x - gp.tileSize, y);
	    }
	}

	public void drawPauseScreen() {
	    String text = "PAUSED";
	    // Atur font ke arcade dan ukuran font ke 72f (atau ukuran lain yang sesuai)
	    g2.setFont(arcade.deriveFont(72f));
	    // Atur warna font ke warna yang kontras (misalnya, putih)
	    g2.setColor(Color.WHITE);
	    // Hitung posisi x untuk menempatkan teks di tengah layar
	    int x = getXforCenteredText(text);
	    // Hitung posisi y untuk menempatkan teks di tengah layar
	    int y = gp.screenHeight / 2;

	    // Gambar teks pada posisi yang dihitung
	    g2.drawString(text, x, y);
	}

	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	
		int x;
		int y;
		String text;
		g2.setFont(arcade);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
	
		text = "Game Over";
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}
		
		// Kembali ke title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}

	public void drawDialogueScreen() {
	    // WINDOW
	    int x = gp.tileSize;
	    int y = gp.tileSize * 8;
	    int width = gp.screenWidth - (gp.tileSize * 2);
	    int height = gp.tileSize * 4;
	    drawSubWindow(x, y, width, height);

	    // Set font
	    g2.setFont(arcade);
	    g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));

	    // Draw NPC face image if it is not null
	    if (npc != null && npc.npcFaceImage != null) {
	        int faceX = x + gp.tileSize / 4;
	        int faceY = y - gp.tileSize * 1 + 30;
	        g2.drawImage(npc.npcFaceImage, faceX, faceY, 160, 180, null);
	    } else {
	    }

	    // Adjust text position to account for NPC face image
	    x += gp.tileSize + 100;
	    y += gp.tileSize;
	    
	    if (npc != null && npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
	        currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
	        
	        if (gp.keyH.enterPressed == true) {
	            npc.dialogueIndex++;
	            gp.keyH.enterPressed = false;
	        }
	    } else {
	        if (npc != null) {
	            npc.dialogueIndex = 0;
	        }
	        
	        if (gp.gameState == gp.dialogueState) {
	            gp.gameState = gp.playState;
	        }
	    }

	    for (String line : currentDialogue.split("\n")) {
	        g2.drawString(line, x, y);
	        y += 40;
	    }
	}

	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}

	public void drawCreditsScreen() {
		// Background Color
		g2.drawImage(bgMenuImage, 0, 0, gp.screenWidth, gp.screenHeight, null);

		// Credits Title
		g2.setFont(arcade);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
		String text = "Credits";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		
		// Shadow
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 8, y + 5);

		g2.setColor(Color.red);
		g2.drawString(text, x, y);

		// Credits Content
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
		String[] credits = { "2250081036-Imtiyaz Mulia Sabda", "2250081023-El Ridho Pertama Kinsa",
				"2250081014-Muhamad Agil Fadhilah" };
		y += gp.tileSize * 2;
		for (String credit : credits) {
			x = getXforCenteredText(credit);
			g2.drawString(credit, x, y);
			y += gp.tileSize;
		}

		// Back Option
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		text = "Back";
		x = getXforCenteredText(text);
		y += gp.tileSize * 2;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
	
	public int getXforRightAlignedText(String text) {
	    int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
	    return length;
	}
	
	public void drawOptionScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(24F));
		
		// SUB WINDOW
		int frameWidth = gp.tileSize * 8;
	    int frameHeight = gp.tileSize * 8;
	    int frameX = (gp.screenWidth / 2) - (frameWidth / 2);
	    int frameY = (gp.screenHeight / 2) - (frameHeight / 2);
	    
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch (subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_control(frameX, frameY); break;
		case 2: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false; 
	}
	
	public void options_top(int frameX, int frameY) {
	    
	    int textX;
	    int textY;
	    
	    // TITLE
	    String text = "Options";
	    textX = getXforCenteredText(text);
	    textY = frameY + gp.tileSize;
	    g2.drawString(text, textX, textY);
	    
	     // MUSIC
	    textX = frameX + gp.tileSize;
	    textY += gp.tileSize;
	    g2.drawString("Music", textX, textY);
	    if(commandNum == 1) {
	        g2.drawString(">", textX-25, textY);
	    }

	    // CONTROL
	    textY += gp.tileSize;
	    g2.drawString("Control", textX, textY);
	    if(commandNum == 2) {
	        g2.drawString(">", textX-25, textY);
	        if(gp.keyH.enterPressed == true) {
	            subState = 1; // Ubah ke 1
	            commandNum = 0;
	        }
	    }
	     
	     
	    // END GAME
	    textY += gp.tileSize;
	    g2.drawString("End Game", textX, textY);
	    if(commandNum == 3) {
	        g2.drawString(">", textX-25, textY);
	        if(gp.keyH.enterPressed == true) {
	            subState = 2; // Ubah ke 2
	            commandNum = 0;
	        }
	    }
	     
	    // BACK
	    textY += gp.tileSize*3;
	    g2.drawString("Back", textX, textY);
	    if(commandNum == 4) {
	        g2.drawString(">", textX-25, textY);
	        if(gp.keyH.enterPressed == true) {
	            gp.gameState = gp.playState;
	            commandNum = 0;
	        }
	    }
	     
	     // MUSIC VOLUME
	     textX = frameX + (int)(gp.tileSize*4.5);
	     textY = frameY + gp.tileSize*2 - 18;
	     g2.drawRect(textX, textY, 120, 24);
	     int volumeWidth = 24 * gp.music.volumeScale;
	     g2.fillRect(textX, textY, volumeWidth, 24);

	}

	public void options_control(int frameX, int frameY) {
	    int textX;
	    int textY;

	    // TITLE
	    String text = "Control";
	    textX = getXforCenteredText(text);
	    textY = frameY + gp.tileSize;
	    g2.drawString(text, textX, textY);
	    g2.setFont(g2.getFont().deriveFont(18F));

	    textX = frameX + gp.tileSize;
	    textY += gp.tileSize;

	    g2.drawString("Move", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("Confirm/Attack", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("Pause", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("Options", textX, textY);
	    textY += gp.tileSize;

	    textX = frameX + gp.tileSize * 6;
	    textY = frameY + gp.tileSize * 2;

	    g2.drawString("WASD", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("ENTER", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("P", textX, textY);
	    textY += gp.tileSize;
	    g2.drawString("ESC", textX, textY);
	    textY += gp.tileSize;

	    // BACK
	    textX = frameX + gp.tileSize;
	    textY = frameY + gp.tileSize * 7;

	    g2.drawString("Back", textX, textY);

	    if (commandNum == 0) {
	        g2.drawString(">", textX - 25, textY);
	        if (gp.keyH.enterPressed == true) {
	            subState = 0;
	            commandNum = 2;
	        }
	    }
	}

	
	public void options_endGameConfirmation(int frameX, int frameY) {
	    int textX = frameX + gp.tileSize;
	    int textY = frameY + gp.tileSize;
	    g2.setFont(g2.getFont().deriveFont(20F));

	    currentDialogue = " Anda yakin akan \nkeluar dari game?";

	    for (String line : currentDialogue.split("\n")) {
	        g2.drawString(line, textX, textY);
	        textY += 40;
	    }

	    // YES
	    String text = "Yes";
	    textX = getXforCenteredText(text);
	    textY += gp.tileSize*2;
	    g2.drawString(text, textX, textY);

	    if (commandNum == 0) {
	        g2.drawString(">", textX - 25, textY);
	        if (gp.keyH.enterPressed == true) {
	        	gp.stopMusic();
	        	gp.restart();
	            subState = 0;
	            gp.gameState = gp.titleState;
	        }
	    }
	    // NO
	    text = "No";
	    textX = getXforCenteredText(text);
	    textY += gp.tileSize;
	    g2.drawString(text, textX, textY);

	    if (commandNum == 1) {
	        g2.drawString(">", textX - 25, textY);
	        if (gp.keyH.enterPressed == true) {
	            subState = 0;
	            commandNum = 3;
	        }
	    }
	}
}
