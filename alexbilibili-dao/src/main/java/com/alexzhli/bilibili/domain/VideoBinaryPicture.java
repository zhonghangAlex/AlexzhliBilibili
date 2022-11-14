package com.alexzhli.bilibili.domain;

import java.util.Date;

public class VideoBinaryPicture {

    private Long id;

    private Long videoId;

    // 帧数
    private Integer frameNo;

    private String url;

    // 帧数对应的时间
    private Long videoTimestamp;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(Integer frameNo) {
        this.frameNo = frameNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVideoTimestamp() {
        return videoTimestamp;
    }

    public void setVideoTimestamp(Long videoTimestamp) {
        this.videoTimestamp = videoTimestamp;
    }
}
