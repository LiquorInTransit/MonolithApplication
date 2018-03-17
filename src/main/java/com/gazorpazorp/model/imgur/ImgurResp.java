package com.gazorpazorp.model.imgur;

public class ImgurResp {

	private ImgurObj data;
	private String success;
	private Integer status;
	public ImgurObj getData() {
		return data;
	}
	public void setData(ImgurObj data) {
		this.data = data;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
