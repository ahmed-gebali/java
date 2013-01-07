package de.benjaminborbe.distributed.index.mock;

import java.util.Collection;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.distributed.index.api.DistributedIndexSearchResultIterator;
import de.benjaminborbe.distributed.index.api.DistributedIndexService;
import de.benjaminborbe.distributed.index.api.DistributedIndexServiceException;

@Singleton
public class DistributedIndexServiceMock implements DistributedIndexService {

	@Inject
	public DistributedIndexServiceMock() {
	}

	@Override
	public void add(final String index, final String id, final Map<String, Integer> data) throws DistributedIndexServiceException {

	}

	@Override
	public void remove(final String index, final String id) throws DistributedIndexServiceException {
	}

	@Override
	public DistributedIndexSearchResultIterator search(final String index, final Collection<String> words) throws DistributedIndexServiceException {
		return null;
	}

	@Override
	public Map<String, Integer> getWordRatingForEntry(final String index, final String url) throws DistributedIndexServiceException {
		return null;
	}

	@Override
	public Map<String, Integer> getEntryRatingForWord(final String index, final String word) throws DistributedIndexServiceException {
		return null;
	}

}
