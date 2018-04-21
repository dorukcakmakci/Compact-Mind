package UI;

public class CellBlock {
	private String questionNo;
	private String currentLetter;
	private Boolean isBlack;
	public CellBlock(){
		questionNo = ""; // -1 IS NOT A QUESTION START BLOCK
		currentLetter = ""; // - is for no current letter
	}
	public String getQuestionNo(){
		return questionNo;
	}
	public String getCurrentLetter(){
		return currentLetter;
	}
	public void setQuestionNo(String questionNo){
		this.questionNo = questionNo;
	}
	public void setCurrentLetter(String currentLetter){
		this.currentLetter = currentLetter;
	}
}
