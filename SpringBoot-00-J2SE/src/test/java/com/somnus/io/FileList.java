package com.somnus.io;

import java.io.File;

/**
 * 遍历目录
 *
 * @author Somnus
 * @version V1.0
 * @Title: FileList.java
 * @Package com.somnus.io
 * @Description: TODO
 * @date 2015年6月9日 下午4:57:21
 */
public class FileList {

    public static void main(String[] args) {
        File file = new File("E:\\Apache Tomcat Server");
        File[] files = file.listFiles();
        for (int i = 1; i <= files.length - 1; i++) {
            System.out.println(files[i].getName());
            String path = files[i].getParent();
            File newfile = new File(path + "/" + i);
            files[i].renameTo(newfile);
            System.out.println(files[i].getName());
        }
    }

}