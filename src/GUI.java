import processing.core.*;

public class GUI extends PApplet {
    Simulator sim;
    DisplayWindow gridDisplay;

    public void settings() {
        size(640, 550); // set the size of the screen.
    }

    public void setup() {
        // Create a simulator
        sim = new Simulator(100, 100);

        // Create the display
        // parameters: (10,10) is upper left of display
        // (620, 530) is the width and height
        gridDisplay = new DisplayWindow(this, 10, 10, 620, 530);

        gridDisplay.setNumCols(sim.getWidth());		// NOTE:  these must match your simulator!!
        gridDisplay.setNumRows(sim.getHeight());

        // Set different grid values to different colors
        // TODO:  uncomment these lines!
        // display.setColor(Simulator.ON_FIRE, color(255, 0, 0));
        // display.setColor(Simulator.ASH, color(180, 180, 180));
        // display.setColor(Simulator.LIVING, color(0, 0, 255));
        // display.setColor(Simulator.EMPTY, color(255, 255, 255));

    }

    @Override
    public void draw() {
        background(200);

        // TODO: have your simulation run one step.

        gridDisplay.drawGrid(sim.getDisplayGrid()); // display the game
    }

    public static void main(String[] args) {
        PApplet.main("GUI");
    }
}