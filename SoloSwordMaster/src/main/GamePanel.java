package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.BonFire;
import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    // World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	Graphics2D g2;
    
    // FPS
    int FPS = 60;
    
    // TILE
    TileManager tileM = new TileManager(this);
    
    // KEYHANDLER
    public KeyHandler keyH = new KeyHandler(this);
    
    // SOUND
    Sound music = new Sound();
    
    Thread gameThread;
    
    // Collision
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    
    // UI
    public UI ui = new UI(this);
    
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    
    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int creditsState = 3;
	public int dialogueState = 4;
	public final int cutsceneState = 5;
	public int gameOverState = 6;
	public final int OptionsState = 7;
	
	public CutsceneManager csManager = new CutsceneManager(this);
    
    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setupGame() {
        
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        stopMusic();
        gameState = titleState;
    }

    public void retry() {
    	
    	player.setDefaultPositions();
    	player.restoreLife();
    	aSetter.setNPC();
//    	aSetter.setMonster();
    }
    public void restart() {
    	
    	player.setDefaultValues();
    	player.setDefaultPositions();
    	player.restoreLife();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    
    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while(gameThread != null) {
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
        }
    }

    public void update() {
    	
        if (gameState == playState) {
            player.update();
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			// Enemy
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					monster[i].update();
				}
			}
			// object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null && obj[i] instanceof BonFire) {
                    obj[i].update();
                }
            }
        }
        if(gameState == pauseState) {
        	// nothing
        }
        if(gameState == cutsceneState) {
            csManager.draw(this.g2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        g2 = (Graphics2D) g; // Initialize g2

        // title screen
        if (gameState == titleState) {
            ui.draw(g2);
        } 
        // credits screen
        else if (gameState == creditsState) {
        	ui.draw(g2);
        }
        // cutscene state
        else if (gameState == cutsceneState) {
            csManager.draw(g2);
        } 
        // other states
        else {
            // tile
            tileM.draw(g2);
            
            // add entities to the list 
            entityList.add(player);

            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    int result = Integer.compare(o1.worldY, o2.worldY);
                    return result;
                }
            });

            // draw entities
            for(int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            // empty entity list
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        g2.dispose();
    }

    
    public void playMusic(int i) {
        
        music.setFile(i);
        music.play();
        music.loop();
        
    }

    public void stopMusic() {
        
        music.stop();
    }
}
