package app.repository;

import java.util.List;

import app.model.ValueObject;

public interface JobRepositoryCustom {
	public Iterable<ValueObject> runMapReduce(String map, String reduce);

	public List<String> getDistinctKeys(String key);

}
