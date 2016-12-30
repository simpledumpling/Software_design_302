package ru.spbau.filatova;


public class DynamicMonsterActor extends CreatureActor{
    public DynamicMonsterActor(Creature creature) {
        super(creature);
    }

    @Override
    public void onUpdate(){
        //Active monster do random step
        int dx = (int)(Math.random() * 3) - 1;
        int dy = (int)(Math.random() * 3) - 1;
        creature.makeTheStep(dx, dy);
    }
}
