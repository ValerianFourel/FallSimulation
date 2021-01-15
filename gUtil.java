package daily;


public class gUtil {
private static final int WIDTH = 1400; // n.b. screen coordinates
 private static final int HEIGHT = 600;
 private static final int OFFSET = 200;
 private static final double SCALE = HEIGHT/100.0; // Pixels/meter
 /**
 * X coordinate to screen x
 * @param X
 * @return x screen coordinate - integer
 */

 static int XtoScreen(double X) {
 return (int) (X * SCALE);
 }

 /**
 * Screen x to X
 */

 static double ScreentoX(int x) {
 return x/SCALE;
 }

 /**
 * Y coordinate to screen y
 * @param Y
 * @return y screen coordinate - integer
 */

 static int YtoScreen(double Y) {
 return (int) (HEIGHT - Y * SCALE);
 }

 /**
 * Screen to Y
 */

 static double ScreentoY(int y) {
 return (HEIGHT-y)/SCALE;
 }

 /**
 * Length to screen length
 * @param length - double
 * @return sLen - integer
 */

 static int LtoScreen(double length) {
 return (int) (length * SCALE);
 }
}
