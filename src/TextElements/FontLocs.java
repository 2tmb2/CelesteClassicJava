package TextElements;

import java.awt.Point;

/**
 * Contains methods to return pointers to positions on font.png according to the provided character
 */
public class FontLocs {
	/**
	 * Returns position pointing to provided character for drawing from font.png
	 * @param c character to draw
	 * @return point of top left of letter in font.png
	 */
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
	/**
	 * Returns position pointing to provided number for drawing from font.png
	 * @param digit to draw
	 * @return position of digit on font.png
	 */
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
