package net.slc.ef.fla.qualif.state;

public abstract class TickableState extends MasterState {

    public abstract void onTick();

    @Override
    protected void switchState(MasterState state) {
        this.switchState((TickableState) state);
    }

    protected abstract void switchState(TickableState state);

}
