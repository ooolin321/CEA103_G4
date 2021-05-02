package tests;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;

import tools.Arith;

public class Test {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	private static final String CATEGORY_REX = "([^\\s,]+,[^\\s,]+,[^\\s,]+)|([^\\s,]+,[^\\s,]+)|([^\\s,]+)";

	public static void main(String[] args) throws NamingException, SQLException {
		double showDiscount = Arith.mul(500.0, 100.0);
		System.out.println(showDiscount);

		showDiscount = Arith.div(showDiscount, 1000.0, 0);

		if (showDiscount % 10 == 0) {
			showDiscount = showDiscount / 10;
		}

		System.out.println(showDiscount);
	}
}
