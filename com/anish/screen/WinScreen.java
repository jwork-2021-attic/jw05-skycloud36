package com.anish.screen;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
public class WinScreen extends RestartScreen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("You won! Press enter to go again.", 2, 8);
    }

}
