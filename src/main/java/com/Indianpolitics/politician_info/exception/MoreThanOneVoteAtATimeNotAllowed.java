package com.Indianpolitics.politician_info.exception;

public class MoreThanOneVoteAtATimeNotAllowed extends RuntimeException {

	public MoreThanOneVoteAtATimeNotAllowed(String message) {

		super(message);
	}
}
