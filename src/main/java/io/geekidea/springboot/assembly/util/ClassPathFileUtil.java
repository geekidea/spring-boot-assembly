package io.geekidea.springboot.assembly.util;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

/**
 * <p>
 *  将resources中的配置文件打包到外部config目录后，不能正常读取文件，请使用该工具类读取
 *  1.开发时，在工具中启动，使用ClassPah类获取到目标文件的觉得路径
 *  2.打包到config目录后，找到config目录下的目标文件的绝对路径
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/27
 */
public class ClassPathFileUtil {
    private static final String USER_DIR = "user.dir";
    private static final String BIN = "bin";
    private static final String CONFIG = "config";

    /**
     * 获取文件
     * @param fileName 文件名称
     * @return
     */
    public static File getFile(String fileName) throws IOException {
        // 获取当前执行启动命令的路径
        String userDir = System.getProperty(USER_DIR);
        System.out.println("user.dir:"+userDir);

        File targetFile;
        // 打包后启动时，路径以\\bin结束：E:\\github\\spring-boot-assembly\\target\\spring-boot-assembly\\bin
        // 执行startup.bat或者startup.sh命令的路径，默认目录是bin，如果不是，请修改
        if (userDir.endsWith(BIN)){
            File file = new File(userDir);
            // 获取E:\\github\\spring-boot-assembly
            File parentFile = file.getParentFile();
            // 获取配置文件目录，默认配置文件目录是config，如果不是，请修改
            File configDir = new File(parentFile,CONFIG);
            // 目标文件
            targetFile = new File(configDir,fileName);
        }else{
            // 工具中启动
            // E:\github\spring-boot-assembly\target\classes\test.jks
            ClassPathResource classPathResource = new ClassPathResource(fileName);
            targetFile = classPathResource.getFile();
        }
        System.out.println("targetFile = " + targetFile);
        return targetFile;
    }
}
