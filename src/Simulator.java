
public class Simulator {

    /***
     * Create a new simulator.  The simulator creates a new Forest of size (r, c).
     *
     * @param r
     * @param c
     */

    private static int[][] forest ;
    private static int row, col;
    public final static int EMPTY_SPACE  = 0; // must be zero for proper initialization
    public final static int LIVING_TREE  = 1;
    public final static int BURNING_TREE = 2;
    public final static int ASH          = 3;
    private final static int DENSITY = 75;

    public Simulator(int r, int c) {
        row = r;
        col = c;
        forest = new int[row][col];
        initialize(DENSITY);
    }

    // TODO: add methods outlines in assignment sheet
    // * way to get statistical information about the current state of the simulation
    // * way to run simulation for one step
    // * way to do a "full run" of running until fires are burned out
    // * way to (re-)initialize your forest with particular tree density
    //

    public void initialize(int n){
        // TODO clean up code
        int density = (n * row * col) / 100;
        int count = 0;

        do {
            int tempRow= (int)(Math.random()*row);
            int tempCol= (int)(Math.random()*col);
            if (forest[tempRow][tempCol] == EMPTY_SPACE){
                count++;
                forest[tempRow][tempCol] = LIVING_TREE;
            }
        } while(count < density);
    }

    private int runOneStep(){
        int count = 0;
        int[][] nextStep = forest;
        for (int r = 0; r < nextStep.length ; r++) {
            for (int c = 0; c < nextStep[0].length ; c++) {
                if(nextToFire(r, c)){
                    nextStep[r][c] = BURNING_TREE;
                    count++;
                }
            }
        }

        forest = nextStep;
        return count;
    }

    public boolean nextToFire(int r, int c) {
        for (int dr = -1; dr <= 1 ; dr++) {
            for (int dc = -1; dc <= 1 ; dc++) {
                if( isInBound(r+dr, c+dc) ) {
                    if ( forest[r+dr][c+dc] == BURNING_TREE ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isInBound(int r, int c) {
        return (r >= 0 && r < row && c >=0 && c < col);
    }


    public int getWidth() {
        return col;
    }

    public int getHeight() {
        return row;
    }

    public int[][] getDisplayGrid() {
        return forest;
    }
}
