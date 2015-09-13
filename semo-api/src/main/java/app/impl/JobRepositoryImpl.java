package app.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;

import app.custom.JobRepositoryCustom;
import app.model.ValueObject;

/**
 *
 * @author mnaveenriaz
 *
 */
public class JobRepositoryImpl implements JobRepositoryCustom {
	@Autowired
	private MongoTemplate	mongoTemplate;
	private final String	collection	= "jobs";

	@Override
	public Iterable<ValueObject> runMapReduce(String map, String reduce) {
		return mongoTemplate.mapReduce(collection, map, reduce, new MapReduceOptions().outputTypeInline(),
				ValueObject.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getDistinctKeys(String key) {
		return mongoTemplate.getCollection(collection).distinct(key);
	}

}
