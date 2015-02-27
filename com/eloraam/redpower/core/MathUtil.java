package com.eloraam.redpower.core;

public class MathUtil
{
	public static int getMaxofList(int[] list)
	{
		int max=list[0];
		for(int i=list.length;i>0;i--)
		{
			max=max>list[i]?max:list[i];
		}
		return max;
	}
	public static int getMinofList(int[] list)
	{
		int min=list[0];
		for(int i=list.length;i>0;i--)
		{
			min=min<list[i]?min:list[i];
		}
		return min;
	}
}
