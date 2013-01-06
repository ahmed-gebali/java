package de.benjaminborbe.distributed.index.dao;

import java.util.Map;

import de.benjaminborbe.storage.tools.EntityBase;

public class DistributedIndexEntryBean extends EntityBase<DistributedIndexEntryIdentifier> {

	private static final long serialVersionUID = -921428835583316483L;

	private Map<String, Integer> data;

	public Map<String, Integer> getData() {
		return data;
	}

	public void setData(final Map<String, Integer> data) {
		this.data = data;
	}

}
