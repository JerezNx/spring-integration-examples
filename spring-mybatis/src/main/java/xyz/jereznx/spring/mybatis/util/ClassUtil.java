package xyz.jereznx.spring.mybatis.util;

import lombok.extern.slf4j.Slf4j;
import xyz.jereznx.spring.mybatis.domain.BaseEnum;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author liqilin
 * @since 2020/11/28 14:27
 */
@Slf4j
public class ClassUtil {

    private static final String TYPE_JAR = "jar";
    private static final String TYPE_FILE = "file";

    /**
     * 获取接口的所有实现
     *
     * @param clazz 接口class
     * @return 所有实现class
     */
    public static List<Class<?>> getAllAssignedClass(Class<?> clazz) {
        List<Class<?>> list = new ArrayList<>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                List<Class<?>> allClass = getAllClass(clazz.getPackage().getName());
//               循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                for (Class<?> aClass : allClass) {
//                    判断是不是同一个接口
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (clazz.isAssignableFrom(aClass)) {
                        if (!clazz.equals(aClass)) {
                            // 自身并不加进去
                            list.add(aClass);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("出现异常" + e.getMessage());
            }
        }
        log.debug("class list size :" + list.size());
        return list;
    }


    /**
     * 从一个指定路径下查找所有的类
     *
     * @param packageName 包名
     * @return 包下所有class
     */
    private static List<Class<?>> getAllClass(String packageName) {
        List<String> classNameList = getClassName(packageName);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (String className : classNameList) {
            try {
                list.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("load class from name failed:" + className + e.getMessage());
            }
        }
        log.debug("find list size :" + list.size());
        return list;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    private static List<String> getClassName(String packageName) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            log.debug("file type : " + type);
            if (TYPE_FILE.equals(type)) {
                String fileSearchPath = url.getPath();
                log.debug("fileSearchPath: {}", fileSearchPath);
//                如果下面放开，可以扫描classPath下所有class，这样枚举实现不必和接口放在同一包下，不过建议还是放一起
//                fileSearchPath = fileSearchPath.substring(0, fileSearchPath.indexOf("/classes") + "/classes".length());
                log.debug("fileSearchPath: {}", fileSearchPath);
                fileNames = getClassNameByFile(fileSearchPath);
            } else {
                if (TYPE_JAR.equals(type)) {
                    try {
                        JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                        fileNames = getClassNameByJar(jarFile);
                    } catch (IOException e) {
                        throw new RuntimeException("open Package URL failed：" + e.getMessage());
                    }
                } else {
                    throw new RuntimeException("file system not support! cannot load MsgProcessor！");
                }
            }
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath 文件路径
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath()));
                } else {
                    String childFilePath = childFile.getPath();
                    if (childFilePath.endsWith(".class")) {
                        childFilePath = childFilePath.substring(childFilePath.indexOf(File.separator + "classes") + 9, childFilePath.lastIndexOf("."));
                        childFilePath = childFilePath.replace(File.separator, ".");
                        myClassName.add(childFilePath);
                    }
                }
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(JarFile jarFile) {
        List<String> myClassName = new ArrayList<>();
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace(File.separator, ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("发生异常:" + e.getMessage());
        }
        return myClassName;
    }

    /**
     * java -cp ".;*;lib/*" xyz.jereznx.spring.mybatis.util.ClassUtil
     */
    public static void main(String[] args) {
        final List<Class<?>> allAssignedClass = ClassUtil.getAllAssignedClass(BaseEnum.class);
        System.out.println(allAssignedClass);
    }

}

