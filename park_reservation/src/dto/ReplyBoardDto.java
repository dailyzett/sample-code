package dto;

public class ReplyBoardDto {
	private int id;
	private String content;
	private int fk_id;
	
	public int getFk_id() {
		return fk_id;
	}
	public void setFk_id(int fk_id) {
		this.fk_id = fk_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
