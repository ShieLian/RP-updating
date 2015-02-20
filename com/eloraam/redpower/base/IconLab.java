package com.eloraam.redpower.base;

import java.util.HashMap;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
@SideOnly(Side.CLIENT)
public class IconLab
{
	public static HashMap<Integer,Icon> iconmap=new HashMap();
	public static void registIcon(int index)
	{
		iconmap.put(index,getIcon(index));
	}
	public static Icon getIcon(int index)
	{
		return ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).registerIcon("eloraam/base:"+index);
	}
}
