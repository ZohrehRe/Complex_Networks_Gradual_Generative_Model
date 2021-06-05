package GA;

import graph.GraphWorks;
import org.jgrapht.VertexFactory;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.ClassBasedVertexFactory;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by Zohreh on 5/28/2017.
 */
public class GA_Algorithm {
    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.07;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;

    static GraphWorks graphWorks = new GraphWorks();
    static VertexFactory<Object> vFactory = new ClassBasedVertexFactory<Object>(Object.class);
    static CompleteGraphGenerator<Object , DefaultEdge> cmGenerator = new CompleteGraphGenerator<Object, DefaultEdge>(4);

    /* Public methods */

    // Evolve a population
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        System.out.println("-----------------------------------------------------------generation crossover---------------------------------------------------------------------");
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        System.out.println("-----------------------------------------------------------generation mutation---------------------------------------------------------------------");
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual(vFactory, cmGenerator, graphWorks);
        for (int i = 0; i < indiv1.size(); i++) {
            newSol.setGene(i, (indiv1.getGene(i) + indiv2.getGene(i))/2);
        }
        for (int i = 0; i < indiv1.getModelParamGenes().length; i++) {
            newSol.setModelParamGene(i, (indiv1.getModelParamGene(i) + indiv2.getModelParamGene(i))/2);
        }
        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through genes
        for (int i = 0; i < indiv.size(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                double gene = Math.random();
                indiv.setGene(i, gene);
            }
        }

        for (int i = 0; i < indiv.getModelParamGenes().length; i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                double gene = Math.random();
                indiv.setModelParamGene(i, gene);
            }
        }
    }

    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        /*for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            if (pop.getIndividual(randomId).getFitness()> 1.5)
                tournament.saveIndividual(i, pop.getIndividual(randomId));
        }*/
        int i = 0;
        while (i < tournamentSize){
            int randomId = (int) (Math.random() * pop.size());
            if (pop.getIndividual(randomId).getFitness()> 1.5) {
                tournament.saveIndividual(i, pop.getIndividual(randomId));
                i++;
            }
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}
