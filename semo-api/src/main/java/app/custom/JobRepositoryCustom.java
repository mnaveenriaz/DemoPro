package app.custom;

import java.util.List;

import app.model.ValueObject;

/**
 *
 * @author mnaveenriaz
 *
 */
public interface JobRepositoryCustom {
	public Iterable<ValueObject> runMapReduce(String map, String reduce);

	public List<String> getDistinctKeys(String key);
}
