package com.somnus.connect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Somnus
 * @version V1.0
 * @Title: FTPClientHelper.java
 * @Package com.somnus.connect
 * @Description: TODO
 * @date 2015年6月2日 上午10:07:47
 */
public class FTPClientHelper {

    public FTPClientHelper() {
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param password
     */
    public FTPClientHelper(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    /**
     * @throws IOException
     * @Description 连接并登录到FTP
     * @author Somnus
     */
    public void login() throws IOException {
        client = new FTPClient();
        client.connect(host, Integer.parseInt(port));
        int replyCode = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            client.disconnect();
            log.error("connect to specified ftp server is refused : {}:{} ", new Object[]{host, port});
        }
        Validate.notEmpty(username, "username is required.");
        Validate.notEmpty(password, "password is required.");
        if (!client.login(username, password)) {
            client.disconnect();
            log.error("Cannot connect to specified ftp server : {}:{} ", new Object[]{host, port});
        }

    }

    /**
     * @param remoteFile 文件
     * @return
     * @Description 判断文件是否存在
     * @author Somnus
     */
    public boolean isRemoteFileExists(String remoteFile) {
        try {
            FTPFile[] files = client.listFiles(remoteFile);
            return !ArrayUtils.isEmpty(files) && files.length > 0;
        } catch (IOException e) {
            log.warn(remoteFile + "文件不存在");
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        FTPClientHelper ftp = new FTPClientHelper("192.168.12.216", "21", "tech", "tech123");
        ftp.login();

        //boolean r = ftp.isRemoteDirExists("/home/tech/posp");
        boolean r = ftp.isRemoteFileExists("/home/tech/posp/20140401/NewPospWater_20140401.txt");
        System.out.println("r >>> " + r);

        ftp.logout();
    }

    /**
     * @param remoteDirPath
     * @return
     * @Description 判断目录是否存在
     * @author Somnus
     */
    public boolean isRemoteDirExists(String remoteDirPath) {
        try {
            return client.changeWorkingDirectory(remoteDirPath);
        } catch (IOException e) {
            log.warn(remoteDirPath + "目录不存在");
            return false;
        }
    }

    /**
     * @param localFile      本地文件
     * @param remoteFilePath 远程文件全路径
     * @param remoteFileName
     * @return
     * @throws IOException
     * @Description 文件上传
     * @author Somnus
     */
    public boolean upload(File localFile, String remoteFilePath, String remoteFileName) throws IOException {
        Validate.notEmpty(remoteFilePath, "remoteFilePath file is required.");
        //set ftp file transfer mode
        client.setFileType(FTP.BINARY_FILE_TYPE);
        //create directory
        client.makeDirectory(new String(remoteFilePath.getBytes(), client.getControlEncoding()));
        //change directory
        client.changeWorkingDirectory(remoteFilePath);
        //construct input stream
        InputStream is = new FileInputStream(localFile);
        //put file to server
        boolean stored = client.storeFile(remoteFileName, is);
        //close stream
        IOUtils.closeQuietly(is);
        log.info("upload {}", stored ? "successful" : "failed");
        return stored;
    }

    /**
     * @param remoteFilePath 远程全路径
     * @param localFile      本地文件全路径
     * @return
     * @throws IOException
     * @Description 下载文件
     * @author Somnus
     */
    public boolean download(String remoteFilePath, File localFile) throws IOException {
        client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);
        if (!localFile.getParentFile().exists()) {
            boolean dirCreated = localFile.getParentFile().mkdirs();
            log.info("directory created {}", dirCreated ? "successful" : "failed");
        }
        OutputStream os = new FileOutputStream(localFile);
        boolean received = client.retrieveFile(new String(remoteFilePath.getBytes(), client.getControlEncoding()), os);
        log.info("download {}", received ? "successful" : "failed");
        return received;
    }

    /**
     * @throws IOException
     * @Description 登出FTP
     * @author Somnus
     */
    public void logout() throws IOException {
        client.disconnect();
    }

    private transient Logger log = LoggerFactory.getLogger(FTPClientHelper.class);

    private String host;

    private String port;

    private String username;

    private String password;

    private FTPClient client;

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
