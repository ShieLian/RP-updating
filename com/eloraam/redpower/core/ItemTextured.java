package com.eloraam.redpower.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemTextured extends Item
{
	int iconindex;
	String pic_location_name;
    /**
     * @param id
     * @param iconindex
     * @param picname
     */
    public ItemTextured(int id, int iconindex, String picname)
    {
        super(id);
        this.iconindex=iconindex;
        this.pic_location_name=picname;
        //this.registerIcons(picname, iconindex);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.itemIcon=registerIcons(par1IconRegister,pic_location_name,iconindex);
    }
    @SideOnly(Side.CLIENT)
    public Icon registerIcons(IconRegister par1IconRegister,String pic_location_name,int iconindex)
    {
        return par1IconRegister.registerIcon("elormma:"+pic_location_name+"/"+iconindex);
    }
    /**
     * @param par1IconRegister
     * @param pic_location_name old name with sub-mod name("base/items1")
     */
    @SideOnly(Side.CLIENT)
    public Icon registerIcons(IconRegister par1IconRegister,String pic_location_name)
    {
        return par1IconRegister.registerIcon("elormma:"+pic_location_name+"/"+this.iconindex);
    }
}
