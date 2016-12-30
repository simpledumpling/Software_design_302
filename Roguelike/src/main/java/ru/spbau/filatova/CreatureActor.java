package ru.spbau.filatova;


public class CreatureActor {
    protected Creature creature;

    public CreatureActor(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureActor(this);
    }

    public void onEnter(int x, int y, GameCell gameCell) {
        if (gameCell.isGround()) {
            creature.setX(x);
            creature.setY(y);
        } else if (gameCell.isDiggable()) {
            creature.digTheCave(x, y);
        }
    }

    //Method to attack enemyCreature
    public void onAttack(Creature enemyCreature) {
    }

    //Method when we get attacked
    public void onAttacked() {
    }

    //Method when we die
    public void onDeath() {
    }

    //Method to update
    public void onUpdate() {

    }

    //Find some achievement
    public void onPick(Achievement achievement) {
    }

}
