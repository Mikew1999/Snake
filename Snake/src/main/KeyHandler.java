package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import entity.IHandleKeys;

public class KeyHandler implements KeyListener {

    public static enum Keys {
        left,
        right,
        up,
        down
    };

    private IHandleKeys handler;
    private HashMap<String, Keys> keyMap = new HashMap<String, Keys>();

    public KeyHandler(IHandleKeys handler) {
        this.handler = handler;
        initKeyMap();
    };

    private void initKeyMap() {
        keyMap.put("A", Keys.left);
        keyMap.put("D", Keys.right);
        keyMap.put("W", Keys.up);
        keyMap.put("S", Keys.down);
    };

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
        if (!keyMap.containsKey(keyText)) return;
        handler.handlePressed(keyMap.get(keyText));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
        if (!keyMap.containsKey(keyText)) return;
        handler.handleUnpressed(keyMap.get(keyText));
    }
}
