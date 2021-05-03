package com.sandwhale.testepoll.model;

import java.util.ArrayList;

public class ImagePoll extends BasePoll{

	public ArrayList<SubImagePollResult> subPolls;
	
	public ArrayList<SubImagePollResult> getSubPolls() {
		return subPolls;
	}

	public void setSubPolls(ArrayList<SubImagePollResult> subPolls) {
		this.subPolls = subPolls;
	}

	public static class SubImagePollResult {
		public int  imagePath;
		public int subVoteCount;
		
		public int getImagePath() {
			return imagePath;
		}
		public void setImagePath(int imagePath) {
			this.imagePath = imagePath;
		}
		public int getSubVoteCount() {
			return subVoteCount;
		}
		public void setSubVoteCount(int subVoteCount) {
			this.subVoteCount = subVoteCount;
		}
		
	}
}
