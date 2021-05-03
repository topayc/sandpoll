package com.sandwhale.testepoll.model;

public class Poll {
	public int category;
	public String pollName;
	public String period;
	public String voteCount;
	public String pooImagePath;
	
	public Poll(){}

	public String getPollName() {
		return pollName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}

	public String getPooImagePath() {
		return pooImagePath;
	}

	public void setPooImagePath(String pooImagePath) {
		this.pooImagePath = pooImagePath;
	}

	public void setPollName(String pollName) {
		this.pollName = pollName;
	}


	
	
}
