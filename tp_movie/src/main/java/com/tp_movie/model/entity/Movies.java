package com.tp_movie.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
        import lombok.experimental.Accessors;
    
/**
 * <p>
 * 
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("movies")
public class Movies implements Serializable {


 @TableId("movieid")
private Integer movieid;

@TableField("moviename")
private String moviename;

@TableField("description")
private String description;

@TableField("ticketprice")
private Double ticketprice;

@TableField("releasedate")
private LocalDateTime releasedate;

@TableField("type")
private String type;

@TableField("image_url")
private String imageUrl;

}