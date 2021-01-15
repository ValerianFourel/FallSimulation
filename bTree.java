package daily;


import acm.graphics.GOval;

public class bTree {
/**
 * addNode method - wrapper for rNode
 */
public void addNode(gBall data) {
 root=rNode(root,data);
 }
/**
 * rNode method - recursively adds a new entry into the B-Tree
 */
private bNode rNode(bNode root, gBall data) {
 if (root==null) {
 bNode node = new bNode();
 node.data = data;
 node.left = null;
 node.right = null;
 root=node;
 return root;
 }
 else if (data.bSize < root.data.bSize) {
 root.left = rNode(root.left,data);
 }
 else {
 root.right = rNode(root.right,data);
 }
 return root;
 }
/**
 * inorder method - inorder traversal via call to recursive method
 */
public void inorder() {
 traverse_inorder(root);
 }
/**
 * traverse_inorder method - recursively traverses tree in order and prints each node.
 */
private void traverse_inorder(bNode root) {
 if (root.left != null) traverse_inorder(root.left);
 System.out.println(root.data.bSize);
 if (root.right != null) traverse_inorder(root.right);
 }
/**
 * isRunning predicate - determines if simulation is still running
 */
boolean isRunning() {
 running=false;
 recScan(root);
 return running;
 }
void recScan(bNode root) {
 if (root.left != null) recScan(root.left);
 if (root.data.bState) running=true;
 if (root.right != null) recScan(root.right);
 }
/**
 * clearBalls - removes all balls from display
 * (note - you need to pass a reference to the display)
 *
 */
void clearBalls(bSim display) {
 recClear(display,root);
 }
void recClear(bSim display,bNode root) {
 if (root.left != null) recClear(display,root.left);
 display.remove (root.data.myBall);
 root.data.bState=false; // Kill controlling thread
 if (root.right != null) recClear(display,root.right);
 }
/**
 * drawSort - sorts balls by size and plots from left to right on display
 *
 */
void moveSort() {
 nextX=0;
 recMove(root);
 }
void recMove(bNode root) {
 if (root.left != null) recMove(root.left);
//
// Plot ball along baseline
//
 double X = nextX;
 double Y = root.data.bSize*2;
 nextX = X + root.data.bSize*2 + SEP;
 root.data.Xt=X;
 root.data.Yt=Y;
 root.data.moveTo(X, Y);
 if (root.right != null) recMove(root.right);
 }
/**
 * gBall nSearch - node search routine. Returns the matching gBall object
 * @param - GOval ball, GOval object within the gBall being searched for
 * @author ferrie
 *
 */
 gBall nSearch(GOval ball) {
 match=null;
 rnSearch(root,ball);
 return match;
 }
void rnSearch(bNode root,GOval ball) {
 if (root.left != null) rnSearch(root.left,ball);
 if (root.data.myBall==ball) match=root.data;
 if (root.right != null) rnSearch(root.right,ball);
 }
/**
 * Replace a node on the tree
 * @author ferrie
 */
void replace(gBall Ball, gBall rBall) {
 rreplace(root,Ball,rBall);
 }
void rreplace(bNode root, gBall Ball, gBall rBall) {
 if (root.left != null) rreplace(root.left,Ball,rBall);
 if (root.data==Ball) root.data=rBall;
 if (root.right != null) rreplace(root.right,Ball,rBall);

 }
/**
 * Method to access the root node of a bTree
 */
 bNode getRoot() {
 return root;
 }
/**
 * Method to reorder the tree in sort order
 * @author ferrie
 */
void reorder() {
 bTree copy = new bTree();
 rreorder(root,copy);
 root=copy.getRoot();
 }
void rreorder(bNode root, bTree copy)
 {
 if (root.left != null) rreorder(root.left,copy);
 copy.addNode(root.data);
 if (root.right != null) rreorder(root.right,copy);
 }
// Example of a nested class //
public class bNode {
 gBall data;
 bNode left;
 bNode right;
 }
// Instance variables
 bNode root=null;
boolean running;
double nextX;
 gBall match;
// Parameters
 private static final double SEP = 0;
}
