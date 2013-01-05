package de.benjaminborbe.distributed.search.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.slf4j.Logger;

import de.benjaminborbe.tools.validation.ValidationConstraintValidator;

public class DistributedSearchPageValidatorUnitTest {

	@Test
	public void testValidateInactive() throws Exception {
		final Logger logger = EasyMock.createNiceMock(Logger.class);
		EasyMock.replay(logger);

		final ValidationConstraintValidator validationConstraintValidator = new ValidationConstraintValidator();

		final DistributedSearchPageValidator va = new DistributedSearchPageValidator(validationConstraintValidator);

		{
			final DistributedSearchPageBean bean = new DistributedSearchPageBean();
			bean.setIndex("bla");
			assertThat(va.validate(bean).size(), is(0));
		}
		{
			final DistributedSearchPageBean bean = new DistributedSearchPageBean();
			bean.setIndex("Bla");
			assertThat(va.validate(bean).size(), is(0));
		}
		{
			final DistributedSearchPageBean bean = new DistributedSearchPageBean();
			bean.setIndex("foo-bar");
			assertThat(va.validate(bean).size(), is(0));
		}
		{
			final DistributedSearchPageBean bean = new DistributedSearchPageBean();
			bean.setIndex("foo_bar");
			assertThat(va.validate(bean).size(), is(1));
		}
		{
			final DistributedSearchPageBean bean = new DistributedSearchPageBean();
			bean.setIndex("l33d");
			assertThat(va.validate(bean).size(), is(1));
		}
	}

}