import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.Factory;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParameterOptimization {
public static int iteration = 0;

    private static double eval(Genotype<DoubleGene> gt) {
        iteration++;
        System.out.println("Iteration: " + iteration);

        ParameterCandidate candidate = new ParameterCandidate(gt.get(0).get(0).doubleValue(), gt.get(1).gene().doubleValue(), gt.get(2).gene().doubleValue(), gt.get(3).gene().doubleValue(), gt.get(4).gene().doubleValue(), gt.get(5).gene().doubleValue(), gt.get(6).gene().doubleValue());
        return 1000000/EvalFunctionBenchmarkTest.EvalFuncScore(candidate);                                //we want to minimize the score but jenetics maximizes the eval value -> inverse
    }


    public static void main(String[] args) {
        Factory<Genotype<DoubleGene>> gtf =
                Genotype.of(DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10), DoubleChromosome.of(0.0, 10));


        Engine<DoubleGene, Double> engine  = Engine
                .builder(ParameterOptimization::eval, gtf)
                .build();

        Genotype<DoubleGene> result = engine.stream()
                .limit(30)
                .collect(EvolutionResult.toBestGenotype());

        // Print the best solution
        System.out.println("Best solution: " + result);
    }
}