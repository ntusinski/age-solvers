/**
 * Copyright (C) 2006 - 2012
 *   Pawel Kedzior
 *   Tomasz Kmiecik
 *   Kamil Pietak
 *   Krzysztof Sikora
 *   Adam Wos
 *   Lukasz Faber
 *   Daniel Krzywicki
 *   and other students of AGH University of Science and Technology.
 *
 * This file is part of AgE.
 *
 * AgE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AgE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AgE.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created: 2011-10-20
 * $Id: AbstractStochasticMutate.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.comma.op.mutation;

import org.jage.algorithms.comma.op.evaluate.SequentialPopulationEvaluator;
import org.jage.random.IDoubleRandomGenerator;
import org.jage.random.IIntRandomGenerator;
import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.mutation.IMutateSolution;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * Abstract implementation of {@link IMutateSolution}. Features are not mutated independently. Instead, a random subset
 * of features is first selected (the size of this subset is proportional to the chance to mutate). Then these features
 * are mutated.<br />
 * <br />
 * Concrete subclasses are supposed to provide the actual mutation computation.
 *
 * @param <R> the representation type of the solution to be mutated
 * @author AGH AgE Team
 */
public abstract class AbstractStochasticMutate<R> extends AbstractStrategy implements IMutateSolution<IVectorSolution<R>>
{

   private static final double DEFAULT_CHANCE_TO_MUTATE = 1.0;

   private double chanceToMutate = DEFAULT_CHANCE_TO_MUTATE;

   private final int steps;

   private final int populationSize;

   private int[] distances;

   @Inject
   private IDoubleRandomGenerator doubleRand;

   @Inject
   private IIntRandomGenerator intRand;

   private Random random = new Random();

   protected AbstractStochasticMutate (int steps, int populationSize)
   {
      this.steps = steps;
      this.populationSize = populationSize;

      int size = populationSize / 2;
      distances = new int[size];
      for (int i = 0; i < size; i++)
      {
         distances[i] = i + 1;
      }
   }

   @Override
   public final void mutateSolution (final IVectorSolution<R> solution)
   {
      final List<R> representation = solution.getRepresentation();
      final int size = representation.size();

      int mutatedBitsCount = (int) (chanceToMutate * size);
      final double chanceForExtraBit = chanceToMutate * size - mutatedBitsCount;
      final int extraBit = (doubleRand.nextDouble() < chanceForExtraBit) ? 1 : 0;
      mutatedBitsCount += extraBit;

      final boolean[] alreadyChecked = new boolean[size];
      for (int i = 0; i < mutatedBitsCount; i++)
      {
         int k = intRand.nextInt(size);
         while (alreadyChecked[k])
         {
            k = intRand.nextInt(size);
         }
         alreadyChecked[k] = true;
         doMutate(representation, k, calculateRange(solution));
      }
   }

   private int calculateRange (final IVectorSolution<R> solution)
   {
      int r = SequentialPopulationEvaluator.getInstance().getRank(solution);
      double rate = 1.0 - ((double) SequentialPopulationEvaluator.getEpoch() / (double) steps);
      double rMinus = ((double) (r * distances.length)) / ((double) populationSize) - rate * ((double) populationSize);
      if (rMinus < 0.0)
      {
         rMinus = 0.0;
      }
      double rPlus = ((double) (r * distances.length)) / ((double) populationSize) + rate * ((double) populationSize);
      if (rPlus > populationSize - 1)
      {
         rPlus = populationSize - 1;
      }
      int modulo = ((int) rPlus) - ((int) rMinus);
      int rPrim = modulo == 0 ? 0 : (Math.abs(random.nextInt()) % modulo) + ((int) rMinus);
      return rPrim;
   }

   /**
    * Mutate the representation at the given index. <br />
    * <br />
    * This method purpose is to allow efficient unboxing in case of representations of primitives. Subclasses can then
    * cast the given representation in the corresponding fastutil collection.
    *
    * @param representation the representation to be mutated
    * @param index          the index at which mutation should occur
    */
   protected abstract void doMutate (List<R> representation, int index, int range);
}