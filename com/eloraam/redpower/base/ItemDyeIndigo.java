package com.eloraam.redpower.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemDyeIndigo extends Item
{
    public ItemDyeIndigo(int var1)
    {
        super(var1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public String getItemNameIS(ItemStack var1)
    {
        return "item.dyeIndigo";
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int var1)
    {
        return this.itemIcon;
    }
   /*public String getTextureFile()
    {
        return "/eloraam/base/items1.png";
    }*/

    public void useItemOnEntity(ItemStack var1, EntityLiving var2)
    {
        if (var1.getItemDamage() == 0)
        {
            if (var2 instanceof EntitySheep)
            {
                EntitySheep var3 = (EntitySheep)var2;

                if (!var3.getSheared() && var3.getFleeceColor() != 11)
                {
                    var3.setFleeceColor(11);
                    --var1.stackSize;
                }
            }
        }
    }
}
