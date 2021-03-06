package com.eloraam.redpower.machine;

import com.eloraam.redpower.RedPowerMachine;
import com.eloraam.redpower.base.TileAppliance;
import com.eloraam.redpower.core.BluePowerConductor;
import com.eloraam.redpower.core.BluePowerEndpoint;
import com.eloraam.redpower.core.CoreLib;
import com.eloraam.redpower.core.IBluePowerConnectable;
import com.eloraam.redpower.core.IChargeable;
import com.eloraam.redpower.core.Packet211TileDesc;
import com.eloraam.redpower.core.RedPowerLib;
import com.eloraam.redpower.machine.TileChargingBench$1;
import java.io.IOException;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileChargingBench extends TileAppliance implements IInventory, IBluePowerConnectable
{
    BluePowerEndpoint cond = new TileChargingBench$1(this);
    public boolean Powered = false;
    public int Storage = 0;
    private ItemStack[] contents = new ItemStack[16];
    public int ConMask = -1;

    public int getConnectableMask()
    {
        return 1073741823;
    }

    public int getConnectClass(int var1)
    {
        return 64;
    }

    public int getCornerPowerMode()
    {
        return 0;
    }

    public BluePowerConductor getBlueConductor(int var1)
    {
        return this.cond;
    }

    public int getLightValue()
    {
        return 0;
    }

    public int getExtendedID()
    {
        return 5;
    }

    public int getMaxStorage()
    {
        return 3000;
    }

    public int getStorageForRender()
    {
        return this.Storage * 4 / this.getMaxStorage();
    }

    public int getChargeScaled(int var1)
    {
        return Math.min(var1, var1 * this.cond.Charge / 1000);
    }

    public int getStorageScaled(int var1)
    {
        return Math.min(var1, var1 * this.Storage / this.getMaxStorage());
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();

        if (!CoreLib.isClient(this.worldObj))
        {
            if (this.ConMask < 0)
            {
                this.ConMask = RedPowerLib.getConnections(this.worldObj, this, this.xCoord, this.yCoord, this.zCoord);
                this.cond.recache(this.ConMask, 0);
            }

            this.cond.iterate();
            this.dirtyBlock();

            if (this.cond.Flow == 0)
            {
                if (this.Powered)
                {
                    this.Powered = false;
                    this.updateBlock();
                }
            }
            else if (!this.Powered)
            {
                this.Powered = true;
                this.updateBlock();
            }

            int var1 = this.getStorageForRender();

            if (this.cond.Charge > 600 && this.Storage < this.getMaxStorage())
            {
                int var2 = Math.min((this.cond.Charge - 600) / 40, 5);
                var2 = Math.min(var2, this.getMaxStorage() - this.Storage);
                this.cond.drawPower((double)(var2 * 1000));
                this.Storage += var2;
            }

            boolean var5 = this.Active;
            this.Active = false;

            if (this.Storage > 0)
            {
                for (int var3 = 0; var3 < 16; ++var3)
                {
                    if (this.contents[var3] != null && this.contents[var3].getItem() instanceof IChargeable && this.contents[var3].getItemDamage() > 1)
                    {
                        int var4 = Math.min(this.contents[var3].getItemDamage() - 1, this.Storage);
                        var4 = Math.min(var4, 25);
                        this.contents[var3].setItemDamage(this.contents[var3].getItemDamage() - var4);
                        this.Storage -= var4;
                        this.onInventoryChanged();
                        this.Active = true;
                    }
                }
            }

            if (var1 != this.getStorageForRender() || var5 != this.Active)
            {
                this.updateBlock();
            }
        }
    }

    public boolean onBlockActivated(EntityPlayer var1)
    {
        if (var1.isSneaking())
        {
            return false;
        }
        else if (CoreLib.isClient(this.worldObj))
        {
            return true;
        }
        else
        {
            var1.openGui(RedPowerMachine.instance, 14, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            return true;
        }
    }

    public void onBlockPlaced(ItemStack var1, int var2, EntityLiving var3)
    {
        this.Rotation = (int)Math.floor((double)(var3.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
    }

    public void onBlockRemoval()
    {
        for (int var1 = 0; var1 < 2; ++var1)
        {
            ItemStack var2 = this.contents[var1];

            if (var2 != null && var2.stackSize > 0)
            {
                CoreLib.dropItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, var2);
            }
        }
    }

    public void onBlockNeighborChange(int var1)
    {
        this.ConMask = -1;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 16;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.contents[var1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        if (this.contents[var1] == null)
        {
            return null;
        }
        else
        {
            ItemStack var3;

            if (this.contents[var1].stackSize <= var2)
            {
                var3 = this.contents[var1];
                this.contents[var1] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = this.contents[var1].splitStack(var2);

                if (this.contents[var1].stackSize == 0)
                {
                    this.contents[var1] = null;
                }

                this.onInventoryChanged();
                return var3;
            }
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if (this.contents[var1] == null)
        {
            return null;
        }
        else
        {
            ItemStack var2 = this.contents[var1];
            this.contents[var1] = null;
            return var2;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.contents[var1] = var2;

        if (var2 != null && var2.stackSize > this.getInventoryStackLimit())
        {
            var2.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Charging Bench";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : var1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void closeChest() {}

    public void openChest() {}

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        NBTTagList var2 = var1.getTagList("Items");
        this.contents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.contents.length)
            {
                this.contents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.cond.readFromNBT(var1);
        this.Storage = var1.getShort("stor");
        byte var6 = var1.getByte("ps");
        this.Powered = (var6 & 1) > 0;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        NBTTagList var2 = new NBTTagList();
        int var3;

        for (var3 = 0; var3 < this.contents.length; ++var3)
        {
            if (this.contents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.contents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        var1.setTag("Items", var2);
        this.cond.writeToNBT(var1);
        var1.setShort("stor", (short)this.Storage);
        var3 = this.Powered ? 1 : 0;
        var1.setByte("ps2", (byte)var3);
    }

    protected void readFromPacket(Packet211TileDesc var1) throws IOException
    {
        this.Rotation = var1.getByte();
        int var2 = var1.getByte();
        this.Active = (var2 & 1) > 0;
        this.Powered = (var2 & 2) > 0;
        this.Storage = (int)var1.getUVLC();
    }

    protected void writeToPacket(Packet211TileDesc var1)
    {
        var1.addByte(this.Rotation);
        int var2 = (this.Active ? 1 : 0) | (this.Powered ? 2 : 0);
        var1.addByte(var2);
        var1.addUVLC((long)this.Storage);
    }
}
