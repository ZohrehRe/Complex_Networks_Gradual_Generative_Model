package GA;

import graph.GraphAttr;
import graph.GraphWorks;

import java.util.Scanner;

/**
 * Created by Zohreh on 5/28/2017.
 */
public class Check_GA {
    public static void main(String[] args) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~HI~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Scanner in = new Scanner(System.in);
        System.out.println("Desired assortativity:");
        float a = in.nextFloat();
        System.out.println("Desired transitivity:");
        float t = in.nextFloat();
        System.out.println("Desired clustering coefficient:");
        float cc = in.nextFloat();
        System.out.println("Desired number of nodes:");
        int n = in.nextInt();
        System.out.println("Added number nodes in each step:");
        int s = in.nextInt();
        GraphWorks graphWorks = new GraphWorks();
        // Set a candidate solution CC, T, A, M, .., .., .., ..
        double[] solution = new double[]{a , t, cc, 0, 0, 0, 0, 0};
        FitnessCalc.setSolution(solution);
        FitnessCalc.setStepN(s);
        FitnessCalc.setTotalN(n);
        Individual.setStepSize(s);

        // Create an initial population
        Population myPop = new Population(20, true);

        // Evolve our population until we reach an optimal solution
        int generationCount = 0;
        while (Math.abs(myPop.getFittest().getFitness() - FitnessCalc.getMaxFitness()) > 0.0001) {
            generationCount++;
            System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness() + " Genes E, B, W, erP, baM, wsB, wsD: " + myPop.getFittest());

            System.out.println("Metrics: " + new GraphAttr().compute(myPop.getFittest().getGraph()));
            System.out.println();
            myPop = GA_Algorithm.evolvePopulation(myPop);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~THE END~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Generation: " + generationCount);
        System.out.println("Result: Probability of Erdos, Probability of Barabasi, Probability of WattsStrogats | P for Erdos, M for Barabasi, B for WS, D for WS: "+ myPop.getFittest());
        graphWorks.printGraphAttr(myPop.getFittest().getGraph());
        System.out.println("Fittest: " + myPop.getFittest().getFitness());
        graphWorks.outPutGraph("out.txt",myPop.getFittest().getGraph() );
    }
}
