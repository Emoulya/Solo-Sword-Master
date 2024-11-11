package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;


public class CutsceneManager {
	GamePanel gp;
  
	Graphics2D g2;
  
	public int sceneNum;
  
	public int scenePhase;
  
	Font arcade, pixel;
  
	int counter = 0;
  
	String endCredit;
  
	float alpha = 0.0F;
  
	public final int opening = 1;
  
	public CutsceneManager(GamePanel gp) {
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
    this.gp = gp;
  }
  
  public void draw(Graphics2D g2) {
    this.g2 = g2;
    switch (this.sceneNum) {
      case 1:
        scene_opening();
        break;
      case 2:
        scene_ending();
        break;
    } 
  }
  
  public void scene_opening() {
	    int pressEnterY = 520;
	    String textPhase0 = "Di sebuah kerajaan yang megah, hiduplah\n seorang bandit terkenal yang tidak \ndiketahui namanya. \n\n"
	            + " \nNamun, para penduduk sering menyebutnya\n dengan julukan Blekping karena \nbusananya yang nyentrik.";
	    String textPhase2 = "Blekping dikenal dengan keahlian bertarungnya\n dan kekejamannya dalam membunuh, \nmenjadi momok menakutkan bagi para \nkesatria kerajaan. \n\n"
	            + " Tanpa sebab yang jelas, \nia selalu mengincar para kesatria, \nmenantang mereka dalam duel hingga mati.";
	    String textPhase4 = "\n\nKetika berita tentang Blekping semakin \nmenyebar, Jenderal Samsul, seorang \nprajurit legendaris yang "
	    		+ "telah mengabdikan \nhidupnya untuk melindungi kerajaan, diutus \noleh raja untuk mengakhiri teror ini.";
	    String textPhase6 = "Pada suatu malam yang gelap, \ndi pinggiran sungai kerajaan, \nsaat Blekping sedang beristirahat, \nJenderal "
	    		+ "Samsul dan pasukannya \ndatang menghampirinya. \n\n"
	    		+ "Jenderal Samsul kemudian mengeluarkan \nsebuah kristal dan mulai mengambil \nkekuatan dari Blekping. ";
	    String textPhase8 = "\nBlekping seketika terbangun, tetapi tubuhnya \ntidak bisa digerakkan karena pengaruh \nkristal milik Jenderal Samsul. \n\n"
	    		+ "Setelah kekuatan bertarung Blekping diserap, \nkristal tersebut dimasukkan ke dalam \nsebuah peti oleh Jenderal Samsul.";
	    String textPhase10 = "\nBlekping, yang terkapar lemas dengan \nmata yang sayu, hanya bisa melihat \nJenderal Samsul dan para prajuritnya \npergi "
	    		+ "meninggalkannya. \n\n Akhirnya, ia pun tidak sadarkan diri.\r\n";
	    String textPhase12 = "Setelah beberapa waktu, Blekping ditemukan \noleh dua orang pengembara yang kebetulan \nmelewati daerah tersebut. \n\n"
	    		+ "Mereka melihat tubuhnya yang tergeletak \ntak berdaya di pinggiran sungai dan segera \nmembawanya ke tempat aman untuk merawat \nlukanya,";
	    String textPhase14 = "\n\n\ntanpa mengetahui identitas asli dari \nBLEKPING.";
	    switch (scenePhase) {
	        case 0:
	            updateScene(textPhase0, pressEnterY, 0.005F, true);
	            break;
	        case 1:
	            transitionScene(textPhase2, pressEnterY);
	            break;
	        case 2:
	            updateScene(textPhase2, pressEnterY, 0.01F, true);
	            break;
	        case 3:
	            transitionScene(textPhase4, pressEnterY);
	            break;
	        case 4:
	            updateScene(textPhase4, pressEnterY, 0.01F, true);
	            break;
	        case 5:
	            transitionScene(textPhase6, pressEnterY);
	            break;
	        case 6:
	            updateScene(textPhase6, pressEnterY, 0.01F, true);
	            break;
	        case 7:
	            transitionScene(textPhase8, pressEnterY);
	            break;
	        case 8:
	            updateScene(textPhase8, pressEnterY, 0.005F, true);
	            break;
	        case 9:
	        	transitionScene(textPhase10, pressEnterY);
	        	break;
	        case 10:
	        	updateScene(textPhase10, pressEnterY, 0.005F, true);
	        	break;
	        case 11:
	        	transitionScene(textPhase12, pressEnterY);
	        	break;
	        case 12:
	        	updateScene(textPhase12, pressEnterY, 0.005F, true);
	        	break;
	        case 13:
	        	transitionScene(textPhase14, pressEnterY);
	        	break;
	        case 14:
	        	updateScene(textPhase14, pressEnterY, 0.005F, true);
	        	break;
	        case 15:
	            finalizeScene();
	            break;
	    }
	}

