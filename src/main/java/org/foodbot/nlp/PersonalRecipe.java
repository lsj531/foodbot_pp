package org.foodbot.nlp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.foodbot.domain.FoodVO;
import org.foodbot.service.FoodService;

/*
 * 
 * 채팅 입력에 대한 최종적인 모든 속성값을 가진
 */
public class PersonalRecipe implements Recipe {

	@Inject
	FoodService fservice;
	
	private List<String> RMajorAttrList;
//	private List<String> RSubAttrList;
	private List<String> RTasteAttrList;
	private List<String> RAttrList;
	private List<FoodVO> flist;
	
	private MorpProcess mp;
	
	public PersonalRecipe() {}
	public PersonalRecipe(FoodService fservice,MorpProcess mp) {
		this.fservice = fservice;
		this.mp = mp;
		RMajorAttrList = new ArrayList<String>();
//		RSubAttrList = new ArrayList<String>();
		RTasteAttrList = new ArrayList<String>();
		RAttrList = new ArrayList<String>();
		
	}
	

	public void setRAttrRecipe() throws Exception {
		for(int i=0 ; i<mp.getWordList().size() ; i++) {
			System.out.println(mp.getWordList().get(i));
		}
		for(int i=0 ; i<mp.getWordList().size() ; i++) {
			flist = fservice.readRecipe(mp.getWordList().get(i));
			setRAttrList(flist);
		}
	}
	
	// 재료/맛의 각각 구분자를 나누어 각각 배열에 넣는다.
	@Override
	public void setRAttrList(List<FoodVO> flist) throws Exception {
		String[] majorStr= {""};
		String[] tasteStr={""};
//		String[] subStr = {""};
		String[] rStr = {""};
		String[] temp = {""};
		
		// Recipe num
		for(int i=0 ;i<flist.size() ; i++) {
			if(flist.get(i).getIngredset() != null) {
				temp = flist.get(i).getIngredset().split("\\|");
				majorStr = temp[0].split(",");
//				subStr = temp[1].split(",");
			}
			if(flist.get(i).getTasteset() != null) {
				tasteStr = flist.get(i).getTasteset().split(",");
			}
			inputRAttr(majorStr,tasteStr);
		}
	}
	
	private void inputRAttr(String[] majorStr, String[] tasteStr) {
		for(int i=0 ; i<majorStr.length ; i++) {
			if(!majorStr[i].trim().equals("") && !majorStr[i].trim().equals(null)) {
				RMajorAttrList.add(majorStr[i]);
//				RAttrList.add(rStr[i]);
			}
		}
		/*
		for(int i=0 ; i<subStr.length ; i++) {
			if(!subStr[i].trim().equals("") && !subStr[i].trim().equals(null)) {
				RSubAttrList.add(subStr[i]);
//				RAttrList.add(rStr[i]);
			}
		}*/
		for(int i=0 ; i<tasteStr.length ; i++) {
			if(!tasteStr[i].trim().equals("") && !tasteStr[i].trim().equals(null)) {
				RTasteAttrList.add(tasteStr[i]);
//				RAttrList.add(rStr[i]);
			}
		}
	}
	@Override
	public List<String> getMajorRecipe() throws Exception {
		return RMajorAttrList;
	}
	@Override
	public List<String> getSubRecipe() throws Exception {
		return null;
	}
	@Override
	public List<String> getTasteRecipe() throws Exception {
		return RTasteAttrList;
	}
	@Override
	public List<String> getRecipe() throws Exception {
		return RAttrList;
	}
}
