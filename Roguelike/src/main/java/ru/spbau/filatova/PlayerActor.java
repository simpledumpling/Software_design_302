package ru.spbau.filatova;


public class PlayerActor extends CreatureActor {
    public PlayerActor(Creature creature) {
        super(creature);
    }

    //Go or dig a cave
    public void onEnter(int x, int y, GameCell gameCell){
        if (gameCell.isGround()){
            creature.setX(x);
            creature.setY(y);
        } else if (gameCell.isDiggable()) {
            creature.digTheCave(x, y);
        }
    }

    @Override
    public void onAttack(Creature enemyCreature) {
        //When we attack monster, our hp decrease, monster died and we go to it's place on map
        creature.modifyHp(-enemyCreature.attackValue());
        enemyCreature.die();
        creature.setX(enemyCreature.getX());
        creature.setY(enemyCreature.getY());
    }

    @Override
    public void onPick(Achievement achievement) {
        //When we pick smth, it removed from the screen and we go to it's place
        creature.getGameWorld().remove(achievement);
        creature.setX(achievement.getX());
        creature.setY(achievement.getY());
    }

}
