package com.sandwhale.testepoll.model;

public class PollVoteResult {
	private int pollImage;
	private int votedCount;
	private boolean isJoined;
	
	public int getPollImage() {
		return pollImage;
	}
	public void setPollImage(int pollImage) {
		this.pollImage = pollImage;
	}
	public int getVotedCount() {
		return votedCount;
	}
	public void setVotedCount(int votedCount) {
		this.votedCount = votedCount;
	}
	public boolean isJoined() {
		return isJoined;
	}
	public void setJoined(boolean isJoined) {
		this.isJoined = isJoined;
	}

	
	
}

