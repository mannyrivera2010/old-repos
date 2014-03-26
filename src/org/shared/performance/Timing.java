package org.shared.performance;

import java.text.DecimalFormat;

public class Timing {
	DecimalFormat decim = new DecimalFormat("#.####");
	// starting and ending time measured in milliseconds
	private double startTime, stopTime;

	public Timing() {
		startTime = stopTime = 0;
	}

	public void start() {
		startTime = System.currentTimeMillis(); // current millisecond time on
	}


	public Double stop_SecDouble() {
		stopTime = System.currentTimeMillis();
		double Current = (stopTime - startTime)/1000;
		
		//System.out.println(decim.format(Current));
		return Double.parseDouble(decim.format(Current));
	}

	public String sec_ms(Double DblFull) {
		String output = Double.parseDouble(decim.format(DblFull))+"";
		// System.out.println(output);
		return output;
	}
	
	public String Min_Sec_msa(Double DblFull) {
		
		String StrDbl = DblFull.toString();
		String Seconds = StrDbl.substring(0, StrDbl.indexOf("."));

		String msSeconds = StrDbl.substring(StrDbl.indexOf(".") + 1,
				StrDbl.length());

		Integer intseconds = Integer.parseInt(Seconds) % 60;
		int intminutes = Integer.parseInt(Seconds) / 60;

		String FinSecond = intseconds.toString();
		if (FinSecond.equalsIgnoreCase("0")) {
			FinSecond = "00";
		} else if (FinSecond.length() == 1) {
			FinSecond = "0" + FinSecond;
		}
		String output = intminutes + ":" + FinSecond + "." + msSeconds;
		// System.out.println(output);
		return output;
	}

}
