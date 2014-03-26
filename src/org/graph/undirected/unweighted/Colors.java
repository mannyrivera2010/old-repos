package org.graph.undirected.unweighted;

import java.util.ArrayList;

public class Colors {

	private ArrayList<String> ALColors = new ArrayList<String>();

	public Colors() {
		ALColors.add("GreenYellow");
		ALColors.add("Orange");
		ALColors.add("Aquamarine"); //2
		ALColors.add("LightPink");
		ALColors.add("White");
				
		ALColors.add("Beige");
		ALColors.add("Bisque");
		ALColors.add("BlanchedAlmond");
		ALColors.add("Blue");
		
		ALColors.add("Brown");
		ALColors.add("Burlywood");
		ALColors.add("Cadet Blue");
		ALColors.add("Chartreuse");
		ALColors.add("Chocolate");
		ALColors.add("Coral");
		ALColors.add("Cornflower");
		ALColors.add("Cornsilk");
		ALColors.add("Crimson");
		ALColors.add("Cyan");
		ALColors.add("DarkBlue");
		ALColors.add("DarkCyan");
		ALColors.add("DarkGoldenrod");
		ALColors.add("DarkGray");
		ALColors.add("DarkGreen");
		ALColors.add("AliceBlue"); //0
		ALColors.add("DarkKhaki");
		ALColors.add("DarkMagenta");
		ALColors.add("DarkOliveGreen");
		ALColors.add("DarkOrange");
		ALColors.add("DarkOrchid");
		ALColors.add("DarkRed");
		ALColors.add("DarkSalmon");
		ALColors.add("DarkSeaGreen");
		ALColors.add("DarkSlateBlue");
		ALColors.add("DarkSlateGray");
		ALColors.add("DarkTurquoise");
		ALColors.add("DarkViolet");
		ALColors.add("DeepPink");
		ALColors.add("DeepSkyBlue");
		ALColors.add("DimGray");
		ALColors.add("DodgerBlue");
		ALColors.add("Firebrick");
		ALColors.add("FloralWhite");
		ALColors.add("ForestGreen");
		ALColors.add("Fuchsia");
		ALColors.add("Gainsboro");
		ALColors.add("GhostWhite");
		ALColors.add("Gold");
		ALColors.add("Goldenrod");
		ALColors.add("Honeydew");
		ALColors.add("HotPink");
		ALColors.add("AntiqueWhite"); //1
		ALColors.add("IndianRed");
		ALColors.add("Indigo");
		ALColors.add("Ivory");
		ALColors.add("LavenderBlush");
		ALColors.add("LawnGreen");
		ALColors.add("LemonChiffon");
		ALColors.add("LightBlue");
		ALColors.add("LightCoral");
		ALColors.add("LightCyan");
		ALColors.add("LightGoldenrod");
		ALColors.add("LightGray");
		ALColors.add("LightGreen");
		
		ALColors.add("LightSalmon");
		ALColors.add("LightSeaGreen");
		ALColors.add("LightSkyBlue");
		ALColors.add("LightSlate Gray");
		ALColors.add("LightSteel Blue");
		ALColors.add("LightYellow");
		ALColors.add("LimeGreen");
		ALColors.add("Linen");
		ALColors.add("Magenta");
		ALColors.add("Medium Aquamarine");
		ALColors.add("Medium Blue");
		ALColors.add("Medium Orchid");
		ALColors.add("Medium Purple");
		ALColors.add("Medium Sea Green");
		ALColors.add("Medium Slate Blue");
		ALColors.add("Medium Spring Green");
		ALColors.add("Medium Turquoise");
		ALColors.add("Medium Violet Red");
		ALColors.add("Midnight Blue");
		ALColors.add("Mint Cream");
		ALColors.add("Misty Rose");
		ALColors.add("Moccasin");
		ALColors.add("Navajo White");
		ALColors.add("Navy");
		ALColors.add("Old Lace");
		ALColors.add("Olive");
		ALColors.add("Olive Drab");
		
		ALColors.add("OrangeRed");
		ALColors.add("Orchid");
		ALColors.add("PaleGoldenrod");
		ALColors.add("PaleGreen");
		ALColors.add("PaleTurquoise");
		ALColors.add("PaleVioletRed");
		ALColors.add("PapayaWhip");
		ALColors.add("PeachPuff");
		ALColors.add("Peru");
		ALColors.add("Pink");
		ALColors.add("Plum");
		ALColors.add("PowderBlue");
		ALColors.add("Red");
		ALColors.add("RosyBrown");
		ALColors.add("RoyalBlue");
		ALColors.add("SaddleBrown");
		ALColors.add("Salmon");
		ALColors.add("SandyBrown");
		ALColors.add("SeaGreen");
		ALColors.add("Seashell");
		ALColors.add("Sienna");
		ALColors.add("SkyBlue");
		ALColors.add("SlateBlue");
		ALColors.add("SlateGray");
		ALColors.add("Snow");
		ALColors.add("SpringGreen");
		ALColors.add("SteelBlue");
		ALColors.add("Tan");
		ALColors.add("Teal");
		ALColors.add("Thistle");
		ALColors.add("Tomato");
		ALColors.add("Turquoise");
		ALColors.add("Violet");
		ALColors.add("Wheat");
		ALColors.add("White");
		ALColors.add("WhiteSmoke");
		ALColors.add("Yellow");

	}

	public String getStringIndex(int index) {
		return index<0?"White":ALColors.get(index);
	}

}
