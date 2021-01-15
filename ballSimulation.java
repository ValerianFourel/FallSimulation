package daily;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


public class ballSimulation  extends GraphicsProgram implements ChangeListener, ItemListener {
	
	// Parameters used in this program
	private static final int WIDTH = 1400; // n.b. screen coordinates
	 private static final int HEIGHT = 600;
	 private static final int OFFSET = 200;
	 private static final int NUMBALLS = 15; // # balls to simulate
	 private static final double MINSIZE = 1; // Minumum ball size
	 private static final double MAXSIZE = 8; // Maximum ball size
	 private static final double XMIN = 10; // Min X starting location
	 private static final double XMAX = 50; // Max X starting location
	 private static final double YMIN = 50; // Min Y starting location
	 private static final double YMAX = 100; // Max Y starting location
	 private static final double EMIN = 0.2; // Minimum loss coefficient
	 private static final double EMAX = 1.0; // Maximum loss coefficient
	 private static final double VMIN = 1.0; // Minimum X velocity
	 private static final double VMAX = 5.0; // Maximum Y velocity

	 public static void main(String[] args) { // Standalone Applet
	 new ballSimulation().start(args);
	 }

	public void init() {
	 this.resize(WIDTH,HEIGHT+OFFSET); // optional, initialize window size

	// Create the ground plane

	 GRect gPlane = new GRect(0,HEIGHT,WIDTH,3);
	 gPlane.setColor(Color.BLACK);
	 gPlane.setFilled(true);
	 add(gPlane);
	// Set up random number generator & B-Tree
	 rgen = RandomGenerator.getInstance();
	// Set up user interface
	 setSliders(); // Parameter sliders
	 setChoosers(); // Pull-down menus
	// Listeners
	 addActionListeners(); // Slider events
	 addMouseListeners(); // Mouse events
	 addJSliderListeners(); // Add change listener for JSliders
	 addJComboListeners(); // Add item item change listners for JComboBox

	/**
	 * Main simulation loop
	 */

	 while(true) {
	 pause(200);
	 if (SimRunning) {
	 runSimulation();
	 }
	 }
	 }

