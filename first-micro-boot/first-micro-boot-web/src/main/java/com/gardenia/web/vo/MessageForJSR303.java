package com.gardenia.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @author caimeng
 * @date 2025/2/21 16:45
 */
@Data
public class MessageForJSR303 {
    @NotBlank(message = "消息标题不允许为空")
    private String title;
    @NotNull(message = "消息的发布日期不允许为空")
    @JSONField(format = "yyyy年MM月dd日")
    private Date pubDate;
    @NotBlank(message = "消息内容不允许为空")
    private String content;
    @Email(message = "消息发送邮箱的格式错误")
    @NotNull(message = "消息发送的邮箱不允许为空")
    private String email;
    @Digits(integer = 1, fraction = 0, message = "消息的级别只能是一位整数")  // 整数部分不超过1位，小数部分不超过0位
    private Integer level;
}
