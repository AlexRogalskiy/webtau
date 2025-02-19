/*
 * Copyright 2022 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testingisdocumenting.webtau.expectation.equality.handlers;

import org.testingisdocumenting.webtau.data.table.TableData;
import org.testingisdocumenting.webtau.data.table.comparison.TableDataComparison;
import org.testingisdocumenting.webtau.data.table.comparison.TableDataComparisonReport;
import org.testingisdocumenting.webtau.data.table.comparison.TableDataComparisonResult;
import org.testingisdocumenting.webtau.expectation.ActualPath;
import org.testingisdocumenting.webtau.expectation.equality.CompareToComparator;
import org.testingisdocumenting.webtau.expectation.equality.CompareToHandler;

public class TableDataCompareToHandler implements CompareToHandler {
    @Override
    public boolean handleEquality(Object actual, Object expected) {
        return actual instanceof TableData && expected instanceof TableData;
    }

    @Override
    public void compareEqualOnly(CompareToComparator comparator, ActualPath actualPath, Object actual, Object expected) {
        TableDataComparisonResult result = TableDataComparison.compare((TableData) actual, (TableData) expected);
        if (!result.areEqual()) {
            comparator.reportNotEqual(this, actualPath, new TableDataComparisonReport(result).generate());
        }
    }
}
