package org.foodbot.nlp;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.foodbot.domain.FoodVO;
import org.foodbot.domain.IngredVO;
import org.foodbot.domain.MorpVO;
import org.foodbot.domain.TasteVO;
import org.foodbot.service.FoodService;

import kr.co.shineware.util.common.model.Pair;

public class MorpSeparate {

	private FoodVO foodVO;
	private IngredVO ingredVO;
	private MorpVO morpVO;
	private TasteVO tasteVO;

	// 형태소 벡터 코드 리스트
	private ArrayList<String> morpList;
	// 형태소 리스트
	private ArrayList<String> wordList;

	private MorpProcess mp;

	@Inject
	private FoodService service;

	@Inject
	private KomoranService komoranService;

	public MorpSeparate() {}

	public MorpSeparate(FoodService service, KomoranService komoranService) {
		this.service = service;
		this.komoranService = komoranService;

		morpList = new ArrayList<String>();
		wordList = new ArrayList<String>();
	}

	public void setComoran(String msg) throws Exception {
		// 파일 로드 디렉토리
		// C:\sts\work\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\FoodBot\WEB-INF\classes\nlp

		List<List<Pair<String, String>>> result = komoranService.getKomoran().analyze(msg);

		for (List<Pair<String, String>> eojeolResult : result) {
			for (Pair<String, String> wordMorph : eojeolResult) {
				System.out.println(wordMorph.getFirst() + " " + wordMorph.getSecond());
				String word = wordMorph.getFirst();
				String morp = wordMorph.getSecond();
				char chosung = getFirstElement(word);
				if (morp.equalsIgnoreCase("nng")) {
					getWordNNG(word);
				} else {
					getWordNNG(word);
				}
			}
			System.out.println();
		}
		// 0000 0000 0000 0000
		// 나라1 공란1 형태소2 / 속성4 / 재료(1)2 재료(2)2 / 개수4
		/*
		 * 1. 음식/재료 속성확인 및 처리 2. 형태소 처리.
		 * 0. 예외처리. 1. 형태소 중에 음식 / 재료 코드 부여 2. 부사처리 3.
		 */
		mp = new MorpProcess(morpList, wordList);
		System.out.println("mp >> "+mp);
	}

	//음식 / 재료 / NNG / morp 순으로 검색 모두 사용자 사전에서 검색
	private void getWordNNG(String word) throws Exception {
		List<FoodVO> foodVOList = service.readFname(word);
		System.out.println("foodVOList.size() > "+foodVOList.size());
		if (foodVOList.size() != 0) {
			morpList.add(foodVOList.get(0).getFcode());
			wordList.add(word);
		} else {
			ingredVO = service.readIngredOne(word);
			if (ingredVO != null) {
				morpList.add(ingredVO.getIngred_code());
				wordList.add(word);
			} else {
				foodVO = service.readNNGOne(word);
				if (foodVO != null) {
					morpList.add(foodVO.getFcode());
					wordList.add(word);
				} else {
					morpVO = service.readMorpOne(word);
					if (morpVO != null) {
						morpList.add(morpVO.getMorp_code());
						wordList.add(word);
					} else {
						tasteVO = service.readTasteOne(word);
						if (tasteVO != null) {
							morpList.add(tasteVO.getTaste_code());
							wordList.add(word);
						}
					}
				}
			}
		}
	}

	private void getWord(String word) throws Exception {
		morpVO = service.readMorpOne(word);
		if (morpVO != null)
			morpList.add(morpVO.getMorp_code());
	}

	// 초성
	private static final char[] firstSounds = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ',
			'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

	/*
	 * 문자열의 첫번째 요소를 추출 (한글일 경우 초성을 추출)
	 * 
	 * @param str 첫번째 문자의 요소를 추출할 문자열
	 * @return 첫번째 요소 (한글일 경우 첫번째 문자의 자음)
	 */
	public static char getFirstElement(String str) {
		if (str == null)
			return '\u0000';

		int len = str.length();
		if (len == 0)
			return '\u0000';

		return getFirstElement(str.charAt(0));
	}

	/*
	 * 한글 문자의 초성을 추출
	 * 
	 * @param c 첫번째 문자의 요소를 추출할 문자열
	 * @return 한글 문자의 초성
	 */
	public static char getFirstElement(char c) {
		if (!isHangul(c))
			return c;
		return firstSounds[(c - 0xAC00) / (21 * 28)];
	}

	/*
	 * 문자 하나가 한글인지 검사
	 * 
	 * @param c 검사 하고자 하는 문자
	 * @return 한글 여부에 따라 'true' or 'false'
	 */
	public static boolean isHangul(char c) {
		if (c < 0xAC00 || c > 0xD7A3)
			return false;
		return true;
	}

	public String[] splitLine(String line) {
		String[] str = line.split(",");
		return str;
	}

	public MorpProcess getMp() {
		return mp;
	}

	public void setMp(MorpProcess mp) {
		this.mp = mp;
	}
}