package com.fintech.model.vo.faceid;

/**   
* @Title: FaceidIDCardSideVo.java 
* @Package com.fintech.model.vo.faceid 
* @author qierkang xyqierkang@163.com   
* @date 2018年6月30日 下午1:05:18  
* @Description: TODO[ 用一句话描述该文件做什么 ]
* 
* ID Photo （正式身份证照片）
Temporary ID Photo  （临时身份证照片）
Photocopy （正式身份证的复印件）
Screen （手机或电脑屏幕翻拍的照片）
Edited （用工具合成或者编辑过的身份证图片）
*/
public class Legality {
    private Integer Edited;
    private Integer Photocopy;
    private Integer IDPhoto;
    private Integer Screen;
    private Integer TemporaryIDPhoto;

    public Integer getEdited() {
        return Edited;
    }

    public void setEdited(Integer edited) {
        Edited = edited;
    }

    public Integer getPhotocopy() {
        return Photocopy;
    }

    public void setPhotocopy(Integer photocopy) {
        Photocopy = photocopy;
    }

    public Integer getIDPhoto() {
        return IDPhoto;
    }

    public void setIDPhoto(Integer iDPhoto) {
        IDPhoto = iDPhoto;
    }

    public Integer getScreen() {
        return Screen;
    }

    public void setScreen(Integer screen) {
        Screen = screen;
    }

    public Integer getTemporaryIDPhoto() {
        return TemporaryIDPhoto;
    }

    public void setTemporaryIDPhoto(Integer temporaryIDPhoto) {
        TemporaryIDPhoto = temporaryIDPhoto;
    }

    @Override
    public String toString() {
        return "Legality [Edited=" + Edited + ", Photocopy=" + Photocopy + ", IDPhoto=" + IDPhoto + ", Screen=" + Screen
                + ", TemporaryIDPhoto=" + TemporaryIDPhoto + "]";
    }

}
