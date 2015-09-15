package app.controller;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.model.Job;
import app.repository.JobRepository;
import app.utility.DateUtil;

/**
 *
 * @author mnaveenriaz
 *
 */
@RestController
@RequestMapping("/job")

public class JobRepositoryController {
	@Autowired
	private JobRepository jobRepository;
	// private final String jobCollection = "jobs";
	// private final String logCollection = "logs";

	public Job buildJob(Map<String, Object> map, String _id) throws NumberFormatException, ParseException {
		return Job.builder()._id(_id).tableID(Integer.parseInt(map.get("tableID").toString()))
				.jobID(Integer.parseInt(map.get("jobID").toString()))
				.versionSerialNumber(Integer.parseInt(map.get("versionSerialNumber").toString()))
				.tableName(map.get("tableName").toString()).application(map.get("application").toString())
				.group(map.get("group").toString()).jobName(map.get("jobName").toString())
				.execFileName(map.get(".execFileName").toString()).server(map.get("server").toString())
				.execPath(map.get("execPath").toString()).owner(map.get("owner").toString())
				.taskType(map.get("taskType").toString()).maxWait(Integer.parseInt(map.get("jobID").toString()))
				.startTime(map.get("startTime").toString()).endTime(map.get("endTime").toString())
				.maxRun(Integer.parseInt(map.get("maxRun").toString())).days(map.get("days").toString())
				.holidays(map.get("holidays").toString()).months(map.get("months").toString())
				.modifiedOn(DateUtil.parse(map.get("modifiedOn").toString())).build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> createJob(@RequestBody Map<String, Object> map) throws ParseException {
		Job job = buildJob(map, null);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Job created successfully");
		response.put("job", job);
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{_id}")
	public Map<String, Object> editJob(@PathVariable("_id") String _id, @RequestBody Map<String, Object> map)
			throws ParseException {
		Job job = buildJob(map, _id);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Job Updated Successfully");
		response.put("job", jobRepository.save(job));
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getAllJobs() {
		List<Job> jobs = jobRepository.findAll();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("count", jobs.size());
		response.put("jobs", jobs);
		return response;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/jobid/{_id}")
	public Job getJobDetailsbyJobID(@PathVariable("_id") String _id) {
		return jobRepository.findOne(_id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/distinct/{key}")
	public Map<String, Object> getDistinctKeys(@PathVariable("key") String key) {

		List<String> keys = jobRepository.getDistinctKeys(key);
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("totalkeys", keys.size());
		response.put("keys", keys);
		return response;
	}
}
