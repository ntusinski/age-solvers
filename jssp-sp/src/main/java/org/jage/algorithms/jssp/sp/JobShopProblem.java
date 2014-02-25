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
 * Created: 2011-12-16
 * $Id: InitializationActionStrategy.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.jssp.sp;

import org.jage.algorithms.jssp.sp.model.InputData;
import org.jage.problem.IVectorProblem;

import java.io.IOException;

/**
 * User: Norbert Tusiński
 * Date: 20.11.13
 * Time: 07:47
 */
public class JobShopProblem implements IVectorProblem<Integer> {
    private InputData inputData;

    public JobShopProblem() throws IOException {
        inputData = InputDataHolder.getInstance().getInputData();
    }

    @Override
    public final int getDimension() {
        return inputData.getJobsNo();
    }

    @Override
    public final Integer lowerBound(int atDimension) {
        checkDimension(atDimension);
        return 0;
    }

    @Override
    public Integer upperBound(int atDimension) {
        checkDimension(atDimension);
        return inputData.getJobsNo() - 1;
    }

    private void checkDimension(int atDimension) {
        if (atDimension < 0 || atDimension >= inputData.getJobsNo()) {
            throw new IllegalArgumentException("Dimension out of range");
        }
    }
}
