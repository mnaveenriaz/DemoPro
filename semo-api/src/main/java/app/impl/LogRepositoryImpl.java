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

import app.model.Log;
import app.model.ValueObject;
import app.repository.LogRepositoryCustom;

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
		final List<ValueObject> statsList = mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class)
				.getMappedResults();
		return statsList;
	}

	@Override
	public List<ValueObject> getServerStatsByOdateAndStatus(String odate, String status) {
		// List<Log> logList = mongoTemplate.find(new
		// Query(Criteria.where("odate").is(odate).is(odate).and("status").is(status)),
		// Log.class);
		final Aggregation aggregation = newAggregation(match(Criteria.where("odate").is(odate).and("status")),
				group("server").count().as("count"), project("count").and("server").previousOperation(),
				sort(Sort.Direction.ASC, "server"));
		final List<ValueObject> statsList = mongoTemplate.aggregate(aggregation, Log.class, ValueObject.class)
				.getMappedResults();
		return statsList;
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
