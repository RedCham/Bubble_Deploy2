package com.example.bubble_deploy.referenceClass;

import android.net.Uri;
import android.util.SparseBooleanArray;

import java.util.ArrayList;

public class imageFolder implements Cloneable{
    private  String FolderName;
    private int numberOfPics = 0;
    public String folderdate;
    public dateImage firstPic;
    public SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    public ArrayList<dateImage> picPathList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public imageFolder(){
        picPathList = new ArrayList<>();
    }


    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getNumberOfPics() {
        return numberOfPics;
    }

    public void addPics(Uri p, String d,String n, String a){
        this.numberOfPics++;
        picPathList.add(new dateImage(p,d,n,a));
    }

    public void addPics(dateImage image){
        this.numberOfPics++;
        picPathList.add(image);
    }

    public Uri getFirstPic() {return firstPic.getImagePath(); }
    public void setFirstPic(dateImage firstPic) {
        this.firstPic = firstPic;
    }
    public void setFirstPic(int i) {
        this.firstPic = picPathList.get(i);
    }
}
