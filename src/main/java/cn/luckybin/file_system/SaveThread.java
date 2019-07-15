package cn.luckybin.file_system;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @ClassName SaveThread
 * @Author liuyunbin
 * @Date 2019/7/15 14:37
 * @Description TODO 将文件保存到本地
 */
@Slf4j
public class SaveThread implements Runnable {

    @Override
    public void run() {
        for (Map.Entry<String, String> m : FileSystem.FileMap.entrySet()) {
            writeToFile(FileSystem.FILE_PATH + "/" + m.getKey(), m.getValue());
        }
    }


    public static void writeToFile(String fileName, String content) {
        File file = new File(fileName);
        Writer out = null;
        try {
            out = new FileWriter(file);
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("保存文件时失败");
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
