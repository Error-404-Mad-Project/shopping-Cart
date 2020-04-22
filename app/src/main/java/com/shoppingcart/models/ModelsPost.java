package com.shoppingcart.models;

public class ModelsPost {
    String uPicture,uName,pTime,pTitle,pDescription,pImage,moreBtn,uid;


    public ModelsPost() {
    }

    public ModelsPost(String uPicture, String uName, String pTime, String pTitle, String pDescription, String pImage, String moreBtn) {
        this.uPicture = uPicture;
        this.uName = uName;
        this.pTime = pTime;
        this.pTitle = pTitle;
        this.pDescription = pDescription;
        this.pImage = pImage;
        this.moreBtn = moreBtn;
        this.uid = uid;

    }


    public String getuPicture() {
        return uPicture;
    }

    public void setuPicture(String uPicture) {
        this.uPicture = uPicture;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getMoreBtn() {
        return moreBtn;
    }

    public void setMoreBtn(String moreBtn) {
        this.moreBtn = moreBtn;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuid() {
        return uid;
    }
}
