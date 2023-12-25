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
<<<<<<< Updated upstream:tp_order/src/main/java/com/tp_order/model/entity/Orders.java
@TableName("orders")
public class Orders implements Serializable {

=======
@TableName("comments")
public class Comments implements Serializable {
@TableId("commentid")
private Integer commentid;
>>>>>>> Stashed changes:tp_movie/src/main/java/com/tp_movie/model/entity/Comments.java

 @TableId("orderid")
private Integer orderid;

@TableField("userid")
private Integer userid;

@TableField("movieid")
private Integer movieid;

<<<<<<< Updated upstream:tp_order/src/main/java/com/tp_order/model/entity/Orders.java
@TableField("ticketprice")
private Double ticketprice;
=======
/**
 * 用户ID
 */
@TableField("userid")
private Integer userid;
>>>>>>> Stashed changes:tp_movie/src/main/java/com/tp_movie/model/entity/Comments.java

@TableField("orderstate")
private String orderstate;

@TableField("ordertime")
private LocalDateTime ordertime;


}