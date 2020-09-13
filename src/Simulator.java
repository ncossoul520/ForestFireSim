import java.util.ArrayList;

public class Simulator {

    /***
     * Create a new simulator.  The simulator creates a new Forest of size (r, c).
     *
     * @param r
     * @param c
     */

    private static int[][] forest;
    private static int row, col;
    private static ArrayList<Location> fires = new ArrayList<>();
    public final static int EMPTY_SPACE  = 0; // must be zero for proper initialization
    public final static int LIVING_TREE  = 1;
    public final static int BURNING_TREE = 2;
    public final static int ASH          = 3;
    private final static int DENSITY = 75;

    public Simulator(int r, int c) {
        row = r;
        col = c;
        forest   = new int[row][col];
        initialize(DENSITY);
        setFire();
    }

    // TODO: add methods outlines in assignment sheet
    // * way to get statistical information about the current state of the simulation
    // * way to run simulation for one step
    // * way to do a "full run" of running until fires are burned out
    // * way to (re-)initialize your forest with particular tree density
    //

    public void initialize(int n){
        int num_trees_total = (n * row * col) / 100;
        int num_trees = 0;

        do {
            int tempRow= (int)(Math.random()*row);
            int tempCol= (int)(Math.random()*col);
            if (forest[tempRow][tempCol] == EMPTY_SPACE){
                num_trees++;
                forest[tempRow][tempCol] = LIVING_TREE;
            }
        } while( num_trees < num_trees_total );
    }


    private void setFire() {
        int r, c;
        do {
            r = (int)(Math.random()*row);
            c = (int)(Math.random()*col);
        } while (forest[r][c] != LIVING_TREE);
        // TODO for testing, remove:
        r = 50;
        c = 50;
        forest[r][c] = BURNING_TREE;
    }


    public int runOneStep(){
        int[][] nextStep = new int[row][col];
        System.arraycopy(forest, 0, nextStep, 0, forest.length);
//        int[][] nextStep = forest.clone();
        int num_trees_fire = 0;

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if ( forest[r][c] == LIVING_TREE && nextToFire(r, c) ){
                    nextStep[r][c] = BURNING_TREE;
                    num_trees_fire++;
                }
            }
        }

        // TODO could this be part of the previous loops?
//        for (int r = 0; r < forest.length ; r++) {
//            for (int c = 0; c < forest[0].length; c++) {
//                if (forest[r][c] == BURNING_TREE) {
//                    nextStep[r][c] = ASH;
//                }
//            }
//        }

//        System.arraycopy(nextStep, 0, forest, 0, nextStep.length);
//        forest = nextStep.clone();
        return num_trees_fire;
    }

    public boolean nextToFire(int r, int c) {
        for (int dr = -1; dr <= 1 ; dr++) {
            for (int dc = -1; dc <= 1 ; dc++) {
                if ( isInBound(r+dr, c+dc) ) {
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
