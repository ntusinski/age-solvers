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

package org.jage.algorithms.commons.solution;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.apache.commons.lang.ArrayUtils;
import org.jage.problem.IVectorProblem;
import org.jage.property.ClassPropertyContainer;
import org.jage.random.IIntRandomGenerator;
import org.jage.solution.ISolutionFactory;
import org.jage.solution.IVectorSolution;
import org.jage.solution.VectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolutionFactory extends ClassPropertyContainer implements
        ISolutionFactory<IVectorSolution<Integer>> {

    private static final Logger LOG = LoggerFactory.getLogger(SolutionFactory.class);

    int count = 0;

    @Inject
    private IIntRandomGenerator rand;

    @Inject
    private IVectorProblem<Integer> problem;

    @Override
    public IVectorSolution<Integer> createEmptySolution() {
        final int[] representation = new int[problem.getDimension()];
        return new VectorSolution<Integer>(new FastIntArrayList(representation));
    }

    @Override
    public IVectorSolution<Integer> createInitializedSolution() {
        count++;
        LOG.debug("count: " + count);

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < problem.getDimension(); i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        LOG.debug("Initialized solution: " + list);

        return new VectorSolution<Integer>(new FastIntArrayList(ArrayUtils.toPrimitive(list.toArray(new Integer[problem.getDimension()]))));
    }

    @Override
    public IVectorSolution<Integer> copySolution(final IVectorSolution<Integer> solution) {
        //throw new RuntimeException("mutacja ma byc in situ");
        //return solution;
        final IntArrayList representation = (IntArrayList) solution.getRepresentation();
        return new VectorSolution<Integer>(new FastIntArrayList(representation));
    }

    /**
     * Helper class with faster equals and compareTo methods.
     *
     * @author AGH AgE Team
     */
    private static class FastIntArrayList extends IntArrayList {

        private static final long serialVersionUID = -2132234650006853053L;

        public FastIntArrayList(final int[] representation) {
            super(representation);
        }

        public FastIntArrayList(final IntArrayList representation) {
            super(representation);
        }

        @Override
        public boolean equals(final Object o) {
            if (o instanceof IntArrayList) {
                return super.equals(o);
            } else {
                return super.equals(o);
            }
        }

        @Override
        public int compareTo(final List<? extends Integer> l) {
            if (l instanceof IntArrayList) {
                return super.compareTo((IntArrayList) l);
            } else {
                return super.compareTo(l);
            }
        }
    }
}