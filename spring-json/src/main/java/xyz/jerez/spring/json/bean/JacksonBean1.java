package xyz.jerez.spring.json.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author liqilin
 * @since 2021/3/5 15:29
 */
@ToString
public class JacksonBean1 {

    @JsonProperty("ID")
    private int id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("NAME")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public static JacksonBean1 getInstance() {
        JacksonBean1 bean = new JacksonBean1();
        bean.setId(1);
        bean.setName("lql");
        bean.setCreateTime(new Date());
        bean.setUpdateTime(LocalDateTime.now());
        return bean;
    }

}
