package app.controller;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Log;
import app.model.ValueObject;
import app.repository.LogRepository;
import app.utility.DateUtil;

/**
 *
 * @author mnaveenriaz
 *
 */
@RestController
@RequestMapping("/log")
public class LogRepositoryController {
	@Autowired
	private LogRepository logRepository;

	public Log buildLog(Map<String, Object> map, String _id) {
		return Log.builder()._id(_id).tableName(map.get("tableName").toString()).jobName(map.get("jobName").toString())
				.application(map.get("application").toString()).group(map.get("group").toString())
				.server(map.get("server").toString()).odate(map.get("odate").toString())
				.startTime(DateUtil.parse(map.get("startTime").toString()))
				.endTime(DateUtil.parse(map.get("endTime").toString())).returnCode(map.get("returnCode").toString())
				.status(map.get("status").toString()).message(map.get("message").toString())
				.incident(map.get("incident").toString()).title(map.get("title").toString())
				.severity(map.get("severity").toString()).affectedCI(map.get("affectedCI").toString()).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createLog(@RequestBody Map<String, Object> map) throws ParseException {

		Log log = buildLog(map, null);
		logRepository.save(log);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Log created successfully");
		response.put("log", log);
		return response;
	}

	public void generateLogs(int count, String odate, String jobName, String status) {
		for (int i = 0; i < count; i++) {
			if (odate == null) odate = DateUtil.getCurrentOdate();

			if (jobName == null) {
				List<String> list = logRepository.getDistinctKeys("jobName");
				jobName = list.get(new Random().nextInt(list.size()));
			}
			// Job Job = logRepository.findOneByJobName(jobName);

			Log log = Log.builder()._id(null).odate(DateUtil.getCurrentOdate())
					.startTime(DateTime.now().minusMinutes(new Random().nextInt(60))).endTime(DateTime.now()).build();
			if (new Random().nextInt(10) < 7) {
				log.setStatus("Failed");
				log.setReturnCode("U0" + new Random().nextInt(100));
				log.setSeverity(Integer.toString(new Random().nextInt(5)));
				log.setIncident("IR00" + log.getSeverity() + "99" + log.getReturnCode());
				log.setMessage(log.getIncident() + " - check detailed log for moe info");
				log.setTitle(log.getJobName() + "failed due to reason");
				logRepository.save(log);
				generateLogs(count, odate, jobName, "complete");
			} else {
				log.setReturnCode("0000");
				log.setStatus("complete");
				logRepository.save(log);
			}
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{_id}")
	public Map<String, Object> editLog(@PathVariable("_id") String _id, @RequestBody Map<String, Object> map)
			throws ParseException {
		Log log = buildLog(map, _id);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Log updated successfully");
		response.put("log", logRepository.save(log));
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generate")
	public Map<String, Object> generateNewLog() {
		generateLogs(1, DateUtil.getCurrentOdate(), null, null);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Log created successfully");
		response.put("totalLogs", logRepository.count());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generate/{count}")
	public Map<String, Object> generateNewLogs(@PathVariable("count") String count) {
		generateLogs(Integer.parseInt(count), DateUtil.getCurrentOdate(), null, null);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", count + "logs created successfully");
		response.put("totalLogs", logRepository.count());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generate/{count}/{odate}")
	public Map<String, Object> generateNewLogs(@PathVariable("count") String count,
			@PathVariable("odate") String odate) {
		generateLogs(Integer.parseInt(count), odate, null, null);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", count + "Log created successfully");
		response.put("totalLogs", logRepository.count());
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generate/{count}/{date1}/{date2}")
	public Map<String, Object> generateNewLogs(@PathVariable("count") String count, @PathVariable("date1") String date1,
			@PathVariable("date2") String date2) {
		int dateCount = 0;
		try {
			DateTime startDate = DateUtil.parse(date1);
			DateTime endDate = DateUtil.parse(date2);
			int compare = startDate.compareTo(endDate);
			if (compare == 0) {
				dateCount = 1;
				generateLogs(Integer.parseInt(count), date1, null, null);
			} else {
				if (compare > 0) { // date2 < date1
					startDate = DateUtil.parse(date2);
					endDate = DateUtil.parse(date1);
				}
				while (startDate.compareTo(endDate) <= 0) {
					generateLogs(Integer.parseInt(count), DateUtil.formatOdate(startDate), null, null);
					startDate.plusDays(1);
					dateCount++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", count + "Log created successfully for" + dateCount + "Dates");
		response.put("totalLogs", logRepository.count());
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> searchByOdate() {

		List<Log> logs = logRepository.searchByOdate(DateUtil.getCurrentOdate());
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalLogs", logs.size());
		response.put("logs", logs);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/jobName/{jobName}")
	public Map<String, Object> searchByJobName(@PathVariable("jobName") String jobName) {
		List<Log> logs = logRepository.searchByJobName(jobName);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalLogs", logs.size());
		response.put("logs", logs);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/odate/{odate}")
	public Map<String, Object> searchByOdate(@PathVariable("odate") String odate) {
		List<Log> logs = logRepository.searchByOdate(odate);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalLogs", logs.size());
		response.put("logs", logs);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/status/{status}")
	public Map<String, Object> searchByOdateAndStatus(@PathVariable("status") String status) {
		List<Log> logs = logRepository.searchByOdateAndStatus(DateUtil.getCurrentOdate(), status);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalLogs", logs.size());
		response.put("logs", logs);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/logid/{_id}")
	public Log getLogDetailsbyLogID(@PathVariable("_id") String _id) {
		return logRepository.findOne(_id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stats/odate/status")
	public Map<String, Object> getOdateStats() {
		List<ValueObject> stats = logRepository.getOdateStats();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalStats", stats.size());
		response.put("stats", stats);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/distinct/{key}")
	public Map<String, Object> getDistinctKeys(@PathVariable("key") String key) {

		List<String> keys = logRepository.getDistinctKeys(key);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalKeys", keys.size());
		response.put("keys", keys);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stats/odate/status/{status}")
	public Map<String, Object> getOdateStatsByStatus(@PathVariable("status") String status) {
		List<ValueObject> stats = logRepository.getOdateStatsByStatus(status);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalStats", stats.size());
		response.put("stats", stats);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stats/server/status/{status}")
	public Map<String, Object> getServerStatsByStatus(@PathVariable("status") String status) {
		List<ValueObject> stats = logRepository.getServerStatsByOdateAndStatus(DateUtil.getCurrentOdate(), status);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalStats", stats.size());
		response.put("stats", stats);
		return response;
	}

}
