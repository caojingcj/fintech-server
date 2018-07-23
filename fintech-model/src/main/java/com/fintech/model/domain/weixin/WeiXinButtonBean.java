package com.fintech.model.domain.weixin;

import java.util.List;

public class WeiXinButtonBean {

    /* 点击打开网页类型 */
    public static String TYPE_CLICK_HTML = "view";

    public static String TYPE_SUB_HTML = "sub";

    private String type;
    private String name;
    private String url;
    private List<WeiXinButtonBean> sub_button;

    public WeiXinButtonBean() {
    }

    public WeiXinButtonBean(String type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public List<WeiXinButtonBean> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<WeiXinButtonBean> sub_button) {
        this.sub_button = sub_button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WeiXinButtonBean{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