	private void finalizeScene() {
	    gp.keyH.enterPressed = false;
	    sceneNum = 0;
	    scenePhase = 0;
	    gp.gameState = 1;
	    gp.playMusic(0);
	}
  
  public void scene_ending() {
	    int pressEnterY = 520;
	    String textPhase0 = "\n\n\nawokoawkawkok.\n\n";	            
	    String textPhase2 = "\n\n\nsama boss ga bisa nge hit kok mati??";
	    String textPhase4 = "\n\n\nSkill issue yaaa.\n\n";	            
	    String textPhase6 = "\n\n\nsemoga hari mu senin terus.\n\n";	            
	    String textPhase8 = "\n\n\nlove youu\n\n";

	    switch (scenePhase) {
	        case 0:
	        	gp.stopMusic();
	            updateScene(textPhase0, pressEnterY, 0.005F, true);
	            break;
	        case 1:
	            transitionScene(textPhase2, pressEnterY);
	            break;
	        case 2:
	            updateScene(textPhase2, pressEnterY, 0.01F, true);
	            break;
	        case 3:
	            transitionScene(textPhase4, pressEnterY);
	            break;
	        case 4:
	            updateScene(textPhase4, pressEnterY, 0.01F, true);
	            break;
	        case 5:
	            transitionScene(textPhase6, pressEnterY);
	            break;
	        case 6:
	            updateScene(textPhase6, pressEnterY, 0.01F, true);
	            break;
	        case 7:
	            transitionScene(textPhase8, pressEnterY);
	            break;
	        case 8:
	            updateScene(textPhase8, pressEnterY, 0.005F, true);
	            break;
	        case 9:
	            gp.gameState = gp.titleState;  // Kembali ke menu judul
	            gp.stopMusic();  // Pastikan musik dihentikan
	            sceneNum = 0;
	            scenePhase = 0;
	            alpha = 0.0F;
	            gp.restart();
	            break;
	    }
	  }
  
	private void updateScene(String text, int pressEnterY, float alphaChange, boolean increaseAlpha) {
	    drawBlackBackground(1.0F);
	    alpha = increaseAlpha ? Math.min(alpha + alphaChange, 1.0F) : Math.max(alpha + alphaChange, 0.0F);
	    drawString(alpha, 35.0F, 70, text, 40);
	    drawString(alpha, 35.0F, pressEnterY, "(Tekan Enter untuk melanjutkan)", 40);
	    if (gp.keyH.enterPressed) {
	        gp.keyH.enterPressed = false;
	        scenePhase++;
	    }
	}

	private void transitionScene(String nextText, int pressEnterY) {
	    drawBlackBackground(1.0F);
	    if (alpha > 0) {
	        alpha -= 0.02F;
	    } else {
	        scenePhase++;
	        alpha = 0.0F; // Reset alpha for the next scene
	        updateScene(nextText, pressEnterY, 0.01F, true); // Directly update to the next text
	    }
	}
  
  public boolean counterReached(int target) {
    boolean counterReached = false;
    this.counter++;
    if (this.counter > target) {
      counterReached = true;
      this.counter = 0;
    } 
    return counterReached;
  }
  
  public void drawBlackBackground(float alpha) {
    this.g2.setComposite(AlphaComposite.getInstance(3, alpha));
    this.g2.setColor(Color.black);
    this.gp.getClass();
    this.gp.getClass();
    this.g2.fillRect(0, 0, 960, 576);
    this.g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
  }
  
  public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
    this.g2.setComposite(AlphaComposite.getInstance(3, alpha));
    this.g2.setColor(Color.white);
	g2.setFont(arcade);
    this.g2.setFont(this.g2.getFont().deriveFont(20f));
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = text.split("\n")).length, b = 0; b < i; ) {
      String line = arrayOfString[b];
      int x = this.gp.ui.getXforCenteredText(line);
      this.g2.drawString(line, x, y);
      y += lineHeight;
      b++;
    } 
    this.g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
  }
}
