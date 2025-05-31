package main;

import main.KeyHandler.Keys;

public class CollisionDetector {
    private GamePanel gamePanel;

    public enum CollisionType {
        none,
        apple,
        wall,
        self
    }

    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    };

    public CollisionType checkCollision() {
        Keys facing = gamePanel.snake.currentDirection;
        if (checkHitApple()) return CollisionType.apple;
        if (checkHitWall(facing)) return CollisionType.wall;
        return CollisionType.none;
    };

    private boolean checkHitWall(Keys facing) {
        switch (facing) {
            case up:
                if ((gamePanel.snake.head.yPos + (gamePanel.snake.height / 2)) < (gamePanel.snake.height / 2))
                    return true;
                break;
            case down:
                if (gamePanel.snake.head.yPos > (gamePanel.screenHeight - gamePanel.snake.height))
                    return true;
                break;
            case left:
                if (gamePanel.snake.head.xPos < 0)
                    return true;
                break;
            case right:
                if (gamePanel.snake.head.xPos > (gamePanel.screenWidth - gamePanel.snake.width))
                    return true;
                break;
            default:
                break;
        }
        return false;
    };

    private boolean checkHitApple() {
        int appleLeft = gamePanel.apple.xPos - (gamePanel.apple.width / 2);
        int appleRight = gamePanel.apple.xPos + (gamePanel.apple.width / 2);
        int appleTop = gamePanel.apple.yPos - (gamePanel.apple.height / 2);
        int appleBottom = gamePanel.apple.yPos + (gamePanel.apple.height / 2);
        return (
            gamePanel.snake.head.xPos >= appleLeft &&
            gamePanel.snake.head.xPos <= appleRight &&
            gamePanel.snake.head.yPos >= appleTop &&
            gamePanel.snake.head.yPos <= appleBottom
        );
    };
}
