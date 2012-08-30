package de.benjaminborbe.dhl.mock;

import java.net.URL;
import java.util.Collection;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.authentication.api.SessionIdentifier;
import de.benjaminborbe.dhl.api.DhlIdentifier;
import de.benjaminborbe.dhl.api.DhlService;
import de.benjaminborbe.dhl.api.DhlServiceException;

@Singleton
public class DhlServiceMock implements DhlService {

	@Inject
	public DhlServiceMock() {
	}

	@Override
	public URL buildDhlUrl(final SessionIdentifier sessionIdentifier, final DhlIdentifier dhlIdentifier) throws DhlServiceException {
		return null;
	}

	@Override
	public DhlIdentifier createDhlIdentifier(final SessionIdentifier sessionIdentifier, final long id) throws DhlServiceException {
		return null;
	}

	@Override
	public void mailStatus(final SessionIdentifier sessionIdentifier, final DhlIdentifier dhlIdentifier) throws DhlServiceException {
	}

	@Override
	public Collection<DhlIdentifier> getRegisteredDhlIdentifiers(final SessionIdentifier sessionIdentifier) throws DhlServiceException {
		return null;
	}

	@Override
	public void removeTracking(final SessionIdentifier sessionIdentifier, final DhlIdentifier dhlIdentifier) throws DhlServiceException {
	}

	@Override
	public DhlIdentifier addTracking(final SessionIdentifier sessionIdentifier, final long trackingNumber, final long zip) throws DhlServiceException {
		return null;
	}

}
