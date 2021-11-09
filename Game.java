package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBrick = 21;

    private Timer timer;
    private int delay = 8;   // speed of ball

    private int playerX = 310;

    private int ball_poxX = 120;
    private int ball_poxY = 350;
    private int ball_Xdir = -2;
    private int ball_Ydir = -5;

    private MapGenerator map;

    public Game() {
        map=new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay,this);
        timer.start();

    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing map
        map.draw((Graphics2D)g);   //  type cast

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("SCORE:"+ score,570,30);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ball_poxX, ball_poxY, 20, 20);

        if(totalBrick<=0){
            play=false;
            ball_Xdir=0;
            ball_Ydir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" YOU ROCK" ,260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString(" PRESS ENTER TO RESTART " ,230,350);

        }

        if(ball_poxY>570){
            play=false;
            ball_Xdir=0;
            ball_Ydir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" GAME OVER SCORE:"+ score ,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString(" PRESS ENTER TO RESTART " ,230,350);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play)
        {
            if(new Rectangle(ball_poxX,ball_poxY,20,20).intersects(new Rectangle(playerX,550,100,8)))
            {
                ball_Ydir=-ball_Ydir;
            }
            A: for(int i=0;i<map.map.length;i++){
                 for(int j=0;j<map.map[0].length;j++){
                     if(map.map[i][j]>0){
                         int brickX=j*map.brickWidth+80;
                         int brickY=i*map.brickHeight+50;
                         int brickWidth=map.brickWidth;
                         int brickHeight=map.brickHeight;

                         Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
                         Rectangle ballRect= new Rectangle(ball_poxX,ball_poxY,20,20);
                         Rectangle bricRect=rect;

                         if(ballRect.intersects(bricRect)){
                             map.setBrickValue(0,i,j);
                             totalBrick--;
                             score+=5;

                             if(ball_poxX+19<=bricRect.x || ball_poxX +1>=ballRect.x + bricRect.width){
                                 ball_Xdir=-ball_Xdir;
                             } else{
                                 ball_Ydir=-ball_Ydir;
                             }
                             break A;
                         }
                     }
                 }
            }

            ball_poxX+=ball_Xdir;
            ball_poxY+=ball_Ydir;
            if(ball_poxX<0)                 // for left border
            {
                ball_Xdir=-ball_Xdir;
            }
            if(ball_poxY<0)              // for top border
            {
                ball_Ydir=-ball_Ydir;
            }
            if(ball_poxX>670)            // for right border
            {
                ball_Xdir=-ball_Xdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
              if(playerX>=600)
              playerX=600;
              else

                  moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
                if(playerX<10)
                    playerX=10;
                else
                    moveLeft();
            }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play=true;
                ball_poxX=120;
                ball_poxY=350;
                ball_Xdir=-1;
                ball_Ydir=-2;
                playerX=310;
                score=0;
                totalBrick=21;
                map= new MapGenerator(3,7);

                repaint();
            }
        }

        }
        public void moveRight(){
             play=true;
             playerX+=20;
        }
        public void moveLeft(){
            play=true;
            playerX-=20;
        }

    }


