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

    public final static int EMPTY_SPACE  = 0; // must be zero for proper initialization
    public final static int LIVING_TREE  = 1;
    public final static int BURNING_TREE = 2;
    public final static int ASH          = 3;
    private final static int DENSITY = 50;

    private int stats_trees = 0;
    private int stats_alive = 0;
    private int stats_on_fire = 0;
    private int stats_burned = 0;

    public Simulator(int r, int c) {
        row = r;
        col = c;
        forest   = new int[row][col];
        initialize(DENSITY);
        setFire();
//        setFire(50, 50);
    }

    // TODO: add methods outlines in assignment sheet
    // * way to get statistical information about the current state of the simulation
    // * way to run simulation for one step
    // * way to do a "full run" of running until fires are burned out
    // * way to (re-)initialize your forest with particular tree density
    //

    public void initialize(int n){
        stats_trees = (n * row * col) / 100;
        stats_alive = stats_trees;
        int num_trees = 0;

        do {
            int tempRow= (int)(Math.random()*row);
            int tempCol= (int)(Math.random()*col);
            if ( forest[tempRow][tempCol] == EMPTY_SPACE ){
                num_trees++;
                forest[tempRow][tempCol] = LIVING_TREE;
            }
        } while( num_trees < stats_trees);
    }


    private void setFire() {
        int r, c;
        do {
            r = (int)(Math.random()*row);
            c = (int)(Math.random()*col);
        } while (forest[r][c] != LIVING_TREE);
        forest[r][c] = BURNING_TREE;
        stats_alive--;
    }

    private void setFire(int r, int c) {
        forest[r][c] = BURNING_TREE;
    }


    public void runOneStep() {
        ArrayList<Location> new_fires = new ArrayList<>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (forest[r][c] == BURNING_TREE) {
                    // Set on fire each of the neighbors
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if ( isInBound(r + dr, c + dc) ) {
                                if ( forest[r + dr][c + dc] == LIVING_TREE ) {
                                    Location loc = new Location(r+dr, c+dc);
                                    if ( !new_fires.contains( loc ) ) {
                                        new_fires.add(new Location(r + dr, c + dc));
                                    }
                                }
                            }
                        }
                    }
                    forest[r][c] = ASH;
                    stats_burned++;
                }
            }
        }

        stats_on_fire = new_fires.size();
        stats_alive -= stats_on_fire;
        for ( Location loc : new_fires ) {
            forest[ loc.getRow() ][ loc.getCol() ] = BURNING_TREE;
        }
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

    public void displayStats() {
        System.out.println( "trees: " + stats_trees + "\talive: " + stats_alive + "\tburned: " + stats_burned + "\ton fire: " + stats_on_fire );
    }

    public boolean isOver() {
        return stats_on_fire == 0;
    }
}
