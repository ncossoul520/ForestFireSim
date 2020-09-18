public class MonteCarloOutput {

    private static Simulator sim;

    public static void main(String[] args) {
        sim = new Simulator(100, 100);

        for (int i = 0; i < 10; i++) {
            sim.initialize(50);
            sim.setFire();

            do {
                sim.runOneStep();
            } while ( !sim.isOver() );

            sim.displayStats(); // TODO: instead, get the stats and do average
        }
    }
}