	/**
	 * Simulation is placed in its own method
	 */
	void runSimulation() {

	 myTree = new bTree();
	// Generate a series of random gballs and let the simulation run till completion

	 for (int i=0; i<PS_NumBalls; i++) {
	 double Xi = rgen.nextDouble(PS_XMin,PS_XMax); // Current Xi
	 double Yi = rgen.nextDouble(PS_YMin,PS_YMax); // Current Yi
	 double iSize = rgen.nextDouble(PS_MinSize,PS_MaxSize); // Current size
	 Color iColor = rgen.nextColor(); // Current color
	 double iLoss = rgen.nextDouble(PS_EMin,PS_EMax); // Current loss coefficient
	 double iVel = rgen.nextDouble(PS_VMin,PS_VMax); // Current X velocity

	 gBall iBall = new gBall(Xi,Yi,iSize,iColor,iLoss,iVel); // Generate instance
	 add(iBall.myBall); // Add to display list
	 myTree.addNode(iBall); // Save instance
	 iBall.start(); // Start this instance
	 }

	// Wait until simulation stops
	 while (myTree.isRunning()); // Block until simulation terminates

	// Draw balls in sort order/

	 GLabel myLabel = new GLabel("Click mouse to continue");
	 myLabel.setLocation(WIDTH-myLabel.getWidth()-500,HEIGHT-myLabel.getHeight());
	 myLabel.setColor(Color.RED);
	 add(myLabel);
	 waitForClick();
	 myTree.moveSort(); // Lay out balls from left to right in size order
	 myLabel.setLabel("");
	 SimRunning=false; // Halt the simulator
	 }
	/**
	 * Method to set up sliders for simulation parameters. Uses the sliderBox class
	 * with the TableLayout manager to create a single JPanel that can be added
	 * to the simple window manager used in GraphicsProgram.
	 *
	 */
	void setSliders() {
	//
	// Best done using the Table Layout Manager inside of a JPanel which
	// subsequently gets added to the right side of the screen.
	//
	// First layout the general simulation parameters
	//
	 JPanel myPanel = new JPanel();
	 myPanel.setLayout(new TableLayout(30, 1));
	 myPanel.add(new JLabel("General Simulation Parameters"));
	 myPanel.add(new JLabel(" "));
	 NumBalls = new sliderBox("NUMBALLS: ",1,NUMBALLS,25); // Number of balls
	 myPanel.add(NumBalls.myPanel);
	 MinSize = new sliderBox("MIN SIZE: ",1.0,MINSIZE,25.0); // Minimum ball size
	 myPanel.add(MinSize.myPanel);
	 MaxSize = new sliderBox("MAX SIZE: ",1.0,MAXSIZE,25.0); // Maximum ball size
	 myPanel.add(MaxSize.myPanel);
	 XMin = new sliderBox("X MIN: ",1.0,XMIN,200.0); // X min
	 myPanel.add(XMin.myPanel);
	 XMax = new sliderBox("X MAX: ",1.0,XMAX,200.0); // X max
	 myPanel.add(XMax.myPanel);
	 YMin = new sliderBox("Y MIN: ",1.0,YMIN,100.0); // Y min
	 myPanel.add(YMin.myPanel);
	 YMax = new sliderBox("Y MAX: ",1.0,YMAX,100.0); // Y max
	 myPanel.add(YMax.myPanel);
	 EMin = new sliderBox("LOSS MIN: ",0.0,0.4,1.0); // Minimum energy loss
	 myPanel.add(EMin.myPanel);
	 EMax = new sliderBox("LOSS MAX: ",0.0,EMAX,1.0); // Maximum energy loss
	 myPanel.add(EMax.myPanel);
	 VMin = new sliderBox("X VEL MIN: ",0.0,VMIN,10.0); // Minimum X velocity
	 myPanel.add(VMin.myPanel);
	 VMax = new sliderBox("X VEL MAX: ",0.0,VMAX,10.0); // Number of balls
	 myPanel.add(VMax.myPanel);
	//
	// And now the ball instance parameters
	//
	 myPanel.add(new JLabel(" "));
	 myPanel.add(new JLabel("Single Ball Instance Parameters"));
	 myPanel.add(new JLabel(" "));
	 CChooser = new sliderBox("Color: ",Color.red);
	 myPanel.add(CChooser.myPanel);
	 BSize = new sliderBox("Ball Size:",MINSIZE,PI_BSize,MAXSIZE);
	 myPanel.add(BSize.myPanel);
	 ELoss = new sliderBox("E Loss:",EMIN,PI_ELoss,EMAX);
	 myPanel.add(ELoss.myPanel);
	 XVel = new sliderBox("X Vel:",VMIN,PI_XVel,VMAX);
	 myPanel.add(XVel.myPanel);
	 add(myPanel,EAST);
	 }
	/**
	 * Method to set up chooser-like pull down menus across the top
	 * of the display.
	 */
	void setChoosers() {
	 ballSimulationC = new JComboBox<String>();
	 ballSimulationC.addItem("ballSimulation");
	 ballSimulationC.addItem("Run");
	 ballSimulationC.addItem("Clear");
	 ballSimulationC.addItem("Stop");
	 ballSimulationC.addItem("Quit");
	 add(ballSimulationC,NORTH);
	 FileC = new JComboBox<String>();
	 FileC.addItem("File");
	 add(FileC,NORTH);
	 EditC = new JComboBox<String>();
	 EditC.addItem("Edit");
	 add(EditC,NORTH);
	 HelpC = new JComboBox<String>();
	 HelpC.addItem("Help");
	 add(HelpC,NORTH);
	 }
	/**
	 * Method to set up change listener for each slider box
	 */
	void addJSliderListeners() {
	 NumBalls.mySlider.addChangeListener((ChangeListener) this);
	 MinSize.mySlider.addChangeListener((ChangeListener) this);
	 MaxSize.mySlider.addChangeListener((ChangeListener) this);
	 XMin.mySlider.addChangeListener((ChangeListener) this);
	 XMax.mySlider.addChangeListener((ChangeListener) this);
	 YMin.mySlider.addChangeListener((ChangeListener) this);
	 YMax.mySlider.addChangeListener((ChangeListener) this);
	 EMin.mySlider.addChangeListener((ChangeListener) this);
	 EMax.mySlider.addChangeListener((ChangeListener) this);
	 VMin.mySlider.addChangeListener((ChangeListener) this);
	 VMax.mySlider.addChangeListener((ChangeListener) this);
	 CChooser.mySlider.addChangeListener((ChangeListener) this);
	 BSize.mySlider.addChangeListener((ChangeListener) this);
	 ELoss.mySlider.addChangeListener((ChangeListener) this);
	 XVel.mySlider.addChangeListener((ChangeListener) this);
	 }
	/**
	 * Method to set up item change listeners for JComboBoxes
	 */
	void addJComboListeners() {
	 ballSimulationC.addItemListener((ItemListener)this);
	 FileC.addItemListener((ItemListener)this);
	 EditC.addItemListener((ItemListener)this);
	 HelpC.addItemListener((ItemListener)this);
	 }
	/**
	 * Method to handle dispatch for JComboBox
	 */
	public void itemStateChanged(ItemEvent e) {
	 JComboBox source = (JComboBox)e.getSource();

	 if (source==ballSimulationC) { // Only ballSimulation active at the moment
	 if (ballSimulationC.getSelectedIndex()==1) {
	 System.out.println("Starting simulation");
	 this.SimRunning=true;
	 }
	 else if (ballSimulationC.getSelectedIndex()==2) {
	 System.out.println("Clearing simulation");
	 myTree.clearBalls(this);
	 }
	 else if (ballSimulationC.getSelectedIndex()==3) {
	 System.out.println("Stopping simulation");
	 this.SimRunning=false;
	 }
	 else if (ballSimulationC.getSelectedIndex()==4) {
	 System.out.println("Shutting down");
	 System.exit(0);
	 }
	 }
	 }
	/**
	 * Method to handle dispach for sliders
	 */

