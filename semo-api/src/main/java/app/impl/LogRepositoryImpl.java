package app.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.query.Criteria;

import app.custom.LogRepositoryCustom;
import app.model.Log;
import app.model.ValueObject;

/**
 *
 * @author mnaveenriaz
 *
 */
public class LogRepositoryImpl implements LogRepositoryCustom {
	@Autowired
	private MongoTemplate	mongoTemplate;
	private final String	collection	= "logs";

	@Override
	public Iterable<ValueObject> runMapReduce(String map, String reduce) {
		return mongoTemplate.mapReduce(collection, map, reduce, new MapReduceOptions().outputTypeInline(),
				ValueObject.class);
	}

	@Override
	public List<ValueObject> getOdateStatsByStatus(String status) {
		final Aggregation aggregation = newAggregation(match(Criteria.where("status").is(status)),
				group("odate").count().as("value").addToSet("odate").as("name"), unwind("name"),
				project("value").and("name").previousOperation(), sort(Sort.Direction.ASC, "name"));
		return mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class).getMappedResults();
	}

	@Override
	public List<ValueObject> getServerStatsByOdateAndStatus(String odate, String status) {
		final Aggregation aggregation = newAggregation(match(Criteria.where("odate").is(odate).and("status")),
				group("server").count().as("value"), project("value").and("server").previousOperation(),
				sort(Sort.Direction.ASC, "server"));
		return mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class).getMappedResults();
	}

	@Override
	public List<ValueObject> getGroupStatsByOdateAndStatus(String odate, String status) {
		final Aggregation aggregation = newAggregation(match(Criteria.where("group").is(odate).and("status")),
				group("server").count().as("value"), project("value").and("server").previousOperation(),
				sort(Sort.Direction.ASC, "server"));
		return mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class).getMappedResults();
	}

	@Override
	public List<ValueObject> getApplicationStatsByOdateAndStatus(String odate, String status) {
		final Aggregation aggregation = newAggregation(match(Criteria.where("application").is(odate).and("status")),
				group("server").count().as("value"), project("value").and("server").previousOperation(),
				sort(Sort.Direction.ASC, "server"));
		return mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class).getMappedResults();
	}

	@Override
	public List<ValueObject> getOdateStats() {
		final Aggregation aggregation = newAggregation(group("odate").count().as("value").addToSet("odate").as("name"),
				unwind("name"), project("value").and("name").previousOperation(), sort(Sort.Direction.ASC, "name"));
		return mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class).getMappedResults();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getDistinctKeys(String key) {
		return mongoTemplate.getCollection(collection).distinct(key);
	}

}
