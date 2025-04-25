package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed;
    // DEBUG
    public boolean debugMode = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    } 

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState) {
            
            if(code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum == 1) {
                    // SOON TO BE IMPLEMENTED
                }
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
            }else if(gp.gameState == gp.pauseState)
            {
                if(code == KeyEvent.VK_UP) {
                    gp.ui.pauseCommandNum--;
                    if(gp.ui.pauseCommandNum < 0) {
                        gp.ui.pauseCommandNum = 1;
                    }
                }
                if(code == KeyEvent.VK_DOWN) {
                    gp.ui.pauseCommandNum++;
                    if(gp.ui.pauseCommandNum > 1) {
                        gp.ui.pauseCommandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    if(gp.ui.pauseCommandNum == 0) {
                        gp.gameState = gp.playState;
                    }
                    if(gp.ui.pauseCommandNum == 1) {
                        gp.gameState = gp.titleState;
                    }
                }
            }



        
        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_E) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.inventoryState;
            } else if(gp.gameState == gp.inventoryState) {
                gp.gameState = gp.playState;
            }
        }
        if(code == KeyEvent.VK_Q) {
            if(gp.gameState == gp.playState) {
                gp.player.interactWithHoldingItem();
            }
        }
        if(code == KeyEvent.VK_F) {
            if(gp.gameState != gp.titleState) {
                if(gp.ui.holding != "none") {
                    
                gp.player.inventory.add(gp.ui.holding);
                }
                gp.ui.holding = "none";
                
            }
        }
        
        if(code == KeyEvent.VK_F3) {
            if(debugMode == false) {
                debugMode = true;
            } else if(debugMode == true) {
                debugMode = false;
            }
            else {
                System.out.println("[DEBUG] Debug mode failed!");
            }
        }
        if(code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
        if(code == KeyEvent.VK_ESCAPE) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if(gp.gameState == gp.titleState) {
                System.exit(0);
            } else {
                gp.gameState = gp.playState;
            }
        }
        if(gp.gameState == gp.inventoryState) {
            if(code == KeyEvent.VK_UP) {
                gp.ui.slotRow--;
                if(gp.ui.slotRow < 0) {
                    gp.ui.slotRow = 0;
                }
            }
            if(code == KeyEvent.VK_DOWN) {
                gp.ui.slotRow++;
                if(gp.ui.slotRow > 3) {
                    gp.ui.slotRow = 3;
                }
            }
            if(code == KeyEvent.VK_LEFT) {
                gp.ui.slotCol--;
                if(gp.ui.slotCol < 0) {
                    gp.ui.slotCol = 0;
                }
            }
            if(code == KeyEvent.VK_RIGHT) {
                gp.ui.slotCol++;
                if(gp.ui.slotCol > 5) {
                    gp.ui.slotCol = 5;
                }
            }
            if(code == KeyEvent.VK_BACK_SPACE) {
                if(gp.ui.slotCol + gp.ui.slotRow * 6 < gp.player.inventory.size()) {
                    gp.player.inventory.remove(gp.ui.slotCol + gp.ui.slotRow * 6);
                    System.out.println("Removed item at index: " + (gp.ui.slotCol + gp.ui.slotRow * 6));
                } else {
                    System.out.println("No item to remove at index: " + (gp.ui.slotCol + gp.ui.slotRow * 6));
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                gp.ui.enterPressed = true;
            }
                        
                        


                        

                            
        
        
    }
                    } 
                
        
    
    

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }



}
