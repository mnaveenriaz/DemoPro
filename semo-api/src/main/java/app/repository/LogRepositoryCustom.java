package app.repository;

import java.util.List;

import app.model.ValueObject;

public interface LogRepositoryCustom {

	public Iterable<ValueObject> runMapReduce(String map, String reduce);

	public List<ValueObject> getOdateStatsByStatus(String status);

	public List<ValueObject> getServerStatsByOdateAndStatus(String odate, String status);

	public List<ValueObject> getOdateStats();

	public List<String> getDistinctKeys(String key);
}
