package ru.spbau.filatova;

public class WorldBuilder {
    private int width;
    private int height;
    private GameCell[][] gameCells;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameCells = new GameCell[width][height];
    }

    public GameWorld build() {
        return new GameWorld(gameCells);
    }

    //We want to get random GameWorld with random caves

    // first we fill the area with random floors and walls
    private WorldBuilder randomizeGameCells() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gameCells[x][y] = Math.random() < 0.5 ? GameCell.FLOOR : GameCell.WALL;
            }
        }
        return this;
    }

    //And then repeatedly smooth them
    private WorldBuilder smoothFloorsAndWalls(int times) {
        GameCell[][] newGameCells = new GameCell[width][height];
        for (int time = 0; time < times; time++) {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int floors = 0;
                    int rocks = 0;

                    for (int dx = -1; dx < 2; dx++) {
                        for (int dy = -1; dy < 2; dy++) {
                            if (x + dx < 0 || x + dx >= width || y + dy < 0
                                    || y + dy >= height)
                                continue;

                            if (gameCells[x + dx][y + dy] == GameCell.FLOOR)
                                floors++;
                            else
                                rocks++;
                        }
                    }
                    newGameCells[x][y] = floors >= rocks ? GameCell.FLOOR : GameCell.WALL;
                }
            }
            gameCells = newGameCells;
        }
        return this;
    }


    //And finally we're ready to make caves
    public WorldBuilder makeCaves() {
        return randomizeGameCells().smoothFloorsAndWalls(8);
    }
}
