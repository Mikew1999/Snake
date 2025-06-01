package main;

import entity.Position;
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
        if (checkHitSelf(facing)) return CollisionType.self;
        return CollisionType.none;
    };

    private boolean checkHitSelf(Keys facing) {
        // int headLeft = gamePanel.snake.head.xPos - (gamePanel.snake.width / 2);
        // int headRight = gamePanel.snake.head.xPos + (gamePanel.snake.width / 2);
        // int headTop = gamePanel.snake.head.yPos - (gamePanel.snake.height / 2);
        // int headBottom = gamePanel.snake.head.yPos + (gamePanel.snake.height / 2);
        for (int i = 1; i < gamePanel.snake.tail.size(); i++) {
            Position pos = gamePanel.snake.tail.get(i);
            int tailItemLeft = pos.xPos - (gamePanel.snake.width / 2);
            int tailItemRight = pos.xPos + (gamePanel.snake.width / 2);
            int tailItemTop = pos.yPos - (gamePanel.snake.height / 2);
            int tailItemBottom = pos.yPos + (gamePanel.snake.height / 2);
            if (
                gamePanel.snake.head.xPos >= tailItemLeft &&
                gamePanel.snake.head.xPos <= tailItemRight &&
                gamePanel.snake.head.yPos >= tailItemTop &&
                gamePanel.snake.head.yPos <= tailItemBottom
            ) return true;
        };
        return false;
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
