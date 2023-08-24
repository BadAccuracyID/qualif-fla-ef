package net.slc.ef.fla.qualif.state;

public abstract class MasterState {

    public abstract void onEnter();

    public abstract void onTick();

    public abstract void onExit();

    protected abstract void switchState(MasterState state);

}
