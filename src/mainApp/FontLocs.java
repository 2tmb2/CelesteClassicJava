package mainApp;

import java.awt.Point;

public class FontLocs {
	public static Point getLoc(char c) {
		if (c >= 65 && c <= 90) {
			return new Point((c - 'A') * 4, 0);
		}
		int digit = c - '0';
		if (digit >= 0 && digit <= 3) {
			return new Point(104 + (4 * digit),0);
		}
		if (digit >= 4 && digit <= 9) {
			return new Point(4 * (digit - 4), 7);
		}
		switch (c) {
		case ':':
			return new Point(88,7);
		case '?':
			return new Point(84,7);
		case '=':
			return new Point(80,7);
		}
		return new Point(96,7);
	}
	public static Point getLoc(int digit) {
		if (digit >= 0 && digit <= 3) {
			return new Point(104 + (4 * digit),0);
		}
		if (digit >= 4 && digit <= 9) {
			return new Point(4 * (digit - 4), 7);
		}
		return new Point(0,0);
	}
}
