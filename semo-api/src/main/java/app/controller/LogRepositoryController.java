package app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import app.repository.LogRepository;

/**
 *
 * @author mnaveenriaz
 *
 */
public class LogRepositoryController {
	@Autowired
	private LogRepository	logRepository;
	private final String	jobCollection	= "jobs";
	private final String	logCollection	= "logs";
}
