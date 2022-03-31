package com.zjx.hencoderstudy;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import okio.BufferedSource;
import okio.Okio;

public class IOTest {
    @Test
    public void run() {
//        io1();
//        io2();
//        io3();
//        io4();
//        io5();
//        io6();
//        io7();
//        io8();
        io9();
    }

    private void io1 () {
        try (FileOutputStream outputStream = new FileOutputStream("./test.txt")){
            outputStream.write('a');
            outputStream.write('b');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io2 () {
        try(InputStream inputStream = new FileInputStream("./test.txt")) {
            System.out.println(inputStream.read());
            System.out.println(inputStream.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io3() {
        try (Writer writer = new FileWriter("./test.txt")){
            writer.write('c');
            writer.write('d');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io4 () {
        try (Reader reader = new FileReader("./test.txt")) {
            System.out.println(reader.read());
            System.out.println(reader.read());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io5() {
//        try (Writer writer = new FileWriter("./test.txt");
//             BufferedWriter bufferedWriter = new BufferedWriter(writer)){
//            bufferedWriter.write("abcd");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (OutputStream outputStream = new FileOutputStream("./test.txt");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)){
            bufferedOutputStream.write('a');
            bufferedOutputStream.write('a');
            bufferedOutputStream.write('a');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io6 () {
//        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("./test.txt"))){
//            bufferedInputStream.read();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./test.txt"))){
            System.out.println(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io7 () {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("./test.txt"));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("./test1.txt"))){
            byte[] bytes = new byte[1024];
            int count = -1;
            while ((count = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, count);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io8 () {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("./test.txt", "r");
             FileChannel channel = randomAccessFile.getChannel()){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                System.out.println(Charset.defaultCharset().decode(byteBuffer));
                byteBuffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void io9() {
        try (BufferedSource source = Okio.buffer(Okio.source(new File("./test.txt")))){
            System.out.println(source.readUtf8Line());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
