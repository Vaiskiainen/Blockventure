package main;

import object.OBJ_BerryBush;
import object.OBJ_Chest;

public class AssetSetter {

    GamePanel gp;
    
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {

        gp.obj[0] = new OBJ_Chest();
        gp.obj[0].worldX = 11*gp.tileSize;
        gp.obj[0].worldY = 32*gp.tileSize;

        gp.obj[1] = new OBJ_BerryBush();
        gp.obj[1].worldX = 22*gp.tileSize;
        gp.obj[1].worldY = 29*gp.tileSize;

        gp.obj[2] = new OBJ_BerryBush();
        gp.obj[2].worldX = 2*gp.tileSize;
        gp.obj[2].worldY = 37*gp.tileSize;
        gp.obj[3] = new OBJ_BerryBush();
        gp.obj[3].worldX = 2*gp.tileSize;
        gp.obj[3].worldY = 38*gp.tileSize;
        gp.obj[4] = new OBJ_BerryBush();
        gp.obj[4].worldX = 2*gp.tileSize;
        gp.obj[4].worldY = 39*gp.tileSize;

        gp.obj[5] = new OBJ_BerryBush();
        gp.obj[5].worldX = 4*gp.tileSize;
        gp.obj[5].worldY = 37*gp.tileSize;
        gp.obj[6] = new OBJ_BerryBush();
        gp.obj[6].worldX = 4*gp.tileSize;
        gp.obj[6].worldY = 38*gp.tileSize;
        gp.obj[7] = new OBJ_BerryBush();
        gp.obj[7].worldX = 4*gp.tileSize;
        gp.obj[7].worldY = 39*gp.tileSize;
        gp.obj[8] = new OBJ_BerryBush();
        gp.obj[8].worldX = 6*gp.tileSize;
        gp.obj[8].worldY = 37*gp.tileSize;
        gp.obj[9] = new OBJ_BerryBush();
        gp.obj[9].worldX = 6*gp.tileSize;
        gp.obj[9].worldY = 38*gp.tileSize;
        gp.obj[10] = new OBJ_BerryBush();
        gp.obj[10].worldX = 6*gp.tileSize;
        gp.obj[10].worldY = 39*gp.tileSize;
    }

}
