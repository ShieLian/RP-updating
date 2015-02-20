package com.eloraam.redpower.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemTextured extends Item
{
    /**
     * @param id
     * @param iconindex
     * @param filename
     */
    public ItemTextured(IconRegister par1IconRegister,int id, int iconindex, String filename)
    {
        super(id);
        this.registerIcons(par1IconRegister, filename, iconindex);
    }
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister,String location,int iconindex)
    {
        super.registerIcons(par1IconRegister);
        this.itemIcon = par1IconRegister.registerIcon(location+iconindex);
    }
}
