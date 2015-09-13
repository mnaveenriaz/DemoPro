package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.custom.JobRepositoryCustom;
import app.model.Job;

/**
 *
 * @author mnaveenriaz
 *
 */
// @Repository
public interface JobRepository extends MongoRepository<Job, String>, JobRepositoryCustom {
	@Query("{'tableID' :?0}")
	public List<Job> searchByTableID(String tableID);

	@Query("{'jobID' :?0}")
	public List<Job> searchByJobID(String jobID);

	@Query("{'versionSerialNumber' :?0}")
	public List<Job> searchByVersionSerialNumber(String versionSerialNumber);

	@Query("{'tableName' :?0}")
	public List<Job> searchByTableName(String tableName);

	@Query("{'application' :?0}")
	public List<Job> searchByApplication(String application);

	@Query("{'group' :?0}")
	public List<Job> searchByGroup(String group);

	@Query("{'jobName' :?0}")
	public List<Job> searchByJobName(String jobName);

	@Query("{'execFileName' :?0}")
	public List<Job> searchByExecFileName(String execFileName);

	@Query("{'server' :?0}")
	public List<Job> searchByServer(String server);

	@Query("{'execPath' :?0}")
	public List<Job> searchByExecPath(String execPath);

	@Query("{'owner' :?0}")
	public List<Job> searchByOwner(String owner);

	@Query("{'taskType' :?0}")
	public List<Job> searchByTaskType(String taskType);

	@Query("{'maxWait' :?0}")
	public List<Job> searchByMaxWait(int maxWait);

	@Query("{'startTime' :?0}")
	public List<Job> searchByStartTime(String startTime);

	@Query("{'endTime' :?0}")
	public List<Job> searchByEndTime(String endTime);

	@Query("{'maxRun' :?0}")
	public List<Job> searchByMaxRun(int maxRun);

	@Query("{'days' :?0}")
	public List<Job> searchByDays(String days);

	@Query("{'holidays' :?0}")
	public List<Job> searchByHolidays(String holidays);

	@Query("{'months' :?0}")
	public List<Job> searchByMonths(String months);

	@Query("{'modifiedOn' :?0}")
	public List<Job> searchByModifiedOn(String modifiedOn);

}
