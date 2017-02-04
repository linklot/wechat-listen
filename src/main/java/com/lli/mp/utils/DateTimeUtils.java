package com.lli.mp.utils;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeUtils {
	public static String toFriendlyString(String input) {
		DateTime now = DateTime.now();
		DateTime inputDt = DateTime.parse(input, ISODateTimeFormat.basicDateTime());
		DateTime yesterday = now.minusDays(1);

		return convertDateToFriendlyString(now, yesterday, inputDt);
	}

	public static String toCommentFriendlyString(String input) {
		DateTime now = DateTime.now();
		DateTime inputDt = DateTime.parse(input, ISODateTimeFormat.dateTime());
		DateTime yesterday = now.minusDays(1);

		return convertDateToFriendlyString(now, yesterday, inputDt);
	}

	private static String convertDateToFriendlyString(DateTime now, DateTime yesterday, DateTime inputDt) {
		StringBuilder sb = new StringBuilder();

//		System.out.println("input: "+ inputDt.year() +" : "+ inputDt.monthOfYear() +" : "+ inputDt.dayOfMonth());
//		System.out.println("now: "+ now.year() +" : "+ now.monthOfYear() +" : "+ now.dayOfMonth());

		if(inputDt.year().get() == now.year().get()
				&& inputDt.monthOfYear().get() == now.monthOfYear().get()
				&& inputDt.dayOfMonth().get() == now.dayOfMonth().get()) {
			// Today
			sb.append("今天 ");
			sb.append(inputDt.toString("HH:mm"));
		} else if(inputDt.year().get() == yesterday.year().get()
				&& inputDt.monthOfYear().get() == yesterday.monthOfYear().get()
				&& inputDt.dayOfMonth().get() == yesterday.dayOfMonth().get()) {
			// Yesterday
			sb.append("昨天 ");
			sb.append(inputDt.toString("HH:mm"));
		} else {
			sb.append(inputDt.toString("yyyy-MM-dd HH:mm"));
		}

		return sb.toString();
	}
}
