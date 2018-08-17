package com.pxq.corelibrary.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ê××ÖÄ¸ºÍÆ´ÒôÌáÈ¡Àà
 * @author pxq
 * @date 2018Äê7ÔÂ27ÈÕ
 */
public class LetterUtil {

	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
			4390, 4558, 4684, 4925, 5249, 5590 };

	private final static String[] lc_FirstLetter = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "w", "x",
			"y", "z" };

	private static Map<String, String> exceptWords = new HashMap<String, String>();
	static {
		exceptWords.put("a", "âÖ÷¡");
		exceptWords.put("b", "èµÙñ²¢‚N±ğ„ö");
		exceptWords.put("c", "ÜİØ©EP„V");
		exceptWords.put("d", "äÂí¸é¦ÙÙ¶ª");
		exceptWords.put("e", "");
		exceptWords.put("f", "ÚúƒìƒÀğ¥");
		exceptWords.put("g", "áÄŞ»İ¸Ø¤O_xœƒé„÷„ø");
		exceptWords.put("h", "æèçõäêäÒCƒê…Iä°‹Ö");
		exceptWords.put("j", "ãşòÔôßçÆìºÜìÛ²Ø¢LMNS|³ƒ×„û…E…GõÓ");
		exceptWords.put("k", "@…H");
		exceptWords.put("l", "áÀäµèïäàäğä¯ñçõ·ãòãÏáÁI„c„Ã†ªéç");
		exceptWords.put("m", "äÅãèDƒØƒíƒÁƒÓØÂ");
		exceptWords.put("n", "");
		exceptWords.put("o", "ê±");
		exceptWords.put("p", "Úüå§Û¯Ø§¬ƒë");
		exceptWords.put("q", "ÇÇôëáéáªëÔÚöHãÜ…ƒîƒ¿…J");
		exceptWords.put("r", "éÅ„U");
		exceptWords.put("s", "ãôî¡ãğáÓì¨İ·áÔÛ·AF‚Lƒ¾");
		exceptWords.put("t", "äüëøÛ°‡‚K‚M");
		exceptWords.put("w", "æÄä¶ãë“©…d…eŞ±");
		exceptWords.put("x", "öÎíìä±ÜşäÀä»ÙôB²Ğ×");
		exceptWords.put("y", "Ü²íô÷ğò£ÙğÛ©ÙÈÛ³êÌJ„±‚©ƒÒ…FîÚ");
		exceptWords.put("z", "è÷äÃÚ¯èÏïöÛÚ«ëĞ„d");
	}

	private final static String polyphoneTxt = "ÖØÇì|cq,ÒôÀÖ|yy,ÃñÀÖ|my,ÉùÀÖ|my";


	/**
	 * È¡µÃ¸ø¶¨ºº×Ö´®µÄÊ××ÖÄ¸´®,¼´ÉùÄ¸´®
	 * 
	 * @param str ¸ø¶¨ºº×Ö´®
	 * @return ÉùÄ¸´®
	 */
	public static String getAllFirstLetter(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}
		if(str.endsWith("´«"))
			str = str.replace("´«", "z");
		// ¶àÒô×ÖÅĞ¶¨
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
	 * È¡µÃ¸ø¶¨ºº×ÖµÄÊ××ÖÄ¸,¼´ÉùÄ¸
	 * 
	 * @param chinese ¸ø¶¨µÄºº×Ö
	 * @return ¸ø¶¨ºº×ÖµÄÉùÄ¸
	 */
	public static String getFirstLetter(String chinese) {
		if (chinese == null || chinese.trim().length() == 0) {
			return "";
		}
		
		String chineseTemp = chinese;
		chinese = conversionStr(chinese, "GB2312", "ISO8859-1");

		if (chinese.length() > 1) {
			// ÅĞ¶ÏÊÇ²»ÊÇºº×Ö
			int li_SectorCode = (int) chinese.charAt(0); // ºº×ÖÇøÂë
			int li_PositionCode = (int) chinese.charAt(1); // ºº×ÖÎ»Âë
			li_SectorCode = li_SectorCode - 160;
			li_PositionCode = li_PositionCode - 160;
			int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // ºº×ÖÇøÎ»Âë
			if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
				for (int i = 0; i < 23; i++) {
					if (li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1]) {
						chinese = lc_FirstLetter[i];
						break;
					}
				}
			} else {
				// ·Çºº×Ö×Ö·û,ÈçÍ¼ĞÎ·ûºÅ»òASCIIÂë
				chinese = matchPinYin(chinese);
			}
		}

		// Èç»¹ÊÇÎŞ·¨Æ¥Åä£¬ÔÙ´Î½øĞĞÆ´ÒôÆ¥Åä
		if (chinese.equals("?")) {
			chinese = matchPinYin(chineseTemp, false);
		}

		return chinese;
	}

	/**
	 * ºº×ÖÆ¥ÅäÆ´Òô¶ÔÕÕ
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
	 * ×Ö·û´®±àÂë×ª»»
	 * 
	 * @param str Òª×ª»»±àÂëµÄ×Ö·û´®
	 * @param charsetName Ô­À´µÄ±àÂë
	 * @param toCharsetName ×ª»»ºóµÄ±àÂë
	 * @return ¾­¹ı±àÂë×ª»»ºóµÄ×Ö·û´®
	 */
	private static String conversionStr(String str, String charsetName, String toCharsetName) {
		try {
			str = new String(str.getBytes(charsetName), toCharsetName);
		} catch (UnsupportedEncodingException ex) {
			System.out.println("×Ö·û´®±àÂë×ª»»Òì³££º" + ex.getMessage());
		}
		return str;
	}

	public static void main(String[] args) {
		String content = "ãÜ";
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
		System.out.println("»ñÈ¡Æ´ÒôÊ××ÖÄ¸£º" + contentAll + ":" + pinyinAll);
		// String address = "(½ğäºĞ¡Çø)ÆÜÉ½Â·1689Åª";
		// address = address.substring(address.indexOf(")") + 1);
		// System.out.println("»ñÈ¡Æ´ÒôÊ××ÖÄ¸£º" + LetterUtil.getFirstLetter(address));
	}

	@SuppressWarnings("unused")
	private static void findRepeatWord(Map<String, String> wordsMap) {
		String words = wordsMap.values().toString().replaceAll("[, ]", "");
		words = words.substring(1, words.length() - 1);
		for (char word : words.toCharArray()) {
			int count = findStr2(words, String.valueOf(word));
			if (count > 1) {
				System.out.println("ºº×Ö£º¡¾" + word + "¡¿³öÏÖÁË" + count + "´Î£¡");
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