package xyz.jerez.spring.json.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author liqilin
 * @since 2021/3/5 15:29
 */
@ToString
public class FastJsonBean1 {

    @JSONField(name = "ID")
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

    @JSONField(name = "NAME")
    public void setName(String name) {
        this.name = name;
    }

    @JSONField(name = "CREATE_TIME",format = "yyyy-MM-dd HH:mm:ss")
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

    public static FastJsonBean1 getInstance() {
        FastJsonBean1 bean = new FastJsonBean1();
        bean.setId(1);
        bean.setName("lql");
        bean.setCreateTime(new Date());
        bean.setUpdateTime(LocalDateTime.now());
        return bean;
    }

}
