package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[120];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/don2.txt");
    }

    public void getTileImage() {
    	setup(0, "00", true);
    	setup(1, "01", false);
    	setup(2, "02", false);
    	setup(3, "03", false);
//    	setup(4, "04", false);
    	setup(5, "05", true);
    	setup(6, "06", true);
//    	setup(7, "07", true);
//    	setup(8, "08", true);
//    	setup(9, "09", true);
//    	setup(10, "10", true);
//    	setup(11, "11", true);
//    	setup(12, "12", true);
//    	setup(13, "13", true);
//    	setup(14, "14", true);
    	setup(15, "15", true);
    	setup(16, "16", true);
    	setup(17, "17", true);
    	setup(18, "18", true);
    	setup(19, "19", false);
    	setup(20, "20", true);
    	setup(21, "21", true);
    	setup(22, "22", true);
    	setup(23, "23", true);
    	setup(24, "24", true);
    	setup(25, "25", true);
    	setup(26, "26", true);
    	setup(27, "27", true);
    	setup(28, "28", true);
    	setup(29, "29", true);
    	setup(30, "30", true);
    	setup(31, "31", true);
    	setup(32, "32", false);
    	setup(33, "33", false);
    	setup(34, "34", false);
    	setup(35, "35", false);
    	setup(36, "36", false);
    	setup(37, "37", false);
    	setup(38, "38", false);
    	setup(39, "39", false);
    	setup(40, "40", true);
    	setup(41, "41", true);
    	setup(42, "42", true);
//    	setup(43, "43", false);
    	setup(44, "44", true);
    	setup(45, "45", true);
    	setup(46, "46", true);
    	setup(47, "47", true);
    	setup(48, "48", true);
    	setup(49, "49", true);
    	setup(50, "50", true);
    	setup(51, "51", true);
    	setup(52, "52", true);
    	setup(53, "53", true);
    	setup(54, "54", true);
    	setup(55, "55", true);
    	setup(56, "56", true);
//    	setup(57, "57", false);
    	setup(58, "58", true);
    	setup(59, "59", true);
    	setup(60, "60", true);
    	setup(61, "61", true);
    	setup(62, "62", true);
    	setup(63, "63", true);
    	setup(64, "64", true);
//    	setup(65, "65", false);
    	setup(66, "66", true);
//    	setup(67, "67", false);
    	setup(68, "68", false);
    	setup(69, "69", true);
    	setup(70, "70", true);
    	setup(71, "71", true);
    	setup(72, "72", true);
    	setup(73, "73", true);
    	setup(74, "74", true);
    	setup(75, "75", true);
//    	setup(76, "76", false);
//    	setup(77, "77", false);
//    	setup(78, "78", false);
    	setup(79, "79", true);
//    	setup(80, "80", false);
//    	setup(81, "81", false);
//    	setup(82, "82", false);
//    	setup(83, "83", false);
    	setup(84, "84", true);
    	setup(85, "85", true);
//    	setup(86, "86", false);
//    	setup(87, "87", false);
    	setup(88, "88", true);
    	setup(89, "89", true);
    	setup(90, "90", true);
    	setup(91, "91", true);
//    	setup(92, "92", false);
    	setup(93, "93", true);
    	setup(94, "94", true);
    	setup(95, "95", true);
    	setup(96, "96", true);
    	setup(97, "97", true);
    	setup(98, "98", true);
//    	setup(99, "99", true);
    	setup(100, "a", false);
    	setup(101, "b", false);
    	setup(102, "c", false);
    	setup(107, "h", false);
    	setup(108, "i", false);
    	setup(109, "j", false);
    	
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/new_tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath) {
        try {
            InputStream is = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                while (col < gp.maxWorldCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Stop camera movement at edges
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (gp.player.screenX > gp.player.worldX ||
                       gp.player.screenY > gp.player.worldY ||
                       rightOffset > gp.screenWidth - gp.player.worldX ||
                       bottomOffset > gp.screenHeight - gp.player.worldY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
