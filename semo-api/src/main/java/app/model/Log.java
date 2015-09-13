package app.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author mnaveenriaz
 *
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Document(collection = "logs")
public class Log {
	@Id
	private String		_id;
	private String		tableName;
	private String		jobName;
	private String		application;
	private String		group;
	private String		server;
	private String		odate;
	private DateTime	startTime;
	private DateTime	endTime;
	private String		returnCode;
	private String		status;
	private String		message;
	private String		incident;
	private String		title;
	private String		severity;
	private String		affectedCI;

}
