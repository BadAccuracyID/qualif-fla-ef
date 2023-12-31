package net.slc.ef.fla.qualif.state;

public abstract class MasterState {

    public abstract String getName();

    public abstract void onEnter();

    public abstract void onExit();

    protected abstract void switchState(MasterState state);

    public abstract boolean isState(Class<? extends MasterState> clazz);

}
