

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.anish.maze.MazeGenerator;
import com.anish.maze.World;
import com.anish.screen.Screen;
import com.anish.screen.StartScreen;
import com.anish.screen.WorldScreen;


import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

public class Main extends JFrame implements KeyListener, MouseListener {

    private AsciiPanel terminal;
    private Screen screen;


    public Main() {
        super();
        // terminal = new AsciiPanel(World.WIDTH, World.HEIGHT, AsciiFont.TALRYTH_15_15);
        terminal = new AsciiPanel(World.WIDTH, World.HEIGHT + 5, AsciiFont.CP437_16x16);
        add(terminal);
        pack();
        // screen = new StartScreen();
        screen = new WorldScreen();
        addKeyListener(this);
        addMouseListener(this);
        paintByTimer();

    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        // if(screen instanceof WorldScreen){
        //     Screen temp = ((WorldScreen)screen).Finish();
        //     if(temp != null){    
        //         super.repaint();
        //         screen = temp;
        //         try {
        //             TimeUnit.MILLISECONDS.sleep(1000);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen.respondToUserInput(e);
        // repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println("x:" + e.getX() + " y:" + e.getY());
        screen.respondToUserMouse(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    private void paintByTimer(){
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                repaint();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 30, TimeUnit.MILLISECONDS);
    }

    private void paintByThread(){
        class MyThread extends Thread{
            @Override
            public void run(){
                Timer myTimer = new Timer();
                class myTask extends TimerTask{
                    @Override
                    public void run(){
                        repaint();
                    }
                };
                myTask mytask = new myTask();
                myTimer.schedule(mytask, 0, 30);
            }
        };
        Thread t = new MyThread();
        t.start();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }



}
