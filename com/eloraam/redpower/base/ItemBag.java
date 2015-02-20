package com.eloraam.redpower.base;

import com.eloraam.redpower.RedPowerBase;
import com.eloraam.redpower.base.ItemBag$InventoryBag;
import com.eloraam.redpower.core.CoreLib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemBag extends Item
{
	public Icon[] iconlist=new Icon[16];
    public ItemBag(int var1)
    {
        super(var1);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        //this.setTextureFile("/eloraam/base/items1.png");
        this.setUnlocalizedName("rpBag");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    public static IInventory getBagInventory(ItemStack var0)
    {
        return !(var0.getItem() instanceof ItemBag) ? null : new ItemBag$InventoryBag(var0);
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack var1)
    {
        return 1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int var1)
    {
        return iconlist[var1];
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        for(int i=0;i<16;i++) this.iconlist[i] = par1IconRegister.registerIcon("RedPower/base:"+i);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3)
    {
        if (CoreLib.isClient(var2))
        {
            return var1;
        }
        else if (var3.isSneaking())
        {
            return var1;
        }
        else
        {
            var3.openGui(RedPowerBase.instance, 4, var2, 0, 0, 0);
            return var1;
        }
    }
}