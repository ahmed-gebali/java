package de.benjaminborbe.analytics.util;

import java.util.Calendar;
import java.util.Collection;

import javax.inject.Inject;

import de.benjaminborbe.analytics.api.AnalyticsReportValue;
import de.benjaminborbe.analytics.api.AnalyticsReportValueDto;

public class AnalyticsAggregatorCalculatorMin implements AnalyticsAggregatorCalculator {

	@Inject
	public AnalyticsAggregatorCalculatorMin() {
	}

	@Override
	public AnalyticsReportValue aggregate(final Calendar calendar, final Collection<AnalyticsReportValue> values) {
		Double value = null;
		for (final AnalyticsReportValue e : values) {
			if (value == null || value > e.getValue()) {
				value = e.getValue();
			}
		}
		return new AnalyticsReportValueDto(calendar, value, 1l);
	}
}