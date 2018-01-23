package com.game.snake.objects.room;

import com.game.snake.controller.KeyboardObserver;
import com.game.snake.graphics.Layer;
import com.game.snake.objects.mouse.Mouse;
import com.game.snake.objects.snake.Snake;
import com.game.snake.objects.snake.SnakeDirection;
import com.game.snake.objects.snake.SnakeSection;
import org.jetbrains.annotations.Contract;

import java.awt.event.KeyEvent;

/**
 * @author Koliadin Nikita
 * @version 1.2
 *
 * This class is the "Room" for the snake
 */
public class Room {

    public static Room room;

    private Snake snake;
    private Mouse mouse;

    /* Height and width of the room */
    private  int width;
    private  int height;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    @Contract(pure = true)
    public int getWidth() {
        return width;
    }

    @Contract(pure = true)
    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Create new mouse
     */
    public void createMouse() {
        mouse = new Mouse((int) (Math.random() * width), (int) (Math.random() * height));
        for (SnakeSection snakez : snake.getSections()) {
            if (snakez.getX() == mouse.getX() && snakez.getY() == mouse.getY()) {
                createMouse();
            }
        }
    }

    /**
     * The method is called when the mouse is eaten
     */
    public void eatMouse() {
        createMouse();
    }

    /**
     * The main program cycle.
     * All important actions take place here
     */
    public void run() {
        /* Create the object "the observer for the keyboard" and start it */
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        /* while snake is alive */
        while (snake.isAlive()) {
            /* "Observer" contains events about keystrokes? */
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                /* if equals 'q' -> exit */
                if (event.getKeyChar() == 'q') {
                    return;
                }  else if (event.getKeyChar() == 'p') { /* PAUSE */
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            if (keyboardObserver.hasKeyEvents()) {
                                KeyEvent eventNew = keyboardObserver.getEventFromTop();
                                if (eventNew.getKeyChar() == 'p') {
                                    break;
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                /* arrow movement */
                switch (event.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (snake.getDirection() != SnakeDirection.RIGHT) {
                            snake.setDirection(SnakeDirection.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (snake.getDirection() != SnakeDirection.LEFT) {
                            snake.setDirection(SnakeDirection.RIGHT);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (snake.getDirection() != SnakeDirection.DOWN) {
                            snake.setDirection(SnakeDirection.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (snake.getDirection() != SnakeDirection.UP) {
                            snake.setDirection(SnakeDirection.DOWN);
                        }
                        break;
                }
            }

            snake.move();   /* move the snake */
            print();        /* to display the current state of the game */
            sleep();        /* pause between moves */
        }

        /* Display the message "Game Over" */
        System.out.println("Game Over!");
    }

    /**
     * Print everything to the window
     */
    private void print() {
        if (KeyboardObserver.frame != null) {
            KeyboardObserver.frame.setContentPane(new Layer());
            KeyboardObserver.frame.setVisible(true);
        }
    }

    /**
     * The program pauses, the length of which depends on the length of the snake.
     */
    private void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level <= 16 ? (300 - 10 * level - 1) : 150;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
