package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.model.Log;

//@Repository
public interface LogRepository extends MongoRepository<Log, String>, LogRepositoryCustom {
	@Query("{'jobName' :?0}")
	public List<Log> searchByJobName(String jobName);

	@Query("{'tableName' :?0}")
	public List<Log> searchByTableName(String tableName);

	@Query("{'application' :?0}")
	public List<Log> searchByApplication(String application);

	@Query("{'group' :?0}")
	public List<Log> searchByGroup(String group);

	@Query("{'server' :?0}")
	public List<Log> searchByServer(String server);

	@Query("{'odate' :?0}")
	public List<Log> searchByOdate(String odate);

	@Query("{'startTime' :?0}")
	public List<Log> searchByStartTime(String startTime);

	@Query("{'endTime' :?0}")
	public List<Log> searchByEndTime(String endTime);

	@Query("{'returnCode' :?0}")
	public List<Log> searchByReturnCode(String returnCode);

	@Query("{'status' :?0}")
	public List<Log> searchByStatus(String status);

	@Query("{'message' :?0}")
	public List<Log> searchByMessage(String message);

	@Query("{'incident' :?0}")
	public List<Log> searchByIncident(String incident);

	@Query("{'title' :?0}")
	public List<Log> searchByTitle(String title);

	@Query("{'severity' :?0}")
	public List<Log> searchBySeverity(String severity);

	@Query("{'affectedCI' :?0}")
	public List<Log> searchByAffectedCI(String affectedCI);

	@Query("{'jobname' :?0,'odate':?1 }")
	public List<Log> searchByJobNameAndOdate(String jobName, String odate);

	@Query("{'odate' :?0,'status':?1 }")
	public List<Log> searchByOdateAndStatus(String odate, String status);

}
