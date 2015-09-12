package app.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Document(collection = "jobs")
public class Job {
	@Id
	private String		_id;
	private int			tableID;
	private int			jobID;
	private int			versionSerialNumber;
	private String		tableName;
	private String		application;
	private String		group;
	private String		jobName;
	private String		execFileName;
	private String		server;
	private String		execPath;
	private String		owner;
	private String		taskType;
	private int			maxWait;
	private String		startTime;
	private String		endTime;
	private int			maxRun;
	private String		days;
	private String		holidays;
	private String		months;
	private DateTime	modifiedOn;
}
