package com.pxq.corelibrary.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

public class FileUtils {
	
	/**
	 * ��ȡ�ļ��ı��뷽ʽ
	 * 
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(
                  new FileInputStream(file));
            bis.mark(4);  //��ǣ������ظ���ȡǰ4���ֽ�
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
                == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
                    == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            Log.d("pxq_code", charset);
            bis.reset();  //���������ٴζ�ȡǰ4���ֽ�
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    //��������BF���µģ�Ҳ����GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// ˫�ֽ� (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),Ҳ������GBK������
                            continue;
                        else
                            break;
                     // Ҳ�п��ܳ������Ǽ��ʽ�С
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                System.out.println(loc + " " + Integer.toHexString(read));
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("pxq_code", charset);
        return charset;
    }
	
	/**
	 * @Title: formatSize
	 * @Description: format size to byte Mb Gb etc
	 * @param ctx
	 * @param number
	 * @return
	 * Created by panxq on ����5:26:26 2017-10-23
	 */
	public static String formatSize(Context ctx, long number){
		return Formatter.formatFileSize(ctx, number);
	}

}
