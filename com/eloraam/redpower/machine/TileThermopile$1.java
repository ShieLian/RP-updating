package com.eloraam.redpower.machine;

import com.eloraam.redpower.core.BluePowerConductor;
import net.minecraft.tileentity.TileEntity;

class TileThermopile$1 extends BluePowerConductor
{
    final TileThermopile this$0;

    TileThermopile$1(TileThermopile var1)
    {
        this.this$0 = var1;
    }

    public TileEntity getParent()
    {
        return this.this$0;
    }

    public double getInvCap()
    {
        return 4.0D;
    }
}
