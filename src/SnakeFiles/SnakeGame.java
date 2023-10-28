package SnakeFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {


    private static class Tile{
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    //BoardVariables
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //ComponentVariables
    Tile snakeHead;
    Tile food;
    ArrayList<Tile> snakeBody;

    //GameVariables
    Timer gameLoop;
    int velocityX;
    int velocityY;
    int setVelocity;
    Color snakeColor;
    Color bgColor;
    boolean gridCondition;
    boolean gameOver = false;


    public SnakeGame(int boardWidth, int boardHeight, SessionSetup sessionSetup) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        //setting all set up variables
        setVelocity = sessionSetup.getSpeedLevel();
        snakeColor = sessionSetup.getSnakeColor();
        bgColor = sessionSetup.getBgColor();
        gridCondition = sessionSetup.getGridCondition();


        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(bgColor);
        addKeyListener(this); //makes the SnakeFiles.SnakeGame listen to key presses
        setFocusable(true);


        //initialized positions for the food and snakehead
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<>();
        setFoodLocations();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100,this); // By passing this as the ActionListener, it means that the current class (or an instance of the current class) should have an actionPerformed method to handle the timer events.
        gameLoop.start();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {


        //gridlines
        if(gridCondition){
            g.setColor(Color.GRAY);
            for(int i = 0; i < boardHeight/tileSize; i++){
                g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
                g.drawLine(0,i*tileSize,boardWidth,i*tileSize);
            }
        }

        //food
        g.setColor(Color.RED);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize,tileSize,true);


        //snakehead
        g.setColor(snakeColor);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);

        //snakebody
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true );
        }

        //score count
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over",tileSize-16, tileSize-5);
            g.drawString("Score : " + snakeBody.size(),tileSize-16, tileSize+15);
        } else{
            g.setColor(Color.GREEN);
            g.drawString("Score : " + snakeBody.size(),tileSize-16, tileSize-5);
        }


    }

    private void setFoodLocations() {
        food = new Tile(new Random().nextInt(boardWidth/tileSize),new Random().nextInt(boardHeight/tileSize));
    }

    public boolean detectCollisions(Tile t1, Tile t2){
        return t1.x == t2.x && t1.y == t2.y;
    }

    //Action listener implementation
    @Override
    public void actionPerformed(ActionEvent e) { //this is what is reiterated based on the timer
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    private void move() {
        //food consumption
        if(detectCollisions(snakeHead,food)){
            snakeBody.add(new Tile(food.x, food.y));
            setFoodLocations();
        }

        //growth
        for(int i = snakeBody.size() - 1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //changes the positions of the snakehead by inc to dec the tile x and y coordinates
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for(int i = 0; i < snakeBody.size() - 1; i++){
            if(detectCollisions(snakeBody.get(i),snakeHead)){
                gameOver = true;
                break;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize> boardHeight ){
            gameOver = true;
        }
    }



    //Key listener implementation
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != setVelocity){
            velocityY = -setVelocity;
            velocityX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -setVelocity) {
            velocityY = setVelocity;
            velocityX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -setVelocity) {
            velocityX = setVelocity;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != setVelocity) {
            velocityX = -setVelocity;
            velocityY = 0;
        }
    }

    //unnecessary implementations
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}




