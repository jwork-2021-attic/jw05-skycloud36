package com.anish.screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public abstract class RestartScreen implements Screen {

    @Override
    public abstract void displayOutput(AsciiPanel terminal);

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                return new WorldScreen();
            default:
                return this;
        }
    }

}
