package com.tp_movie.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
        import lombok.experimental.Accessors;
    
/**
 * <p>
 * 
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-12 11:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("comments")
public class Comments implements Serializable {


/**
 * 电影ID
 */
@TableField("movieid")
private Integer movieid;

/**
 * 用户ID
 */
 @TableId("userid")
private Integer userid;

/**
 * 评论
 */
@TableField("comment")
private String comment;


        }