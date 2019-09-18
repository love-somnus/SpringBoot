package com.somnus.guava;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Kevin
 * @packageName com.somnus.guava
 * @title: IoTest
 * @description: TODO
 * @date 2019/6/14 13:08
 */
public class IoTest {

    @Test
    @SneakyThrows(IOException.class)
    public void files() {
        File file = new File("src/main/resources/build.xml");
        List<String> readLines = Files.readLines(file, Charsets.UTF_8);
        System.out.println(readLines);
        System.out.println(Files.toString(file, Charsets.UTF_8));
    }

    @Test
    @SneakyThrows(IOException.class)
    public void resources() {
        URL url = Resources.getResource("build.xml");
        List<String> lines = Resources.asCharSource(url, Charsets.UTF_8).readLines();
        System.out.println(lines);

        URL url2 = new URL("http://www.baidu.com/");
        List<String> lines2 = Resources.asCharSource(url2, Charsets.UTF_8).readLines();
        System.out.println(lines2);

        URL url3 = new URL("http://www.baidu.com/");
        String lines3 = Resources.toString(url3, Charsets.UTF_8);
        System.out.println(lines3);
    }
}
