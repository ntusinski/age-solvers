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

import org.jage.algorithms.commons.input.InputData;
import org.jage.algorithms.commons.input.InputDataHolder;
import org.jage.problem.IVectorProblem;

import java.io.IOException;

public class Problem implements IVectorProblem<Integer> {
    private InputData inputData;

    public Problem() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    @Override
    public final int getDimension() {
        return inputData.getN();
    }

    @Override
    public final Integer lowerBound(int atDimension) {
        checkDimension(atDimension);
        return 0;
    }

    @Override
    public final Integer upperBound(int atDimension) {
        checkDimension(atDimension);
        return inputData.getN() - 1;
    }

    private void checkDimension(int atDimension) {
        if (atDimension < 0 || atDimension >= inputData.getN()) {
            throw new IllegalArgumentException("Dimension out of range");
        }
    }
}
