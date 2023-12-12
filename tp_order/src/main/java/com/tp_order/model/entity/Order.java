package com.tp_order.model.entity;

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
 * @since 2023-11-10 11:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class Order implements Serializable {

public static final String STATE_UNPAID="UNPAID";

public static final String STATE_FINISHED="FINISHED";

public static final String STATE_FAILED="FAILED";

public static final String STATE_CANCELED="CANCELED";

 @TableId("orderid")
private Integer orderid;

@TableField("userid")
private Integer userid;

@TableField("username")
private String username;

@TableField("movieid")
private Integer movieid;

@TableField("moviename")
private String moviename;

@TableField("ticketprice")
private Double ticketprice;

@TableField("orderstate")
private String orderstate;

@TableField("ordertime")
private LocalDateTime ordertime;


        }