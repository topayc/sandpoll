package com.sandwhale.testepoll.model;

public class BasePoll {
	
	public int pollType;
	public int category;
	public boolean isVoted;
	public String pollName;
	public String period;
	public String voteCount;
	public String mainPollImage;
	public int joinedSubPoll;
	
	
	public static final int POLL_TYPE_IMAGE = 0;
	public static final int POLL_TYPE_TEXT = 1;

	
	
	public int getJoinedSubPoll() {return joinedSubPoll;}
	public void setJoinedSubPoll(int joinedSubPoll) {this.joinedSubPoll = joinedSubPoll;}
	public int getPollType() {return pollType;}
	public void setPollType(int pollType) {this.pollType = pollType;}
	public boolean isVoted() {return isVoted;}
	public void setVoted(boolean isVoted) {this.isVoted = isVoted;}
	public int getCategory() {return category;}
	public void setCategory(int category) {this.category = category;}
	public String getPollName() {return pollName;}
	public void setPollName(String pollName) {this.pollName = pollName;}
	public String getPeriod() {return period;}
	public void setPeriod(String period) {this.period = period;}
	public String getVoteCount() {return voteCount;}
	public void setVoteCount(String voteCount) {this.voteCount = voteCount;}
	public String getMainPollImage() {return mainPollImage;}
	public void setMainPollImage(String mainPollImage) {this.mainPollImage = mainPollImage;}
}
