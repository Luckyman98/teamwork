package cn.luckybin.Controller;

import cn.luckybin.file_system.FileSystem;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName FileController
 * @Author liuyunbin
 * @Date 2019/7/15 11:52
 * @Description TODO
 */
@RestController
public class FileController {

    //获取文件列表(下拉框)
    @RequestMapping(value = "list/file", method = RequestMethod.GET)
    public Object getFileList() {
        String[] fileNamelist = FileSystem.getFileList();
        return new ResponseData(1, "成功", fileNamelist);
    }

    @PostMapping(value = "create/file")
    public Object createFile(@RequestBody Map<String, String> map) {
        String newFileName = map.get("newFileName");
        //在本地新建一个文件
        boolean res = FileSystem.createNewFile(newFileName);
        if (res) {
            return new ResponseData(1, "成功");
        }
        return new ResponseData(1, "失败");
    }
}
