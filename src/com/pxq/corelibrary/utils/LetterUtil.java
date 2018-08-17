package com.pxq.corelibrary.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����ĸ��ƴ����ȡ��
 * @author pxq
 * @date 2018��7��27��
 */
public class LetterUtil {

	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
			4390, 4558, 4684, 4925, 5249, 5590 };

	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x",
			"y", "z" };

	private static Map<String, String> exceptWords = new HashMap<String, String>();
	static {
		exceptWords.put("a", "����");
		exceptWords.put("b", "��񲢂N����");
		exceptWords.put("c", "��ة�E�P�V");
		exceptWords.put("d", "�����ٶ�");
		exceptWords.put("e", "");
		exceptWords.put("f", "������");
		exceptWords.put("g", "��޻ݸؤ�O�_�x�������");
		exceptWords.put("h", "�������ҁC��I䰋�");
		exceptWords.put("j", "�����������۲آ�L�M�N�S�|���ׄ��E�G��");
		exceptWords.put("k", "�@�H");
		exceptWords.put("l", "���������������������I�c�Æ���");
		exceptWords.put("m", "����D�؃������");
		exceptWords.put("n", "");
		exceptWords.put("o", "�");
		exceptWords.put("p", "���ۯا����");
		exceptWords.put("q", "������������H�܁���J");
		exceptWords.put("r", "�ńU");
		exceptWords.put("s", "��������ݷ��۷�A�F�L��");
		exceptWords.put("t", "����۰���K�M");
		exceptWords.put("w", "����끓���d�eޱ");
		exceptWords.put("x", "������������B����");
		exceptWords.put("y", "ܲ�������۩��۳�́J�������҅F��");
		exceptWords.put("z", "����گ�����ځ��Єd");
	}

	private final static String polyphoneTxt = "����|cq,����|yy,����|my,����|my";


	/**
	 * ȡ�ø������ִ�������ĸ��,����ĸ��
	 * 
	 * @param str �������ִ�
	 * @return ��ĸ��
	 */
	public static String getAllFirstLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}
		if(str.endsWith("��"))
			str = str.replace("��", "z");
		// �������ж�
		for (String polyphone : polyphoneTxt.split(",")) {
			String[] chinese = polyphone.split("[|]");
			if (str.indexOf(chinese[0]) != -1) {
				str = str.replace(chinese[0], chinese[1]);
			}
		}

		String _str = "";
		for (int i = 0; i < str.length(); i++) {
			_str = _str + getFirstLetter(str.substring(i, i + 1));
		}

		return _str;
	}

	/**
	 * ȡ�ø������ֵ�����ĸ,����ĸ
	 * 
	 * @param chinese �����ĺ���
	 * @return �������ֵ���ĸ
	 */
	public static String getFirstLetter(String chinese) {
		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}
		
		String chineseTemp = chinese;
		chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

		if (chinese.length() > 1) {
			// �ж��ǲ��Ǻ���
			int li_SectorCode = (int) chinese.charAt(0); // ��������
			int li_PositionCode = (int) chinese.charAt(1); // ����λ��
			li_SectorCode = li_SectorCode - 160;
			li_PositionCode = li_PositionCode - 160;
			int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // ������λ��
			if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
				for (int i = 0; i < 23; i++) {
					if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
						chinese = lc_FirstLetter[i];
						break;
					}
				}
			} else {
				// �Ǻ����ַ�,��ͼ�η��Ż�ASCII��
				chinese = matchPinYin(chinese);
			}
		}

		// �绹���޷�ƥ�䣬�ٴν���ƴ��ƥ��
		if (chinese.equals("?")) {
			chinese = matchPinYin(chineseTemp, false);
		}

		return chinese;
	}

	/**
	 * ����ƥ��ƴ������
	 * 
	 * @param chinese
	 * @return
	 */
	private static String matchPinYin(String chinese, boolean needConvert) {
		String chineseTemp = chinese;
		if (needConvert) {
			chinese = conversionStr(chinese, "ISO8859-1", "GB2312");
		}
		chinese = chinese.substring(0, 1);

		// findRepeatWord(exceptWords);

		for (Entry<String, String> letterSet : exceptWords.entrySet()) {
			if (letterSet.getValue().indexOf(chinese) != -1) {
				chinese = letterSet.getKey();
				break;
			}
		}
		chinese = chineseTemp.equals(chinese) ? "?" : chinese;
		return chinese;
	}

	private static String matchPinYin(String chinese) {
		return matchPinYin(chinese, true);
	}

	/**
	 * �ַ�������ת��
	 * 
	 * @param str Ҫת��������ַ���
	 * @param charsetName ԭ���ı���
	 * @param toCharsetName ת����ı���
	 * @return ��������ת������ַ���
	 */
	private static String conversionStr(String str, String charsetName, String toCharsetName) {
		try {
			str = new String(str.getBytes(charsetName), toCharsetName);
		} catch (UnsupportedEncodingException ex) {
			System.out.println("�ַ�������ת���쳣��" + ex.getMessage());
		}
		return str;
	}

	public static void main(String[] args) {
		String content = "��";
		String pinyin = "";
		String contentAll = "";
		String pinyinAll = "";
		for (int i = 21000; i <= 22000; i++) {
			content = ((char) i) + "";
			pinyin = LetterUtil.getFirstLetter(content);
			if (pinyin.equals("?")) {
				contentAll += content;
				pinyinAll += pinyin;
			}
		}
		System.out.println("��ȡƴ������ĸ��" + contentAll + ":" + pinyinAll);
		// String address = "(���С��)��ɽ·1689Ū";
		// address = address.substring(address.indexOf(")") + 1);
		// System.out.println("��ȡƴ������ĸ��" + LetterUtil.getFirstLetter(address));
	}

	@SuppressWarnings("unused")
	private static void findRepeatWord(Map<String, String> wordsMap) {
		String words = wordsMap.values().toString().replaceAll("[, ]", "");
		words = words.substring(1, words.length() - 1);
		for (char word : words.toCharArray()) {
			int count = findStr2(words, String.valueOf(word));
			if (count > 1) {
				System.out.println("���֣���" + word + "��������" + count + "�Σ�");
			}
		}
	}

	private static int findStr2(String srcText, String keyword) {
		int count = 0;
		Pattern p = Pattern.compile(keyword);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}
}