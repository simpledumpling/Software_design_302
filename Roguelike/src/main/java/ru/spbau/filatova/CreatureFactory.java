package ru.spbau.filatova;


import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private GameWorld gameWorld;

    public CreatureFactory(GameWorld gameWorld){
        this.gameWorld = gameWorld;
    }

    //Create new Player with glyph @, white color, 100 hp and 20 attack power
    public Creature newPlayer(){
        Creature player = new Creature(gameWorld, '@', AsciiPanel.brightWhite, 100, 20);
        gameWorld.addCreatureAtEmptyLocation(player);
        new PlayerActor(player);
        return player;
    }

    //Create static monster
    public Creature newStaticMonster(){
        Creature staticMonster = new Creature(gameWorld, 'f', AsciiPanel.brightBlue, 10, 5);
        gameWorld.addCreatureAtEmptyLocation(staticMonster);
        new StationaryMonsterActor(staticMonster);
        return staticMonster;
    }

    //Create active monster
    public Creature newDynamicMonster(){
        Creature dynamicMonster= new Creature(gameWorld, 'd', AsciiPanel.yellow, 20, 20);
        gameWorld.addCreatureAtEmptyLocation(dynamicMonster);
        new DynamicMonsterActor(dynamicMonster);
        return dynamicMonster;
    }
}
