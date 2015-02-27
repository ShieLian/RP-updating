package com.eloraam.redpower.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Icon;
@SideOnly(Side.CLIENT)
public class RPIcon implements Icon
{
	String iconname;
	int index;//0-255
	int u;//格 0-15
	int v;//格 0-15
	public RPIcon(String texturefile,int index)
	{
		this.u=index%16;
		this.v=index/16;
		this.index=index;
	}
	@Override
	public int getIconWidth()
	{
		return 16;
	}
	@Override
	public int getIconHeight()
	{
		return 16;
	}
	@Override
	public float getMinU()
	{
		return u*16;
	}

	@Override
	public float getMaxU()
	{
		return (u+1)*16;
	}

	@Override
	public float getInterpolatedU(double d0)
	{
		return (float) (d0*16+getMinU());
	}

	@Override
	public float getMinV()
	{
		return v*16;
	}

	@Override
	public float getMaxV()
	{
		return (v+1)*16;
	}

	@Override
	public float getInterpolatedV(double d0)
	{
		return (float)(getMinV()+d0*16);
	}

	@Override
	public String getIconName()
	{
		return this.iconname;
	}

}
class TextureSet
{
	BufferedImage bufferedtexset;
	public String simpletexlocation;
	public TextureSet(String simpletexlocation) throws IOException
	{
		this.simpletexlocation=simpletexlocation;
		this.bufferedtexset=ImageIO.read(new File(simpletexlocation));
	}
}
