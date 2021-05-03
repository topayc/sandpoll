package com.sandwhale.testepoll.model;

import java.util.ArrayList;

public class TextPoll extends BasePoll {
	
	public ArrayList<SubTextPollResult>	subPolls;
	
	public ArrayList<SubTextPollResult> getSubPolls() {
		return subPolls;
	}

	public void setSubPolls(ArrayList<SubTextPollResult> subPolls) {
		this.subPolls = subPolls;
	}

	public static class SubTextPollResult{
		public String subPollText;
		public int subVoteCount;
		
		public String getSubPollText() {
			return subPollText;
		}
		public void setSubPollText(String subPollText) {
			this.subPollText = subPollText;
		}
		public int getSubVoteCount() {
			return subVoteCount;
		}
		public void setSubVoteCount(int subVoteCount) {
			this.subVoteCount = subVoteCount;
		}
		
		
	}
}
