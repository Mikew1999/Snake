package entity;

import main.KeyHandler;

public interface IHandleKeys {
    public void handlePressed(KeyHandler.Keys key);
    public void handleUnpressed(KeyHandler.Keys key);
}
