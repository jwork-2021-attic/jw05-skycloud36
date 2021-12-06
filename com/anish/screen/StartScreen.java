package com.anish.screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("This is the start screen.", 30, 18);
        terminal.write("Press ENTER to continue...", 30, 20);
    }

}
