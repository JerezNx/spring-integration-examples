package xyz.jerez.spring.json.bean;

import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author liqilin
 * @since 2021/3/5 15:29
 */
@ToString
public class FastJsonBean {

    private int id;

    private String name;

    private Date createTime;

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

    public void setName(String name) {
        this.name = name;
    }

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

    public static FastJsonBean getInstance() {
        FastJsonBean bean = new FastJsonBean();
        bean.setId(1);
        bean.setName("lql");
        bean.setCreateTime(new Date());
        bean.setUpdateTime(LocalDateTime.now());
        return bean;
    }

}
