package app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import app.repository.JobRepository;

/**
 *
 * @author mnaveenriaz
 *
 */
public class JobRepositoryController {
	@Autowired
	private JobRepository	jobRepository;
	private final String	jobCollection	= "jobs";
	private final String	logCollection	= "logs";

}
