package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // title state
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        // play state
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        // pause state
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // dialog state
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        // cut scene state
        else if (gp.gameState == gp.cutsceneState) {
            cutsceneState(code);
        }
        // credits state
        else if (gp.gameState == gp.creditsState) {
            creditState(code);
        }
        // game over state
        else if(gp.gameState == gp.gameOverState) {
        	gameoverState(code);
        }
		// options state
		else if (gp.gameState == gp.OptionsState) {
			OptionsState(code);
		}
    }

    public void titleState(int code) {
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.cutsceneState;
                    gp.csManager.sceneNum = gp.csManager.opening;
                    gp.csManager.scenePhase = 0; // Inisialisasi scenePhase ke 0
                }
				if (gp.ui.commandNum == 1) {
					gp.gameState = gp.creditsState;
				}
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
    }

    public void playState(int code) {
        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.OptionsState;
			}
        }
    }
    

    public void creditState(int code) {
        if (gp.gameState == gp.creditsState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
                gp.ui.commandNum = (gp.ui.commandNum == 0) ? 1 : 0;
            }
            if (code == KeyEvent.VK_ENTER && gp.ui.commandNum == 0) {
                gp.gameState = gp.titleState;
            }
        }
    }

    public void gameoverState(int code) {
    	if(code == KeyEvent.VK_W) {
    		gp.ui.commandNum--;
    		if(gp.ui.commandNum < 0) {
    			gp.ui.commandNum = 1;
    		}
    	}
    	if(code == KeyEvent.VK_S) {
    		gp.ui.commandNum++;
    		if(gp.ui.commandNum > 1) {
    			gp.ui.commandNum = 0;
    		}
    	}
    	if(code  == KeyEvent.VK_ENTER) {
    		if(gp.ui.commandNum == 0) {
    			gp.gameState = gp.playState;
    			gp.retry();
    		}
    		else if(gp.ui.commandNum == 1) {
    			gp.gameState = gp.titleState;
    			gp.restart();
    			gp.stopMusic();
    		}
    	}
    }
    public void pauseState(int code) {
        if (gp.gameState == gp.pauseState && code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    public void cutsceneState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }
    
    public void OptionsState(int code) {

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        int maxCommandNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCommandNum = 4;
                break;
            case 1:
                maxCommandNum = 0;
                break;
            case 2:
                maxCommandNum = 1;
                break;
        }
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playMusic(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playMusic(9);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

     // volumeScale
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playMusic(9);
                }
                if (gp.ui.commandNum == 2 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.playMusic(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playMusic(9);
                }
                if (gp.ui.commandNum == 2 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.playMusic(9);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
