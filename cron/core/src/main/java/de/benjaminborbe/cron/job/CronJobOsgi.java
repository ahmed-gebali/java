package de.benjaminborbe.cron.job;

import de.benjaminborbe.cron.CronConstants;
import de.benjaminborbe.cron.util.CronMessageSender;
import de.benjaminborbe.tools.date.DateUtil;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
@DisallowConcurrentExecution
public class CronJobOsgi implements Job {

	private final Logger logger;

	private final DateUtil dateUtil;

	private final CronMessageSender cronMessageSender;

	@Inject
	public CronJobOsgi(final Logger logger, final DateUtil dateUtil, final CronMessageSender cronMessageSender) {
		this.logger = logger;
		this.dateUtil = dateUtil;
		this.cronMessageSender = cronMessageSender;
	}

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		final Date fireTime = context.getFireTime();
		final String name = (String) context.getJobDetail().getJobDataMap().get(CronConstants.JOB_NAME);
		try {
			logger.trace("execute " + name + " at " + dateUtil.dateTimeString(fireTime));
			cronMessageSender.send(name);
		} catch (final Exception e) {
			logger.info("execute - failed job: " + name + " exception: " + e.getClass().getSimpleName(), e);
		}
	}
}
