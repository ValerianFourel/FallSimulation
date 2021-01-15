package daily;


import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import acm.graphics.GRect;
import acm.gui.TableLayout;

public class sliderBox {
/**
 * This constructor creates an instance of a JPanel using the default
 * layout manager, and populates it as follows:
 * Parameter: JLabel --- JSlider --- JLabel JLabel
 * @param min - Integer, min slider value
 * @param dValue - Integer, default slider value
 * @param max - Integer, max slider value
 */
public sliderBox(String name, Integer min, Integer dValue, Integer max) { // Integer values
 myPanel = new JPanel();
 nameLabel = new JLabel(name);
 minLabel = new JLabel(min.toString());
 maxLabel = new JLabel(max.toString());
 mySlider = new JSlider(min,max,dValue);
 sReadout = new JLabel(dValue.toString());
 sReadout.setForeground(Color.blue);
 myPanel.setLayout(new TableLayout(1,5));
 myPanel.add(nameLabel,"width=100");
 myPanel.add(minLabel,"width=25");
 myPanel.add(mySlider,"width=100");
 myPanel.add(maxLabel,"width=100");
 myPanel.add(sReadout,"width=80");
 imin=min;
 imax=max;
 }
public sliderBox(String name, Double min, Double dValue, Double max) { // Floating point
values
 myPanel = new JPanel();
 nameLabel = new JLabel(name);
 minLabel = new JLabel(min.toString());
 maxLabel = new JLabel(max.toString());
 mySlider = new JSlider(JSMIN,JSMAX,(int) ((JSMAX-JSMIN)*(dValue/(max-min))));
 sReadout = new JLabel(dValue.toString());
 sReadout.setForeground(Color.blue);
 myPanel.setLayout(new TableLayout(1,5));
 myPanel.add(nameLabel,"width=100");
 myPanel.add(minLabel,"width=25");
 myPanel.add(mySlider,"width=100");
 myPanel.add(maxLabel,"width=100");
 myPanel.add(sReadout,"width=80");
 fmin=min;
 fmax=max;
 }
public sliderBox(String name, Color dValue) { // Color chooser
 myPanel = new JPanel();
 nameLabel = new JLabel(name);
 mySlider = new JSlider(JSMIN,Clut.length,Clut.length/2);
 colorDisp = new JLabel("COLOR");
 colorDisp.setForeground(dValue);
 myPanel.setLayout(new TableLayout(1,5));
 myPanel.add(nameLabel,"width=100");
 myPanel.add(new JLabel(" "),"width=25");
 myPanel.add(mySlider,"width=100");
 myPanel.add(new JLabel(" "),"width=100");
 myPanel.add(colorDisp,"width=80");
 }
/**
 * Accessor methods to read back slider values as reals and integers.
 */
public int getISlider() { // Get value at slider - int
 return mySlider.getValue();
 }
void setISlider(Integer arg) {
 sReadout.setText(arg.toString()); // Set value on readout - int
 }
public double getFSlider() { // Get slider and do linear
interplation
 double scale = ((double)mySlider.getValue())/JSMAX; // to get floating point
 return fmin+scale*(fmax-fmin);
 }
void setFSlider(Double arg) { // Set value on readout - double
 sReadout.setText(arg.toString());
 }
public Color getCSlider() { // Get color slider
 return Clut[mySlider.getValue()];
 }
void setCSlider(Color arg) { // Set color slider
 colorDisp.setForeground(arg);
 }
/**
 * Instance Variables for this class
 *
 */
 JPanel myPanel;
 JLabel nameLabel;
 JLabel minLabel;
 JLabel maxLabel;
 JLabel sReadout;
 JSlider mySlider;
 JLabel colorDisp;
int imin;
int imax;
double fmin;
double fmax;
 Color Clut[] = {Color.red, Color.green, Color.blue, Color.magenta, Color.cyan, Color.yellow,
 Color.black, Color.white, Color.gray, Color.darkGray, Color.lightGray,
 Color.orange, Color.pink
 };
/**
 * Parameters
 */
private static final int JSMIN=1;
private static final int JSMAX=100;
}
