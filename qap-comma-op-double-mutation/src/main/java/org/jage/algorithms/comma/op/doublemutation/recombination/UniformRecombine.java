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
 * Created: 21-03-2012
 * $Id$
 */

package org.jage.algorithms.comma.op.doublemutation.recombination;

import java.util.*;

import javax.inject.Inject;

import org.jage.random.INormalizedDoubleRandomGenerator;
import org.jage.solution.IVectorSolution;
import org.jage.strategy.AbstractStrategy;
import org.jage.variation.recombination.IRecombine;

/**
 * Recombination strategy that uniformly swaps two solutions representation with a probability of 0.5.
 *
 * @param <R>
 *            the representation type of the solution to be recombined
 *
 * @author AGH AgE Team
 */
public class UniformRecombine<R> extends AbstractStrategy implements IRecombine<IVectorSolution<R>> {

    @Inject
    private INormalizedDoubleRandomGenerator rand;

    @Override
    public final void recombine(final IVectorSolution<R> solution1, final IVectorSolution<R> solution2) {
        final List<R> representation1 = solution1.getRepresentation();
        final List<R> representation2 = solution2.getRepresentation();

        for (int i = 0, n = representation1.size(); i < n; i++) {
            if (rand.nextDouble() <= 0.5) {
                swap(representation1, representation2, i);
            }
        }

        fixGenotype((List<Integer>) representation1);
        fixGenotype((List<Integer>) representation2);
    }

    private void fixGenotype(List<Integer> representation) {
        Set<Integer> missingValues = new HashSet<Integer>();
        for (int i = 0; i < representation.size(); i++) {
            missingValues.add(i);
        }
        for (Integer aRepresentation : representation) {
            missingValues.remove(aRepresentation);
        }
        List<Integer> missingValuesList = new ArrayList<Integer>();
        missingValuesList.addAll(missingValues);
        Collections.shuffle(missingValuesList);

        boolean[] duplicateCheckArray = new boolean[representation.size()];
        for (int i = 0; i < representation.size(); i++) {
            int gene = representation.get(i);
            if (!duplicateCheckArray[gene]) {
                duplicateCheckArray[gene] = true;
            } else {
                representation.set(i, missingValuesList.get(0));
                missingValuesList.remove(0);
            }
        }
    }

    /**
     * Swaps the representations at the given index. <br />
     * <br />
     * This method purpose is to allow efficient unboxing in case of representations of primitives. Subclasses can then
     * cast the given representation in the corresponding fastutil collection.
     *
     *
     * @param representation1
     *            the first representation
     * @param representation2
     *            the second representation
     * @param index
     *            the index at which swapping should occur
     */
    protected void swap(final List<R> representation1, final List<R> representation2, final int index) {
        final R element = representation1.get(index);
        representation1.set(index, representation2.get(index));
        representation2.set(index, element);
    }
}