	public void stateChanged(ChangeEvent e) {
	 JSlider source = (JSlider)e.getSource();

	 if (source==NumBalls.mySlider) { // Global parameter sliders
	 PS_NumBalls=NumBalls.getISlider();
	 NumBalls.setISlider(PS_NumBalls);
	 }
	 else if (source==MinSize.mySlider) {
	 PS_MinSize=MinSize.getFSlider();
	 MinSize.setFSlider(PS_MinSize);
	 }
	 else if (source==MaxSize.mySlider) {
	 PS_MaxSize=MaxSize.getFSlider();
	 MaxSize.setFSlider(PS_MaxSize);
	 }
	 else if (source==XMin.mySlider) {
	 PS_XMin=XMin.getFSlider();
	 XMin.setFSlider(PS_XMin);
	 }
	 else if (source==XMax.mySlider) {
	 PS_XMax=XMax.getFSlider();
	 XMax.setFSlider(PS_XMax);
	 }
	 else if (source==YMin.mySlider) {
	 PS_YMin=YMin.getFSlider();
	 YMin.setFSlider(PS_YMin);
	 }
	 else if (source==YMax.mySlider) {
	 PS_YMax=YMax.getFSlider();
	 YMax.setFSlider(PS_YMax);
	 }
	 else if (source==EMin.mySlider) {
	 PS_EMin=EMin.getFSlider();
	 EMin.setFSlider(PS_EMin);
	 }
	 else if (source==EMax.mySlider) {
	 PS_EMax=EMax.getFSlider();
	 EMax.setFSlider(PS_EMax);
	 }
	 else if (source==VMin.mySlider) {
	 PS_VMin=VMin.getFSlider();
	 VMin.setFSlider(PS_VMin);
	 }
	 else if (source==VMax.mySlider) {
	 PS_VMax=VMax.getFSlider();
	 VMax.setFSlider(PS_VMax);
	 }
	 else if (source==CChooser.mySlider) { // Instance parameter settings
	 PI_Color=CChooser.getCSlider();
	 CChooser.setCSlider(PI_Color);
	 }
	 else if (source==BSize.mySlider) {
	 PI_BSize=BSize.getFSlider();
	 BSize.setFSlider(PI_BSize);
	 }
	 else if (source==ELoss.mySlider) {
	 PI_ELoss=ELoss.getFSlider();
	 ELoss.setFSlider(PI_ELoss);
	 }
	 else if (source==XVel.mySlider) {
	 PI_XVel=XVel.getFSlider();
	 XVel.setFSlider(PI_XVel);
	 }
	 }
	/**
	 * Methods to handle mouse events
	 */
	public void mousePressed(MouseEvent e) {
	 last = new GPoint(e.getPoint());
	 obj = getElementAt(last);
	 match=null;
	 if (obj != null) {
	 match = myTree.nSearch((GOval) obj);
	 if (match != null) {
	 match.bState=false; // Kill thread
	 }
	 }
	 }
	public void mouseDragged(MouseEvent e) {
	 if (match != null) {
	 match.myBall.move(e.getX() - last.getX(), e.getY() - last.getY());
	 last=new GPoint(e.getPoint());
	 }
	 }
	public void mouseReleased(MouseEvent e) {
	 if (match!=null) {
	 updateSimParams((int)e.getX(),(int)e.getY());
	 }
	 }
	/**
	 * Method to update simulation parameters
	 */
	void updateSimParams(int X, int Y) {
	 double Xt=gUtil.ScreentoX(X)+match.bSize;
	 double Yt=gUtil.ScreentoY(Y)-match.bSize;

	 // Substitute a new simulation instance
	 gBall repl = new gBall(Xt,Yt,PI_BSize,PI_Color,PI_ELoss,PI_XVel); // New sim
	 repl.myBall=match.myBall; // Attach to GOval
	 myTree.replace(match,repl); // Substitute in tree
	 match=repl;
	 myTree.reorder(); // Reorder tree

	 // Update the screen display

	 match.myBall.setFillColor(PI_Color);
	 match.myBall.setSize(gUtil.LtoScreen(2*PI_BSize),gUtil.LtoScreen(2*PI_BSize));
	 match.myBall.setLocation(gUtil.XtoScreen(Xt-match.bSize),gUtil.YtoScreen(Yt+match.bSize));
	 match.start();
	 }
	/**
	 * Instance variables - simulation parameters
	 */
	private int PS_NumBalls=NUMBALLS; // Global simulation parameters for the rgen
	private double PS_MinSize=MINSIZE;
	private double PS_MaxSize=MAXSIZE;
	private double PS_XMin=XMIN;
	private double PS_XMax=XMAX;
	private double PS_YMin=YMIN;
	private double PS_YMax=YMAX;
	private double PS_EMin=EMIN;
	private double PS_EMax=EMAX;
	private double PS_VMin=VMIN;
	private double PS_VMax=VMAX;
	private Color PI_Color=Color.red;
	private double PI_BSize=4;
	private double PI_ELoss=0.4;
	private double PI_XVel=VMIN;
	/**
	 * Instance variables - slider boxes
	 *
	 */
	private sliderBox NumBalls;
	private sliderBox MinSize;
	private sliderBox MaxSize;
	private sliderBox XMin;
	private sliderBox XMax;
	private sliderBox YMin;
	private sliderBox YMax;
	private sliderBox EMin;
	private sliderBox EMax;
	private sliderBox VMin;
	private sliderBox VMax;
	private sliderBox CChooser;
	private sliderBox BSize;
	private sliderBox ELoss;
	private sliderBox XVel;
	/**
	 * Instance variables - JCombo boxes
	 *
	 */
	 private JComboBox<String> ballSimulationC;
	 private JComboBox<String> FileC;
	 private JComboBox<String> EditC;
	 private JComboBox<String> HelpC;

	/**
	 * Instance variables - simulation
	 */
	RandomGenerator rgen;
	bTree myTree;
	boolean SimRunning=false;
	GObject obj;
	gBall match;
	GPoint last;
	
}
