package com.pxq.corelibrary.utils;

public class BitmapUtil {

	private int getSampleSize(int width, int height, int reqWidth, int reqHeight) {
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			// ʹ����Ҫ�Ŀ�ߵ����ֵ���������
			final int suitedValue = reqHeight > reqWidth ? reqHeight : reqWidth;
			final int heightRatio = Math.round((float) height
					/ (float) suitedValue);
			final int widthRatio = Math.round((float) width
					/ (float) suitedValue);

			inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;// �����
		}
		return inSampleSize;
	}
}
