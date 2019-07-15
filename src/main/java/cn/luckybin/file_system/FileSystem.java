package cn.luckybin.file_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName FileSystem
 * @Author liuyunbin
 * @Date 2019/7/15 11:32
 * @Description TODO
 */
@Slf4j
@Component
public class FileSystem {

    //文件存放路径
//    public static final String FILE_PATH = "./filelist";
    public static final String FILE_PATH = "/opt/tempwork/filelist";


    //存储文件名与文件内容的映射关系
    public static Map<String, String> FileMap = new ConcurrentHashMap<>(5);

    //获取文件列表
    public static String[] getFileList() {
        //全局的File实例
        File file = new File(FILE_PATH);
        if (file == null) {
            return null;
        }
        File[] files = file.listFiles();
        String[] fileNameList = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNameList[i] = files[i].getName();
        }
        return fileNameList;
    }

    //读取某文件内容,映射文件名与文件内容的关系
    public static void getContentFromFile(String fileName) throws FileNotFoundException {
        FileMap.put(fileName, readToString(FILE_PATH + "/" + fileName));
    }

    public static String readToString(String fileName) throws FileNotFoundException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        //获取文件大小
        Long fileLength = file.length();
        //将文件大小转换成数组
        byte[] fileContent = new byte[fileLength.intValue()];
        FileInputStream in = null;
        try {
            //文件输入流
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return new String(fileContent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    //新建一个文件
    public static boolean createNewFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        File file = new File(FILE_PATH + "/" + fileName);
        //文件是否存在
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
