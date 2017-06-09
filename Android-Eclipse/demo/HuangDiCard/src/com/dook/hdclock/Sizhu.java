package com.dook.hdclock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;



public class Sizhu {
	public static String getDate(Calendar date){
		return date.get(Calendar.YEAR)+"-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_MONTH)
		+"-" + date.get(Calendar.HOUR_OF_DAY);
	}
	public static String getGua(Calendar date,String dates){
		//StringBuilder mess = new StringBuilder("您的八字：\n" + get(date) + "\n命中运程: \n");
		return getGua(get(date),dates);
	}
	public static String agetGua(String  bz,Calendar other){
		StringBuilder mess = new StringBuilder("");
		if (alunTianYiGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的天乙贵人。能帮助你逢凶化吉。助你解困。助你受众人人拥护。\n");
		}
		if (alunTaiJiGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的太极贵人。能帮助你为人正直，做事专一，有始有终，是你人生中最好的贵人。\n");
		}
		if(alunTianDeGuiRen(bz,other) || alunYueDeGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的天德月德贵人。能帮助你逢凶化吉，有灾自解。\n");
		}
		
		
		if(alunFuXingGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的福星贵人。能帮助你一生福禄无缺，多福多寿，衣食无忧。\n");
		}
		
		if (alunWenChangGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的文昌贵人。能帮助你好学上进，得官进禄。\n");
		}
		
		
		
		if (alunGuoYinGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的国印贵人。能帮助你能帮助你严守清规，照章办事，办事公道，不失为良师益友。\n");
		}
		
		/*if (alunDeXiuGuiRen(bz,other)) {
			mess.append("\n德秀贵人：赋性聪明，温厚和气，主内涵充实，精神爽朗，仪表清奇，才华出众。\n");
		}*/
		
		if(alunYiMa(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的驿马星。能帮助你解决出差行程方面的问题。\n");
		}
		
		
		
		
		
		if (alunTianYi(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的天医星。能帮助你从事医学、心理和哲学方面的研究。\n");
		}
		
		if (alunLuShen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的禄神星。能帮助你升官发财，好运连绵。\n");
		}
		
		
		/*if (alunTianShe(bz,other)) {
			mess.append("\n天赦：逢凶化吉，解人灾祸，有救，尤其对罪人，有得黄帝大赦的机会。");
		}*/
		
		if (alunXianChi(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的咸池星（桃花星）。能帮助你结识异性，特别适合做知己。\n");
		}
		
		if (alunYangRenzhiSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的羊刃之煞星。能帮助你消灾解难，但有时也会给你惹麻烦。\n");
		}
		
		if (alunJieSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的劫煞星。能帮助你避祸患，救急，有时也会给你痛失钱财。\n");
		}
		
		if (alunZaiSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的灾煞星。能帮助你消灾解难，有时也会福少祸相连。\n");
		}
		
		if (alunGuChenGuaSu(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的孤辰寡宿星。能帮助你解除寂寞，但有时也会令你孤独。\n");
		}
		
		if (alunKongWang(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的六甲空亡星。能帮助你从消极情绪中解救出来，有时也会给你泼冷水，头脑发热。时好时坏。建议你择善从之。\n");
		}
		
		if (alunShiEDaBai(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的十恶大败星。能帮助你抑恶从善，有时也会为非作歹，学懂法律有益对方。\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (alunYinYangChaCuo(bz,other)) {
			mess.append("\n度客人生导航温馨提示：他是你的阴差阳错星。能帮助你纠正错误，但也会产生婚配不利。\n");
		}
		
		/*if (lunSiFei(bz)) {
			mess.append("\n四废：做事无成，有始无终。身弱多病而无能，主伤残。官司口舌，甚至牢狱之灾，或为僧道之人。\n");
		}*/
		
		//mess.append("\n" + wuxing(bz,get(new GregorianCalendar())));
		return mess + "";
	}
	public static String agetGua2a(String  bz,Calendar other){
		StringBuilder mess = new StringBuilder("");
		if (alunTianYiGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的天乙贵人日。能帮助你逢凶化吉。助你解困。助你受众人人拥护。\n");
		}
		if (alunTaiJiGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的太极贵人日。能帮助你为人正直，做事专一，有始有终，是你人生中最好的贵人。\n");
		}
		if(alunTianDeGuiRen(bz,other) || alunYueDeGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的天德月德贵人日。能帮助你逢凶化吉，有灾自解。\n");
		}
		
		
		if(alunFuXingGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的福星贵人日。能帮助你一生福禄无缺，多福多寿，衣食无忧。\n");
		}
		
		if (alunWenChangGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的文昌贵人日。能帮助你好学上进，得官进禄。\n");
		}
		
		
		
		if (alunGuoYinGuiRen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的国印贵人日。能帮助你能帮助你严守清规，照章办事，办事公道，不失为良师益友。\n");
		}
		  
		/*if (alunDeXiuGuiRen(bz,other)) {
			mess.append("\n德秀贵人：赋性聪明，温厚和气，主内涵充实，精神爽朗，仪表清奇，才华出众。\n");
		}*/
		
		if(alunYiMa(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的驿马星日。能帮助你解决出差行程方面的问题。\n");
		}
		
		
		
		
		
		if (alunTianYi(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的天医星日。能帮助你从事医学、心理和哲学方面的研究。\n");
		}
		
		if (alunLuShen(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的禄神星日。能帮助你升官发财，好运连绵。\n");
		}
		
		
		/*if (alunTianShe(bz,other)) {
			mess.append("\n天赦：逢凶化吉，解人灾祸，有救，尤其对罪人，有得黄帝大赦的机会。");
		}*/
		
		if (alunXianChi(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的咸池星（桃花星）日。能帮助你结识异性，特别适合做知己。\n");
		}
		
		if (alunYangRenzhiSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的羊刃之煞星日。能帮助你消灾解难，但有时也会给你惹麻烦。\n");
		}
		
		if (alunJieSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的劫煞星日。能帮助你避祸患，救急，有时也会给你痛失钱财。\n");
		}
		
		if (alunZaiSha(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的灾煞星日。能帮助你消灾解难，有时也会福少祸相连。\n");
		}
		
		if (alunGuChenGuaSu(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的孤辰寡宿星日。能帮助你解除寂寞，但有时也会令你孤独。\n");
		}
		
		if (alunKongWang(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的六甲空亡星日。能帮助你从消极情绪中解救出来，有时也会给你泼冷水，头脑发热。时好时坏。建议你择善从之。\n");
		}
		
		if (alunShiEDaBai(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的十恶大败星日。能帮助你抑恶从善，有时也会为非作歹，学懂法律有益对方。\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (alunYinYangChaCuo(bz,other)) {
			mess.append("\n度客人生导航温馨提示：今天是你的阴差阳错星日。能帮助你纠正错误，但也会产生婚配不利。\n");
		}
		
		/*if (lunSiFei(bz)) {
			mess.append("\n四废：做事无成，有始无终。身弱多病而无能，主伤残。官司口舌，甚至牢狱之灾，或为僧道之人。\n");
		}*/
		
		//mess.append("\n" + wuxing(bz,get(new GregorianCalendar())));
		return mess + "";
	}
	public static String getGua(String  bz,String date){
		StringBuilder mess = new StringBuilder("您的八字：\n\n" + bz + "(" + date + ")"  +"\n\n命中运程: \n");
		if (lunTianYiGuiRen(bz)) {
			mess.append("\n天乙贵人：有贵人星，遇事有人帮，遇危难之事有人解救，是逢凶化吉之星。聪明能干，遇事机灵，为人大方，乐于助人，心慈好施，人际关系好，受众人拥护。\n");
		}
		if (lunTaiJiGuiRen(bz)) {
			mess.append("\n太极贵人：聪明好学，喜神秘，有钻劲，特别喜欢星相，面相等信息预测及其他学术职业，为人正直，做事专一，有始有终！\n");
		}
		if(lunTianDeGuiRen(bz) || lunYueDeGuiRen(bz)) {
			mess.append("\n天德月德贵人：做事公道，忧国忧民，聪明智慧有才气，一生少病，官刑不犯，遇事逢凶化吉有灾自解。\n");
		}
		if (lunSanQiGuiRen(bz)) {
			mess.append("\n三奇贵人：精神异常，襟怀卓越，好奇尚大，博学多能");
			if (lunTianYiGuiRen(bz)){
				mess.append("，勋业超群 \n");
			} else {
				mess.append("。\n");
			}
		}
		
		if(lunFuXingGuiRen(bz)) {
			mess.append("\n福星贵人：一生禄禄无缺，多福多寿，金玉满堂，三餐温饱，无忧无虑。\n");
		}
		
		if (lunWenChangGuiRen(bz)) {
			mess.append("\n文昌贵人：气质雅秀，举止温文，男命逢着内涵，女命着重仪容，好学新知，具上进心，一生利官进贵，不与粗俗之辈乱交。\n");
		}
		
		if(lunKuiGangGuiRen(bz)) {
			mess.append("\n魁罡贵人：虽有领导才能，声宏气壮，且好权术，好胜心强，如不遵纪守法，难免牢狱之苦。\n");
		}
		
		if (lunGuoYinGuiRen(bz)) {
			mess.append("\n国印贵人：诚实可靠，严守清规，照章办事，办事公道，为人和悦，礼仪仁慈，气质轩昂。\n");
		}
		
		if (lunDeXiuGuiRen(bz)) {
			mess.append("\n德秀贵人：赋性聪明，温厚和气，主内涵充实，精神爽朗，仪表清奇，才华出众。\n");
		}
		
		if(lunYiMa(bz)) {
			mess.append("\n驿马：有走遍东西南北之行，迁居不止。妇女有之，更是身心不安，住不安处，为不利之象。\n");
		}
		if(lunHuaGai(bz)) {
			mess.append("\n华盖：又为艺术星，遇之主人气度不凡，喜美术，书法，绘画，音乐；喜神秘，喜命理，拜佛信道。有的还具有特异功能,能与神鬼交流，有说有笑，最好拜老和尚为师，让其调理。\n");
		}
		
		if(lunJiangXing(bz)) {
			mess.append("\n将星：如大将驻扎军中。如从事其他事业，是一颗权力之星，具有组织领导指挥才干，有慑众之威。\n");
		}
		
		if (lunJinYu(bz)) {
			mess.append("\n金舆：有福之人。男的遇之，多妻妾，骨肉安泰，子孙茂盛。女的得之多富贵，不仅骑马坐车，更是车来人往，接送频繁，八面威风。\n");
		}
		
		if (lunJinShen(bz)) {
			mess.append("\n金神：遇之不富则贵，聪明好学有才，但性急刚烈，一意孤行，具破坏性。\n");
		}
		
		if (lunTianYi(bz)) {
			mess.append("\n天医：适合研究医学，医药学，心理学，哲学以及从事医务工作。\n");
		}
		
		if (lunLuShen(bz)) {
			mess.append("\n禄神：" + lunLuShenMess(bz) +  "\n");
		}
		
		if (lunGongLu(bz)) {
			mess.append("\n拱禄：有吉神扶助，主富寿官贵。\n");
		}
		
		if (lunTianShe(bz)) {
			mess.append("\n天赦：逢凶化吉，解人灾祸，有救，尤其对罪人，有得黄帝大赦的机会。");
		}
		
		if (lunXianChi(bz)) {
			mess.append("\n咸池：咸池旺必肾功能好。精力充沛，性欲旺盛。很多名人富商将帅，大都有咸池。咸池又名桃花，不仅自己靓，配偶子女都靓。\n");
		}
		
		if (lunYangRenzhiSha(bz)) {
			mess.append("\n羊刃之煞：多主伤残之灾，也主刑法犯罪之事。身弱遇刃可帮身，身强遇刃增凶。因此，有忌羊刃之人应多行善事，克己为人，遵纪守法，方可逢凶化吉保平安，否则终生祸患无穷。\n");
		}
		
		if (lunJieSha(bz)) {
			mess.append("\n劫煞：劫煞主凶，主病伤刑法之灾。如为忌神，性刚暴好妒，奸猾狡诈，常招凶灾。如为吉神或喜神，用神，则好学上进，事业心强，工作勤勉，遇事果断，可创一番事业。\n");
		}
		
		if (lunZaiSha(bz)) {
			mess.append("\n灾煞：灾煞怕克，生处却祥，四柱交加见，福少祸连绵。\n");
		}
		
		if (lunGuChenGuaSu(bz)) {
			mess.append("\n孤辰寡宿：主男女婚姻不顺，命克六亲，徒刑之灾。但如四柱配合得当，又有鬼神相扶，不致为害，甚至当官领袖。\n");
		}
		
		if (lunKongWang(bz)) {
			mess.append("\n空亡：生旺则气度宽大，动昭虚名，长大肥满，多意外无心之祸。\n");
		}
		
		if (lunShiEDaBai(bz)) {
			mess.append("\n十恶大败：仓库金银化尘土，然若内有吉神相扶，贵气相辅，则为吉。\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (lunYinYangChaCuo(bz)) {
			mess.append("\n阴阳差错：女子逢之，公姑寡合，妯娌不足，夫家冷退，男子逢之，主退妻家，也与妻家是非寡合。婚姻不顺时最明显的信息标志，必有二婚之事。\n");
		}
		
		if (lunSiFei(bz)) {
			mess.append("\n四废：做事无成，有始无终。身弱多病而无能，主伤残。官司口舌，甚至牢狱之灾，或为僧道之人。\n");
		}
		
		//mess.append("\n" + wuxing(bz,get(new GregorianCalendar())));
		return mess + "";
	}
	
	
	public static String todaywuxing(Calendar ziji) {
		String zibazi = get(ziji);
		return todaywuxing(zibazi);
	}
	public static String todaywuxing(String zibazi) {
		String drbazi = get(new GregorianCalendar());
		return wuxing2(zibazi,drbazi,null) + agetGua2a(zibazi, new GregorianCalendar());
	}
	public static String wuxing(Calendar ziji,Calendar other) {
		String zibazi = get(ziji);
		
		return wuxing(zibazi,other);
	}
public static String haoyou(String zibazi,Calendar other) {
		
		return haoyou2(zibazi,get(other),other) +  agetGua(zibazi, other);
	}
	public static String haoyou2(String zibazi,String drbazi,Calendar rq){
		String m = "好友助运：\n";
		if (rq != null) {
			m = "好友八字：" + drbazi + "(" + getNongLi(rq) + ")\n" +  "好友对您运程的影响：\n";
		}
		StringBuilder mess = new StringBuilder(m);
		
		/*List<String>  yunGua = dgbazi(zibazi.charAt(4),drbazi);
		if (yunGua.contains("比肩")) {
			mess.append("\n比肩：\n");
		}
		if (yunGua.contains("败财")) {
			mess.append("\n败财：\n");
		}
		if (yunGua.contains("食神")) {
			mess.append("\n食神：\n");
		}
		if (yunGua.contains("伤官")) {
			mess.append("\n伤官：\n");
		}
		if (yunGua.contains("偏财")) {
			mess.append("\n偏财：\n");
		}
		if (yunGua.contains("正财")) {
			mess.append("\n正财：\n");
		}
		if (yunGua.contains("偏官")) {
			mess.append("\n偏官：\n");
		}
		if (yunGua.contains("正官")) {
			mess.append("\n正官：\n");
		}
		if (yunGua.contains("偏印")) {
			mess.append("\n偏印：\n");
		}
		if (yunGua.contains("正印")) {
			mess.append("\n正印：\n");
		}
		if (yunGua.contains("劫财")) {
			mess.append("\n劫财：\n");
		}*/
		
		return mess + "";
	}
	public static String wuxing(String zibazi,Calendar other) {
		
		return wuxing2(zibazi,get(other),other);
	}
	public static String wuxingN(String zibazi,Calendar other) {
		final NongLi2 nl2 = new NongLi2();
		String soldate  = nl2.getLundarToSolar(other.get(Calendar.YEAR), other.get(Calendar.MONTH) + 1, 
				other.get(Calendar.DAY_OF_MONTH));

		final Calendar cdate = new GregorianCalendar(
				Integer.parseInt(soldate.substring(0, 4)),
				Integer.parseInt(soldate.substring(4, 6))-1, 
				Integer.parseInt(soldate.substring(6)));
		cdate.add(Calendar.HOUR_OF_DAY, other.get(Calendar.HOUR_OF_DAY));
		return wuxing2(zibazi,GetBaZi.get(cdate),cdate);
	}
	public static String getYString(Calendar other){
		String date =  other.get(Calendar.YEAR) + "年" ;
		if ((other.get(Calendar.MONTH) + 1) > 9)
			date += (other.get(Calendar.MONTH) + 1) + "月";
		else 
			date += "0" + (other.get(Calendar.MONTH) + 1) + "月";
		if (other.get(Calendar.DAY_OF_MONTH) > 9) {
			date += other.get(Calendar.DAY_OF_MONTH) + "日";
		} else {
			date += "0" + other.get(Calendar.DAY_OF_MONTH) + "日";
		}
		return date;
	}
	public static String wuxing2(String zibazi,String drbazi,Calendar rq){
		String m = "今日运程：\n";
		if (rq != null) {
			m = "五行运程(农历：" + getNongLinh(rq) + " 阳历：" + getYString(rq) + ")：\n";
		}
		StringBuilder mess = new StringBuilder(m);
		
		List<String>  yunGua = dgbazi(zibazi.charAt(4),drbazi);
		if (yunGua.contains("比肩")) {
			mess.append("\n度客人生导航温馨提示：您今天行比肩运。\n比肩，在八字命理中代表兄弟朋友的意思。建议你今天一定要与兄弟朋友搞好关系，请客送礼，小恩小惠也能帮助你事业更上一层楼。如果你事业处于逆境之时，找兄弟朋友倾诉也是一个绝佳机会，一个好汉三个帮，行动吧！DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("败财")) {
			mess.append("\n度客人生导航温馨提示：您今天行偏财运。\n在八字命理中偏财是指工薪或经营外收入，一般指意外所得，比如股票、证券、彩票、赌博、拣财等。因此，建议您打打小麻将或是买张彩票，娱乐一下，说不定横财就手了。炒股买卖证券的，不妨交易交易，说不定大有收获哟。但是如果你是做偏门生意的，不妨出动出动，可是树大招风，做得太大了也不好。\n");

		}
		else if (yunGua.contains("食神")) {
			mess.append("\n度客人生导航温馨提示：您今天行食神运。\n食神的象征有四: \n一, 食神有得到财禄的机会, 座享其成. \n二, 有酒宴口福之庆, 四处逢迎, \n三, 有怠惰, 好逸恶劳之情, 不务正业. \n四, 有花钱消费的癖好, 不知节俭.身旺带健旺食神, 有福禄, 好饮食, 财厚食丰, 宽宏大量, 一生优游自逸.食伤过重, 喜怒哀乐易行于外, 心中有话藏不住, 作事喜欢高谈阔论, 如果经营事业往往或对事物的看法抱持过于乐观的态度而导致失败。因此，建议你今天要善待食神，有口福之庆时要懂得节制，别过份贪杯。用心于正业上，不要好逸恶劳，移情于酒色财气，终会误你锦绣前程。去做就对了。\n");
		}
		else if (yunGua.contains("伤官")) {
			mess.append("\n度客人生导航温馨提示：您今天行伤官运。\n伤官在八字命理中代表偏激的思想、言语、行为等，伤官是异性相吸的，如果你的老公确实做了有违背婚姻家庭中原则性的错误行为时，你务必毫不含糊地予以批评指正，以儆效尤。今天如果有人和你顶嘴，可能你存在偏激的言行而不能醒悟，建议你加以修身自省，就能和睦相处，事业婚姻美满幸福。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("偏财")) {
			mess.append("\n度客人生导航温馨提示：您今天行偏财运。\n在八字命理中偏财是指工薪或经营外收入，一般指意外所得，比如股票、证券、彩票、赌博、拣财等。因此，建议您打打小麻将或是买张彩票，娱乐一下，说不定横财就手了。炒股买卖证券的，不妨交易交易，说不定大有收获哟。但是如果你是做偏门生意的，不妨出动出动，可是树大招风，做得太大了也不好。\n");
		}
		else if (yunGua.contains("正财")) {
			mess.append("\n度客人生导航温馨提示：您今天行正财运。\n在八字命理中正财是指工薪收入或有固定场所的经营性收入，一般指劳动所得，比如工资、奖金、补贴、销售收入、利润等。。那是辛劳价钱所得的，说得更贴切一点，就是没获得你的许可，任何人也休想分一杯羹。因此，建议您要努力工作喔。合法所得，也用得心安理得。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("偏官")) {
			mess.append("\n度客人生导航温馨提示：您今天行偏官运。\n偏官也叫七杀，在八字命理中代表在外界的名气、名声、威信、声誉等，也代表小人，因为总是那些小人会坏了你的名气、名声、威信、声誉。因此建议你今天要谦虚谨慎，周边小人多，所以要特别防范。日省吾身之过，终得善心之友。日修之，小人也能成为君子，定能帮助你飞黄腾达。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("正官")) {
			mess.append("\n度客人生导航温馨提示：您今天行正官运。\n在八字命理中正官代表事业、官运、老公。因些建议你今天特别要用心工作，亲近上司，顺丈夫之命，定能事业顺景，步步高升。在社会交往中，作为妻子，可尽可能保持低调，要尽力树立和维护老公的威信、声誉等，给足老公颜面，其好处有，一可增添老公内心的底气和信心，对事业更加充满自信和希望；二可使周围的人受到感染，让老公在周围的环境中赢得好的气场；三作为老公完全可以切身体会到自己的事业的进一步发展是离不开贤妻方方面面无微不至的体贴和关爱的，并且一定会更加勤勉地回执妻子、回报家庭的。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("偏印")) {
			mess.append("\n度客人生导航温馨提示：您今天行偏印运。\n偏印也叫枭神，在八字命理中代表学习、知识、涵养等，就是辅助之神。因此建议你今天要特别尊师爱友，广纳良言，如此进德修业，便能福泽当今后世，功德圆满。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("正印")) {
			mess.append("\n度客人生导航温馨提示：您今天行正印运。\n在八字命理中正印是指护身之神物，因此今天建议你要特别尊敬长辈、孝敬父母，尊师重友，如果坚持在行正印运时日，能尊敬长辈、孝敬父母，你会财运亨通，万事胜意，家庭和睦幸福。DO OK！去做就对了。\n");
		}
		else if (yunGua.contains("劫财")) {
			mess.append("\n度客人生导航温馨提示：您今天行劫财运。\n劫财，顾名思义，自然是控制钱财的意思。如果你是搞营销做业务的，建议你及早出门，因为今天的运气容易促使你成功，是一个理想的交易时间，能把别人的财合法地劫回来。如果你恋爱有时，也是你动手的时候到了，在八字命理中财色相伴，建议你去登记结婚，把你心中的情感顺理成章地劫回家，也能白头到老，何乐而不为呢！如果你已婚多时，可能你为事业忽略了情感，建议你和另一半出门旅行，旅行中品尝重温你们过去相互劫持的味道，可以重拾恋爱的感觉。DO OK！去做就对了。\n");
		}
		
		return mess + "";
	}
	
	
	
	
	private static List<String> dgbazi(char dg,String bazi){
		List<String> wuxings = new ArrayList<String>();
		char gz = bazi.charAt(4);
		switch (dg) {
		case '甲': {
			if ('甲' ==  gz) {
				wuxings.add("比肩");
			} 
			else if ('乙' ==  gz) {
				wuxings.add("败财");
			}
			else if ('丙' ==  gz) {
				wuxings.add("食神");
			}
			else if ('丁' ==  gz) {
				wuxings.add("伤官");
			}
			else if (('戊' == gz)) {
				wuxings.add("偏财");
			}
			else if (('己' == gz)) {
				wuxings.add("正财");
			}
			else if (('庚' == gz)) {
				wuxings.add("偏官");
			}
			else if (('辛' == gz)) {
				wuxings.add("正官");
			}
			else if (('壬' == gz)) {
				wuxings.add("偏印");
			}
			else if (('癸' == gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '丙': {
			if (('丙' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('丁' == gz)) {
				wuxings.add("败财");
			}
			else if (('戊' == gz)) {
				wuxings.add("食神");
			}
			else if (('己' == gz)) {
				wuxings.add("伤官");
			}
			else if (('庚' == gz)) {
				wuxings.add("偏财");
			}
			else if (('辛' == gz)) {
				wuxings.add("正财");
			}
			else if (('壬' == gz)) {
				wuxings.add("偏官");
			}
			else if (('癸' == gz)) {
				wuxings.add("正官");
			}
			else if (('甲' == gz)) {
				wuxings.add("偏印");
			}
			else if (('乙' == gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '戊': {
			if (('戊' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('己' == gz)) {
				wuxings.add("败财");
			}
			else if (('庚' == gz)) {
				wuxings.add("食神");
			}
			else if (('辛' == gz)) {
				wuxings.add("伤官");
			}
			else if (('壬' == gz)) {
				wuxings.add("偏财");
			}
			else if (('癸' == gz)) {
				wuxings.add("正财");
			}
			else if (('甲' == gz)) {
				wuxings.add("偏官");
			}
			else if (('乙' == gz)) {
				wuxings.add("正官");
			}
			else if (('丙' == gz)) {
				wuxings.add("偏印");
			}
			else if (('丁' == gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '庚': {
			if (('庚' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('辛' == gz)) {
				wuxings.add("败财");
			}
			else if (('壬' == gz)) {
				wuxings.add("食神");
			}
			else if (('癸' == gz)) {
				wuxings.add("伤官");
			}
			else if (('甲' == gz)) {
				wuxings.add("偏财");
			}
			else if (('乙' == gz)) {
				wuxings.add("正财");
			}
			else if (('丙' == gz)) {
				wuxings.add("偏官");
			}
			else if (('丁' == gz)) {
				wuxings.add("正官");
			}
			else if (('戊' == gz)) {
				wuxings.add("偏印");
			}
			else if (('己' == gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '壬': {
			if (('壬' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('癸' == gz)) {
				wuxings.add("败财");
			}
			else if (('甲' == gz)) {
				wuxings.add("食神");
			}
			else if (('乙' == gz)) {
				wuxings.add("伤官");
			}
			else if (('丙' == gz)) {
				wuxings.add("偏财");
			}
			else if (('丁' == gz)) {
				wuxings.add("正财");
			}
			else if (('戊' == gz)) {
				wuxings.add("偏官");
			}
			else if (('己' == gz)) {
				wuxings.add("正官");
			}
			else if (('庚' == gz)) {
				wuxings.add("偏印");
			}
			else if (('辛' == gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '乙': {
			if (('乙' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('丙' == gz)) {
				wuxings.add("伤官");
			}
			else if (('丁' == gz)) {
				wuxings.add("食神");
			}
			else if (('戊' == gz)) {
				wuxings.add("正财");
			}
			else if (('己' == gz)) {
				wuxings.add("偏财");
			}
			else if (('庚' == gz)) {
				wuxings.add("正官");
			}
			else if (('辛' == gz)) {
				wuxings.add("偏官");
			}
			else if (('壬' == gz)) {
				wuxings.add("正印");
			}
			else if (('癸' == gz)) {
				wuxings.add("偏印");
			}
			else if (('甲' == gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '丁': {
			if (('丁' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('戊' == gz)) {
				wuxings.add("伤官");
			}
			else if (('己' == gz)) {
				wuxings.add("食神");
			}
			else if (('庚' == gz)) {
				wuxings.add("正财");
			}
			else if (('辛' == gz)) {
				wuxings.add("偏财");
			}
			else if (('壬' == gz)) {
				wuxings.add("正官");
			}
			else if (('癸' == gz)) {
				wuxings.add("偏官");
			}
			else if (('甲' == gz)) {
				wuxings.add("正印");
			}
			else if (('乙' == gz)) {
				wuxings.add("偏印");
			}
			else if (('丙' == gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '辛': {
			if (('辛' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('壬' == gz)) {
				wuxings.add("伤官");
			}
			else if (('癸' == gz)) {
				wuxings.add("食神");
			}
			else if (('甲' == gz)) {
				wuxings.add("正财");
			}
			else if (('乙' == gz)) {
				wuxings.add("偏财");
			}
			else if (('丙' == gz)) {
				wuxings.add("正官");
			}
			else if (('丁' == gz)) {
				wuxings.add("偏官");
			}
			else if (('戊' == gz)) {
				wuxings.add("正印");
			}
			else if (('己' == gz)) {
				wuxings.add("偏印");
			}
			else if (('庚' == gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '己': {
			if (('己' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('庚' == gz)) {
				wuxings.add("伤官");
			}
			else if (('辛' == gz)) {
				wuxings.add("食神");
			}
			else if (('壬' == gz)) {
				wuxings.add("正财");
			}
			else if (('癸' == gz)) {
				wuxings.add("偏财");
			}
			else if (('甲' == gz)) {
				wuxings.add("正官");
			}
			else if (('乙' == gz)) {
				wuxings.add("偏官");
			}
			else if (('丙' == gz)) {
				wuxings.add("正印");
			}
			else if (('丁' == gz)) {
				wuxings.add("偏印");
			}
			else if (('戊' == gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '癸': {
			if (('癸' == gz)) {
				wuxings.add("比肩");
			} 
			else if (('甲' == gz)) {
				wuxings.add("伤官");
			}
			else if (('乙' == gz)) {
				wuxings.add("食神");
			}
			else if (('丙' == gz)) {
				wuxings.add("正财");
			}
			else if (('丁' == gz)) {
				wuxings.add("偏财");
			}
			else if (('戊' == gz)) {
				wuxings.add("正官");
			}
			else if (('己' == gz)) {
				wuxings.add("偏官");
			}
			else if (('庚' == gz)) {
				wuxings.add("正印");
			}
			else if (('辛' == gz)) {
				wuxings.add("偏印");
			}
			else if (('壬' == gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		}
		
		return wuxings;
		/*List<String> wuxings = new ArrayList<String>();
		char[] gz = getGZ(bazi);
		switch (dg) {
		case '甲': {
			if (isGan('甲',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('乙',gz)) {
				wuxings.add("败财");
			}
			if (isGan('丙',gz)) {
				wuxings.add("食神");
			}
			if (isGan('丁',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('戊',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('己',gz)) {
				wuxings.add("正财");
			}
			if (isGan('庚',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('辛',gz)) {
				wuxings.add("正官");
			}
			if (isGan('壬',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('癸',gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '丙': {
			if (isGan('丙',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('丁',gz)) {
				wuxings.add("败财");
			}
			if (isGan('戊',gz)) {
				wuxings.add("食神");
			}
			if (isGan('己',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('庚',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('辛',gz)) {
				wuxings.add("正财");
			}
			if (isGan('壬',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('癸',gz)) {
				wuxings.add("正官");
			}
			if (isGan('甲',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('乙',gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '戊': {
			if (isGan('戊',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('己',gz)) {
				wuxings.add("败财");
			}
			if (isGan('庚',gz)) {
				wuxings.add("食神");
			}
			if (isGan('辛',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('壬',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('癸',gz)) {
				wuxings.add("正财");
			}
			if (isGan('甲',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('乙',gz)) {
				wuxings.add("正官");
			}
			if (isGan('丙',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('丁',gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '庚': {
			if (isGan('庚',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('辛',gz)) {
				wuxings.add("败财");
			}
			if (isGan('壬',gz)) {
				wuxings.add("食神");
			}
			if (isGan('癸',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('甲',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('乙',gz)) {
				wuxings.add("正财");
			}
			if (isGan('丙',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('丁',gz)) {
				wuxings.add("正官");
			}
			if (isGan('戊',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('己',gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '壬': {
			if (isGan('壬',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('癸',gz)) {
				wuxings.add("败财");
			}
			if (isGan('甲',gz)) {
				wuxings.add("食神");
			}
			if (isGan('乙',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('丙',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('丁',gz)) {
				wuxings.add("正财");
			}
			if (isGan('戊',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('己',gz)) {
				wuxings.add("正官");
			}
			if (isGan('庚',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('辛',gz)) {
				wuxings.add("正印");
			}
			
			break;
		}
		case '乙': {
			if (isGan('乙',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('丙',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('丁',gz)) {
				wuxings.add("食神");
			}
			if (isGan('戊',gz)) {
				wuxings.add("正财");
			}
			if (isGan('己',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('庚',gz)) {
				wuxings.add("正官");
			}
			if (isGan('辛',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('壬',gz)) {
				wuxings.add("正印");
			}
			if (isGan('癸',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('甲',gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '丁': {
			if (isGan('丁',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('戊',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('己',gz)) {
				wuxings.add("食神");
			}
			if (isGan('庚',gz)) {
				wuxings.add("正财");
			}
			if (isGan('辛',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('壬',gz)) {
				wuxings.add("正官");
			}
			if (isGan('癸',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('甲',gz)) {
				wuxings.add("正印");
			}
			if (isGan('乙',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('丙',gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '辛': {
			if (isGan('辛',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('壬',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('癸',gz)) {
				wuxings.add("食神");
			}
			if (isGan('甲',gz)) {
				wuxings.add("正财");
			}
			if (isGan('乙',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('丙',gz)) {
				wuxings.add("正官");
			}
			if (isGan('丁',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('戊',gz)) {
				wuxings.add("正印");
			}
			if (isGan('己',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('庚',gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '己': {
			if (isGan('己',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('庚',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('辛',gz)) {
				wuxings.add("食神");
			}
			if (isGan('壬',gz)) {
				wuxings.add("正财");
			}
			if (isGan('癸',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('甲',gz)) {
				wuxings.add("正官");
			}
			if (isGan('乙',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('丙',gz)) {
				wuxings.add("正印");
			}
			if (isGan('丁',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('戊',gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		case '癸': {
			if (isGan('癸',gz)) {
				wuxings.add("比肩");
			} 
			if (isGan('甲',gz)) {
				wuxings.add("伤官");
			}
			if (isGan('乙',gz)) {
				wuxings.add("食神");
			}
			if (isGan('丙',gz)) {
				wuxings.add("正财");
			}
			if (isGan('丁',gz)) {
				wuxings.add("偏财");
			}
			if (isGan('戊',gz)) {
				wuxings.add("正官");
			}
			if (isGan('己',gz)) {
				wuxings.add("偏官");
			}
			if (isGan('庚',gz)) {
				wuxings.add("正印");
			}
			if (isGan('辛',gz)) {
				wuxings.add("偏印");
			}
			if (isGan('壬',gz)) {
				wuxings.add("劫财");
			}
			
			break;
		}
		}
		
		return wuxings;*/
	}
	public static  boolean isZi(char zi,char[] bazi){
		boolean isExist = false;
		for (int i = 4; i < 8; i++) {
			if (bazi[i] == zi) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	
	public static  boolean isGan(char gan,char[] bazi){
		boolean isExist = false;
		for (int i = 0; i < 4; i++) {
			if (bazi[i] == gan) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}
	public static char[] getGZ(String year,String month,String day,String hour){
		char[] gz = new char[8];
		gz[GZymdh.YG] = year.charAt(0);
		gz[GZymdh.MG] = month.charAt(0);
		gz[GZymdh.DG] = day.charAt(0); 
		gz[GZymdh.HG] = hour.charAt(0);
		
		gz[GZymdh.YZ] = year.charAt(1);
		gz[GZymdh.MZ] = month.charAt(1);
		gz[GZymdh.DZ] = day.charAt(1);
		gz[GZymdh.HZ] = hour.charAt(1);
		return gz;
	}
	
	
	
	public static String get(Calendar cdate){
		String bazi = "";
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		int ryear = nl.getYear();
		int rmonth = nl.getMonth();
		
		                                       
		int rday =  cdate.get(Calendar.DAY_OF_YEAR);
		
		int	hour = cdate.get(Calendar.HOUR_OF_DAY);
		
		// 计算年月日的天干地支1900 1 1  公立1899 己亥 35 12 丙子12  1甲戌  10 
		int year = (ryear - 1899 + 35) % 60 ;
	
		int month = ((ryear - 1899) * 12 + rmonth + 1) % 60;
		int day =( (int)(Math.ceil((cdate.get(Calendar.YEAR) - 1900)  / 4.0)) + (cdate.get(Calendar.YEAR) - 1900) * 365 + rday + 8  ) % 60;
		String yz = JiaZi.jiazis[month];
		bazi = JiaZi.jiazis[year]  + yz + JiaZi.jiazis[day]  + BaZi.get(hour, JiaZi.jiazis[day].charAt(0) + "") ;
		
		
		
		
		return bazi;
	}
	public static String getNongLi(Calendar cdate){
		String bazi = "";
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		int ryear = nl.getYear();
		int rmonth = nl.getMonth();
		
		                                       
		int rday = nl.getDay();
		
		int	hour = cdate.get(Calendar.HOUR_OF_DAY);
		return ryear + "年" + rmonth + "月" + rday + "日" + hour + "时";
	}
	public static String getNongLinh(Calendar cdate){
		String bazi = "";
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		int ryear = nl.getYear();
		int rmonth = nl.getMonth();
		
		                                       
		int rday = nl.getDay();
		
		int	hour = cdate.get(Calendar.HOUR_OF_DAY);
		String date =  ryear + "年" ;
		if (rmonth > 9)
			date += rmonth + "月";
		else
			date += "0" + rmonth + "月";
		if (rday > 9)
			date += rday + "日";
		else
			date += "0" + rday + "日";
		return date;
	}
	
	public static char[] getGZ(String bazi){
		char[] gz = new char[8];
		gz[GZymdh.YG] = bazi.charAt(0);
		gz[GZymdh.MG] = bazi.charAt(2);
		gz[GZymdh.DG] = bazi.charAt(4); 
		gz[GZymdh.HG] = bazi.charAt(6);
		
		gz[GZymdh.YZ] = bazi.charAt(1);
		gz[GZymdh.MZ] = bazi.charAt(3);
		gz[GZymdh.DZ] = bazi.charAt(5);
		gz[GZymdh.HZ] = bazi.charAt(7);
		return gz;
	}
	public static boolean lunSiFei(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		String dz = bz.substring(4, 6);
		char mz = bazi[GZymdh.MZ];
		to:switch (mz) {
		case '寅': 
		case '卯':
		case '辰': {
			if(dz.equals("庚申") || dz.equals("辛酉")) {
				isLun = true;
			}
			break to;
		}
		case '巳': 
		case '午':
		case '未': {
			if(dz.equals("壬子") || dz.equals("癸亥")) {
				isLun = true;
			}
			break to;
		}
		case '申': 
		case '酉':
		case '戌': {
			if(dz.equals("甲寅") || dz.equals("乙卯")) {
				isLun = true;
			}
			break to;
		}
		case '亥': 
		case '子':
		case '丑': {
			if(dz.equals("丙午") || dz.equals("丁巳")) {
				isLun = true;
			}
			break to;
		}
	
	}
		return isLun;
	}
	
	public static boolean lunSiFei(Calendar date){
		
		String bz = get(date);
		return lunSiFei(bz);
	}
	public static boolean alunYinYangChaCuo(String bz,Calendar other){
		boolean isLun = false;
		bz = get(other);
		String dz = bz.substring(4, 6);
		ArrayList<String> mess = new ArrayList<String>();
		mess.add("丙子");
		mess.add("丁丑");
		mess.add("戊寅");
		mess.add("辛卯");
		mess.add("壬辰");
		mess.add("癸巳");
		mess.add("丁未");
		mess.add("丙午");
		mess.add("戊申");
		mess.add("辛酉");
		mess.add("壬戌");
		mess.add("癸亥");
		if (mess.contains(dz)) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean lunYinYangChaCuo(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		ArrayList<String> mess = new ArrayList<String>();
		mess.add("丙子");
		mess.add("丁丑");
		mess.add("戊寅");
		mess.add("辛卯");
		mess.add("壬辰");
		mess.add("癸巳");
		mess.add("丁未");
		mess.add("丙午");
		mess.add("戊申");
		mess.add("辛酉");
		mess.add("壬戌");
		mess.add("癸亥");
		if (mess.contains(dz)) {
			isLun = true;
		}
		return isLun;
	}
	
	public static boolean lunYinYangChaCuo(Calendar date){
		
		String bz = get(date);
		return lunYinYangChaCuo(bz);
	}
	
	public static boolean lunGu(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		String hz = bz.substring(6);
		ArrayList<String> mess = new ArrayList<String>();
		mess.add("乙巳");
		mess.add("丁巳");
		mess.add("辛亥");
		mess.add("戊申");
		mess.add("壬寅");
		mess.add("戊午");
		mess.add("壬子");
		mess.add("丙午");
		if (mess.contains(dz) && mess.contains(hz)) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean lunGu(Calendar date){
		
		String bz = get(date);
		return lunGu(bz);
	}
	
	
	
	public static boolean lunShiEDaBai(String bz){
		boolean isLun = false;
		
		String dgz = bz.substring(4, 6);
		if (dgz.equals("甲辰") || dgz.equals("乙巳")|| dgz.equals("壬申") || dgz.equals("丙申")|| dgz.equals("丁亥")
				|| dgz.equals("庚辰") || dgz.equals("戊戌") || dgz.equals("癸亥") || dgz.equals("辛巳") || dgz.equals("乙丑") ) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean alunShiEDaBai(String bz,Calendar other){
		boolean isLun = false;
		bz = get(other);
		String dgz = bz.substring(4, 6);
		if (dgz.equals("甲辰") || dgz.equals("乙巳")|| dgz.equals("壬申") || dgz.equals("丙申")|| dgz.equals("丁亥")
				|| dgz.equals("庚辰") || dgz.equals("戊戌") || dgz.equals("癸亥") || dgz.equals("辛巳") || dgz.equals("乙丑") ) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean lunShiEDaBai(Calendar date){
		
		String bz = get(date);
		return lunShiEDaBai(bz);
	}
	
	public static boolean alunKongWang(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		String dgz = bz.substring(4, 6);
		to:for (int z = 0 ; z < 11 ;z += 2) {
			nei:for (int i = 0,j = z; i < JiaZi.jias.length; i++) {
					String ji = JiaZi.jias[i];
					ji = ji + JiaZi.zis[j];

					if (ji.equals(dgz)) {
						for (int di = z; di < 12; di += 2) {
							if (isZi(JiaZi.zis[Math.abs(z - 2 + 12) % 12].charAt(0),bazi) && isZi(JiaZi.zis[Math.abs(z - 1 + 12) % 12].charAt(0),bazi)) {
								isLun = true;
								break to;
							}
						}
						break nei;
					}
					j++;
					j = j % JiaZi.zis.length;
				}
			
		}
		return isLun;
	}
	public static boolean lunKongWang(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		String dgz = bz.substring(4, 6);
		to:for (int z = 0 ; z < 11 ;z += 2) {
			nei:for (int i = 0,j = z; i < JiaZi.jias.length; i++) {
					String ji = JiaZi.jias[i];
					ji = ji + JiaZi.zis[j];

					if (ji.equals(dgz)) {
						for (int di = z; di < 12; di += 2) {
							if (isZi(JiaZi.zis[Math.abs(z - 2 + 12) % 12].charAt(0),bazi) && isZi(JiaZi.zis[Math.abs(z - 1 + 12) % 12].charAt(0),bazi)) {
								isLun = true;
								break to;
							}
						}
						break nei;
					}
					j++;
					j = j % JiaZi.zis.length;
				}
			
		}
		return isLun;
	}
	
	public static boolean lunKongWang(Calendar date){
		
		String bz = get(date);
		return lunKongWang(bz);
	}
	
	public static boolean lunGuChenGuaSu(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		
		to:for (int i = 4; i < 5 ; i++) {
			char z = bazi[i];
			switch (z) {
			case '亥':
			case '子':
			case '丑': {
				if (isZi('寅',bazi) || isZi('戌',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '午':
			case '未': {
				if (isZi('申',bazi) || isZi('辰',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '卯':
			case '辰': {
				if (isZi('巳',bazi) || isZi('丑',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '申':
			case '酉':
			case '戌': {
				if (isZi('亥',bazi) || isZi('未',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	
	public static boolean lunGuChenGuaSu(Calendar date){
	
		String bz = get(date);
		
		return lunGuChenGuaSu(bz);
	}
	public static boolean alunGuChenGuaSu(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char[] bazio =getGZ(get(other));
		to:for (int i = 4; i < 5 ; i++) {
			char z = bazi[i];
			switch (z) {
			case '亥':
			case '子':
			case '丑': {
				if (isZi('寅',bazio) || isZi('戌',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '午':
			case '未': {
				if (isZi('申',bazio) || isZi('辰',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '卯':
			case '辰': {
				if (isZi('巳',bazio) || isZi('丑',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '申':
			case '酉':
			case '戌': {
				if (isZi('亥',bazio) || isZi('未',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	public static boolean alunZaiSha(String bz,Calendar other){
		boolean isLun = false;
	
		char[]  baziz = getGZ(bz);
		char[] bazi = getGZ(get(other));
		to:for (int i = 4; i < 5 ; i++) {
			char z = baziz[i];
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('午',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('卯',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('子',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('酉',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	
	public static boolean lunZaiSha(String bz){
		boolean isLun = false;
	
		char[]  bazi = getGZ(bz);
		
		to:for (int i = 4; i < 5 ; i++) {
			char z = bazi[i];
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('午',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('卯',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('子',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('酉',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	public static boolean lunZaiSha(Calendar date){
		
		String bz = get(date);
		return lunZaiSha(bz);
	}
	
	public static boolean alunJieSha(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  baziz = getGZ(bz);
		char[] bazi = getGZ(get(other));
		to:for (int i = 4; i < 7 ; i += 2) {
			char z = baziz[i];
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('巳',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('寅',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('亥',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('申',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	public static boolean lunJieSha(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		
		to:for (int i = 4; i < 7 ; i += 2) {
			char z = bazi[i];
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('巳',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('寅',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('亥',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('申',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
	}
	
	public static boolean lunJieSha(Calendar date){
	
		String bz = get(date);
		return lunJieSha(bz);
	}
	
	public static boolean alunYangRenzhiSha(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  baziz = getGZ(bz);
		char[] bazi = getGZ(get(other));
		char dg = baziz[GZymdh.DG];
		switch (dg) {
		case '甲':	{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '乙':	{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丙':	
		case '戊': {
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丁':	
		case '己': {
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '庚':	{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辛':	{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '壬':	{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		case '癸':	{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
			
		}
		return isLun;
	}
	
	public static boolean lunYangRenzhiSha(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		
		char dg = bazi[GZymdh.DG];
		switch (dg) {
		case '甲':	{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '乙':	{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丙':	
		case '戊': {
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丁':	
		case '己': {
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '庚':	{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辛':	{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '壬':	{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		case '癸':	{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
			
		}
		return isLun;
	}
	public static boolean lunYangRenzhiSha(Calendar date){
		
		String bz = get(date);
		return lunYangRenzhiSha(bz);
	}
	
	public static boolean alunXianChi(String bz,Calendar other){
			boolean isLun = false;
		
			char[]  bazi = getGZ(get(other));
		
		
			char z = bz.charAt(5);
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('酉',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('午',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('卯',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('子',bazi)) {
					isLun = true;
				
				}
				break;
			}
				
			}
		
		return isLun;
		
		
	}
	
	public static boolean lunXianChi(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		
		to:for (int i = 4; i < 7 ; i += 2) {
			char z = bazi[i];
			switch (z) {
			case '申':
			case '子':
			case '辰': {
				if (isZi('酉',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '巳':
			case '酉':
			case '丑': {
				if (isZi('午',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '寅':
			case '午':
			case '戌': {
				if (isZi('卯',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '亥':
			case '卯':
			case '未': {
				if (isZi('子',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
				
			}
		}
		return isLun;
		
		
	}
	
	public static boolean lunXianChi(Calendar date){
	
		String bz = get(date);
		return lunXianChi(bz);
		
		
	}
	
	//查吉start
	public static  boolean lunJinShen(String bz){
		boolean isLun = false;
		
		for (int i = 4; i < 8; i += 2) {
			String dz = bz.substring(i, i + 2);
			if (dz.equals("乙丑") || dz.equals("己巳") || dz.equals("癸酉")) {
				isLun = true;
				break;
			}
		}
		return isLun;
	}
	public static  boolean lunJinShen(Calendar date){
		
		String bz = get(date);
		return lunJinShen(bz);
	}
	
	public  static boolean alunTianShe(String bz,Calendar other){
		boolean isLun = false;
		
		String dz = get(other).substring(4, 6);
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		to:switch (mz) {
		case '寅': 
		case '卯':
		case '辰': {
			if(dz.equals("戊寅")) {
				isLun = true;
			}
			break to;
		}
		case '巳': 
		case '午':
		case '未': {
			if(dz.equals("甲午")) {
				isLun = true;
			}
			break to;
		}
		case '申': 
		case '酉':
		case '戌': {
			if(dz.equals("戊申")) {
				isLun = true;
			}
			break to;
		}
		case '亥': 
		case '子':
		case '丑': {
			if(dz.equals("甲子")) {
				isLun = true;
			}
			break to;
		}
	
	}
		return isLun;
	}
	public  static boolean lunTianShe(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		to:switch (mz) {
		case '寅': 
		case '卯':
		case '辰': {
			if(dz.equals("戊寅")) {
				isLun = true;
			}
			break to;
		}
		case '巳': 
		case '午':
		case '未': {
			if(dz.equals("甲午")) {
				isLun = true;
			}
			break to;
		}
		case '申': 
		case '酉':
		case '戌': {
			if(dz.equals("戊申")) {
				isLun = true;
			}
			break to;
		}
		case '亥': 
		case '子':
		case '丑': {
			if(dz.equals("甲子")) {
				isLun = true;
			}
			break to;
		}
	
	}
		return isLun;
	}
	
	public  static boolean lunTianShe(Calendar date){
		
		String bz = get(date);
		return lunTianShe(bz);
	}
	
	public static  boolean lunGongLu(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		String hz = bz.substring(6, 8);
		if (dz.equals("癸亥") && hz.equals("癸丑")) {
			isLun = true;
		} else if (dz.equals("癸丑") && hz.equals("癸亥")) {
			isLun = true;
		} else if (dz.equals("丁巳") && hz.equals("丁未")) {
			isLun = true;
		} else if (dz.equals("己未") && hz.equals("己巳")) {
			isLun = true;
		} else if (dz.equals("戊辰") && hz.equals("戊午")) {
			isLun = true;
		}
		return isLun;
	}
	public static  boolean lunGongLu(Calendar date){
		
		String bz = get(date);
		return lunGongLu(bz);
	}
	
	public static  String alunLuShenMess(String bz,Calendar other){
		String mess = "";
		
		char[]  baziz = getGZ(bz);
		char dg = baziz[GZymdh.DG];
		char[] bazi = getGZ(get(other));
		switch (dg) {
		case '甲':{
			if (isZi('寅',bazi)) {
				mess = "甲寅谓之长生禄，大吉";
			}
			break;
		}
		case '乙':{
			if (isZi('卯',bazi)) {
				mess = "喜神旺禄，主吉。";
			}
			break;
		}
		case '丙':
		case '戊':{
			if (isZi('巳',bazi)) {
				mess = "有官位重，俱吉";
			}
			break;
		}
		case '丁':
		case '己':{
			if (isZi('午',bazi)) {
				mess = "截路空亡，多凶。";
			}
			break;
		}
		case '庚':{
			if (isZi('申',bazi)) {
				mess = "庚申长生之禄，大吉";
			}
			break;
		}
		case '辛':{
			if (isZi('酉',bazi)) {
				mess = "辛酉正禄，俱吉";
			}
			break;
		}
		case '壬':{
			if (isZi('亥',bazi)) {
				mess = "大吉";
			}
			break;
		}
		case '癸':{
			if (isZi('子',bazi)) {
				mess = "带神星贵人，有权。";
			}
			break;
		}
		
		
		}
		return mess;
	}
	public static  String lunLuShenMess(String bz){
		String mess = "";
		
		char[]  bazi = getGZ(bz);
		char dg = bazi[GZymdh.DG];
		
		switch (dg) {
		case '甲':{
			if (isZi('寅',bazi)) {
				mess = "甲寅谓之长生禄，大吉";
			}
			break;
		}
		case '乙':{
			if (isZi('卯',bazi)) {
				mess = "喜神旺禄，主吉。";
			}
			break;
		}
		case '丙':
		case '戊':{
			if (isZi('巳',bazi)) {
				mess = "有官位重，俱吉";
			}
			break;
		}
		case '丁':
		case '己':{
			if (isZi('午',bazi)) {
				mess = "截路空亡，多凶。";
			}
			break;
		}
		case '庚':{
			if (isZi('申',bazi)) {
				mess = "庚申长生之禄，大吉";
			}
			break;
		}
		case '辛':{
			if (isZi('酉',bazi)) {
				mess = "辛酉正禄，俱吉";
			}
			break;
		}
		case '壬':{
			if (isZi('亥',bazi)) {
				mess = "大吉";
			}
			break;
		}
		case '癸':{
			if (isZi('子',bazi)) {
				mess = "带神星贵人，有权。";
			}
			break;
		}
		
		
		}
		return mess;
	}
	
	public static  String lunLuShenMess(Calendar date){
		
		String bz = get(date);
		return lunLuShenMess(bz);
	}
	
	public static  boolean alunLuShen(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  baziz = getGZ(bz);
		char dg = baziz[GZymdh.DG];
		char[] bazi = getGZ(get(other));
		switch (dg) {
		case '甲':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '乙':{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丙':
		case '戊':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丁':
		case '己':{
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '庚':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辛':{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '壬':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '癸':{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		
		
		}
		return isLun;
	}
	
	public static  boolean lunLuShen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char dg = bazi[GZymdh.DG];
		
		switch (dg) {
		case '甲':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '乙':{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丙':
		case '戊':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丁':
		case '己':{
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '庚':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辛':{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '壬':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '癸':{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		
		
		}
		return isLun;
	}
	public static  boolean lunLuShen(Calendar date){
		
		String bz = get(date);
		return lunLuShen(bz);
	}
	public static  boolean alunTianYi(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  baziz = getGZ(bz);
		char mz = baziz[GZymdh.MZ];
		char[] bazi = getGZ(get(other));
		switch (mz) {
		case '寅':{
			if (isZi('丑',bazi)) {
				isLun = true;
			}
			break;
		}
		case '卯':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辰':{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '巳':{
			if (isZi('辰',bazi)) {
				isLun = true;
			}
			break;
		}
		case '午':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '未':{
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':{
			if (isZi('未',bazi)) {
				isLun = true;
			}
			break;
		}
		case '酉':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '戌':{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':{
			if (isZi('戌',bazi)) {
				isLun = true;
			}
			break;
		}
		case '子':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		
		}
		return isLun;
	}
	public static  boolean lunTianYi(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		switch (mz) {
		case '寅':{
			if (isZi('丑',bazi)) {
				isLun = true;
			}
			break;
		}
		case '卯':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辰':{
			if (isZi('卯',bazi)) {
				isLun = true;
			}
			break;
		}
		case '巳':{
			if (isZi('辰',bazi)) {
				isLun = true;
			}
			break;
		}
		case '午':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '未':{
			if (isZi('午',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':{
			if (isZi('未',bazi)) {
				isLun = true;
			}
			break;
		}
		case '酉':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '戌':{
			if (isZi('酉',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':{
			if (isZi('戌',bazi)) {
				isLun = true;
			}
			break;
		}
		case '子':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':{
			if (isZi('子',bazi)) {
				isLun = true;
			}
			break;
		}
		
		}
		return isLun;
	}
	public static  boolean lunTianYi(Calendar date){
		
		String bz = get(date);
		return  lunTianYi(bz);
	}
	public static  boolean alunJinYu(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  baziz = getGZ(bz);
		char dg = baziz[GZymdh.DG];
		char[] bazi = getGZ(get(other));
		switch (dg) {
		case '甲':{
			if(isZi('辰',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '乙':{
			if(isZi('巳',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '丙':
		case '戊':{
			if(isZi('未',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '丁':
		case '己':{
			if(isZi('申',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		case '庚':{
			if(isZi('戌',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '辛':{
			if(isZi('亥',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '壬':{
			if(isZi('丑',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '癸':{
			if(isZi('寅',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		}
		return isLun;
	}
	public static  boolean lunJinYu(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char dg = bazi[GZymdh.DG];
		switch (dg) {
		case '甲':{
			if(isZi('辰',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '乙':{
			if(isZi('巳',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '丙':
		case '戊':{
			if(isZi('未',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '丁':
		case '己':{
			if(isZi('申',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		case '庚':{
			if(isZi('戌',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '辛':{
			if(isZi('亥',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '壬':{
			if(isZi('丑',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '癸':{
			if(isZi('寅',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		}
		return isLun;
	}
	
	public static  boolean lunJinYu(Calendar date){
		
		String bz = get(date);
		return lunJinYu(bz);
	}
	
	public  static boolean lunJiangXing(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to:for (int i = 4; i < 8; i += 2) {
			char z = bazi[i];
				switch (z) {
					case '寅': 
					case '午':
					case '戌': {
						if(isZi('午',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '申': 
					case '子':
					case '辰': {
						if(isZi('子',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '巳': 
					case '酉':
					case '丑': {
						if(isZi('酉',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '亥': 
					case '卯':
					case '未': {
						if(isZi('卯',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
				
				}
		}
		return isLun;
	}
	public  static boolean lunJiangXing(Calendar date){
		
		String bz = get(date);
		return lunJiangXing(bz);
	}
	
	public  static boolean lunHuaGai(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to:for (int i = 4; i < 8; i += 2) {
			char z = bazi[i];
				switch (z) {
					case '寅': 
					case '午':
					case '戌': {
						if(isZi('戌',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '申': 
					case '子':
					case '辰': {
						if(isZi('辰',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '巳': 
					case '酉':
					case '丑': {
						if(isZi('丑',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '亥': 
					case '卯':
					case '未': {
						if(isZi('未',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
				
				}
		}
		return isLun;
	}
	public  static boolean lunHuaGai(Calendar date){
		
		String bz = get(date);
		return lunHuaGai(bz);
	}
	
	
	
	public static   boolean lunSanQiGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);

		for (int i = 0; i < 2; i++) {
			String sq = bazi[i] + "" + bazi[i + 1] + "" +  bazi[i + 2];
			if (sq.equals("甲戊庚") || sq.equals("乙丙丁") || sq.equals("壬癸辛")) {
				isLun = true;
			}
		}
		return isLun;
	}
	public static   boolean lunSanQiGuiRen(Calendar date){
		
		String bz = get(date);
		return lunSanQiGuiRen(bz);
	}
	public static  boolean alunYiMa(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		
			char z = bz.charAt(5);
				switch (z) {
					case '寅': 
					case '午':
					case '戌': {
						if(isZi('申',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '申': 
					case '子':
					case '辰': {
						if(isZi('寅',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '巳': 
					case '酉':
					case '丑': {
						if(isZi('亥',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '亥': 
					case '卯':
					case '未': {
						if(isZi('巳',bazi)) {
							isLun = true;
							
						}
						break;
					}
				
				}
		
		return isLun;
		
	}
	
	public static  boolean lunYiMa(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to:for (int i = 4; i < 8; i += 2) {
			char z = bazi[i];
				switch (z) {
					case '寅': 
					case '午':
					case '戌': {
						if(isZi('申',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '申': 
					case '子':
					case '辰': {
						if(isZi('寅',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '巳': 
					case '酉':
					case '丑': {
						if(isZi('亥',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '亥': 
					case '卯':
					case '未': {
						if(isZi('巳',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
				
				}
		}
		return isLun;
		
	}
	
	public static  boolean lunYiMa(Calendar date){
		
		String bz = get(date);
		return lunYiMa(bz);
		
	}
	
	public static  boolean alunDeXiuGuiRen(String bz,Calendar other) {
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		char mz = bz.charAt(5);
		switch (mz) {
		case '寅': 
		case '午':
		case '戌': {
			if(isGan('戊',bazi) && isGan('癸',bazi)){
				if (isGan('丙',bazi) || isGan('丁',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '申': 
		case '子':
		case '辰': {
			if(isGan('丙',bazi) && isGan('辛',bazi)){
				if (isGan('甲',bazi) && isGan('乙',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '巳': 
		case '亥':
		case '丑': {
			if(isGan('乙',bazi) && isGan('庚',bazi)){
				if (isGan('庚',bazi) || isGan('辛',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '酉': 
		case '卯':
		case '未': {
			if(isGan('丁',bazi) && isGan('壬',bazi)){
				if (isGan('甲',bazi) || isGan('乙',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
			
		}
		return isLun;
	}
	
	public static  boolean lunDeXiuGuiRen(String bz) {
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		switch (mz) {
		case '寅': 
		case '午':
		case '戌': {
			if(isGan('戊',bazi) && isGan('癸',bazi)){
				if (isGan('丙',bazi) || isGan('丁',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '申': 
		case '子':
		case '辰': {
			if(isGan('丙',bazi) && isGan('辛',bazi)){
				if (isGan('甲',bazi) && isGan('乙',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '巳': 
		case '亥':
		case '丑': {
			if(isGan('乙',bazi) && isGan('庚',bazi)){
				if (isGan('庚',bazi) || isGan('辛',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '酉': 
		case '卯':
		case '未': {
			if(isGan('丁',bazi) && isGan('壬',bazi)){
				if (isGan('甲',bazi) || isGan('乙',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
			
		}
		return isLun;
	}
	
	public static  boolean lunDeXiuGuiRen(Calendar date) {
		
		String bz = get(date);
		return lunDeXiuGuiRen(bz);
	}
	
	public static  boolean alunGuoYinGuiRen(String bz,Calendar other){
			boolean isLun = false;
			
			char[]  bazi = getGZ(get(other));
		
			char g = bz.charAt(4);
			switch (g) {
				
				case '甲':  {
					if(isZi('戌',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '乙':  {
					if(isZi('亥',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '丙':  {
					if(isZi('丑',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '丁':  {
					if(isZi('寅',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '戊':  {
					if(isZi('丑',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '己':  {
					if(isZi('寅',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '庚':  {
					if(isZi('辰',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '辛':  {
					if(isZi('巳',bazi)){
						isLun = true;
					
					}
					break;
				}
				case '壬':  {
					if(isZi('未',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '癸':  {
					if(isZi('申',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				
			}
		
		
		return isLun;
	}
	
	public static  boolean lunGuoYinGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to : for (int i = 0; i < 4; i += 2) {
			char g = bazi[i];
			switch (g) {
				
				case '甲':  {
					if(isZi('戌',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '乙':  {
					if(isZi('亥',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '丙':  {
					if(isZi('丑',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '丁':  {
					if(isZi('寅',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '戊':  {
					if(isZi('丑',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '己':  {
					if(isZi('寅',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '庚':  {
					if(isZi('辰',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '辛':  {
					if(isZi('巳',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '壬':  {
					if(isZi('未',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '癸':  {
					if(isZi('申',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				
			}
		}
		
		return isLun;
	}
	
	public static  boolean lunGuoYinGuiRen(Calendar date){
		
		String bz = get(date);
		return lunGuoYinGuiRen(bz);
	}
	
	public static  boolean lunKuiGangGuiRen(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		if (dz.equals("壬辰") || dz.equals("庚戌") || dz.equals("庚辰") || dz.equals("戊戌")) {
			isLun = true;
		}
		return isLun;
	}
	
	public static  boolean lunKuiGangGuiRen(Calendar date){
		
		String bz = get(date);
		return lunKuiGangGuiRen(bz);
	}
	public static  boolean alunWenChangGuiRen(String bz,Calendar other){
			boolean isLun = false;
		
			char[]  bazi = getGZ(get(other));
		
			char g = bz.charAt(4);
			switch (g) {
				case '甲':
				case '乙':  {
					if(isZi('巳',bazi) || isZi('午',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '丙':
				case '戊': {
					if(isZi('申',bazi) || isZi('卯',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '丁':
				case '己': {
					if(isZi('酉',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '庚':  {
					if(isZi('亥',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '辛':  {
					if(isZi('子',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '壬':  {
					if(isZi('寅',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '癸':  {
					if(isZi('卯',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				
			}
		
		return isLun;
	}
	public static  boolean lunWenChangGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to : for (int i = 0; i < 4; i += 2) {
			char g = bazi[i];
			switch (g) {
				case '甲':
				case '乙':  {
					if(isZi('巳',bazi) || isZi('午',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '丙':
				case '戊': {
					if(isZi('申',bazi) || isZi('卯',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '丁':
				case '己': {
					if(isZi('酉',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '庚':  {
					if(isZi('亥',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '辛':  {
					if(isZi('子',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '壬':  {
					if(isZi('寅',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '癸':  {
					if(isZi('卯',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				
			}
		}
		return isLun;
	}
	public static  boolean lunWenChangGuiRen(Calendar date){
		
		String bz = get(date);
		return lunWenChangGuiRen(bz);
	}
	public static  boolean alunFuXingGuiRen(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		
			char g = bz.charAt(4);
			switch (g) {
				case '甲':
				case '丙':  {
					if(isZi('子',bazi) || isZi('寅',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '乙':
				case '癸': {
					if(isZi('丑',bazi) || isZi('卯',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '戊':  {
					if(isZi('申',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '己':  {
					if(isZi('未',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '丁':  {
					if(isZi('亥',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '庚':  {
					if(isZi('午',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '辛':  {
					if(isZi('巳',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '壬':  {
					if(isZi('辰',bazi)){
						isLun = true;
						
					}
					break;
				}
				
			}
		
		return isLun;
	}
	public static  boolean lunFuXingGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to : for (int i = 0; i < 4; i += 2) {
			char g = bazi[i];
			switch (g) {
				case '甲':
				case '丙':  {
					if(isZi('子',bazi) || isZi('寅',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '乙':
				case '癸': {
					if(isZi('丑',bazi) || isZi('卯',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '戊':  {
					if(isZi('申',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '己':  {
					if(isZi('未',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '丁':  {
					if(isZi('亥',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '庚':  {
					if(isZi('午',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '辛':  {
					if(isZi('巳',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '壬':  {
					if(isZi('辰',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
			}
		}
		return isLun;
	}
	public static  boolean lunFuXingGuiRen(Calendar date){
		
		String bz = get(date);
		return lunFuXingGuiRen(bz);
	}
	public static  boolean alunYueDeGuiRen(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		char mz = bz.charAt(5);
		switch (mz) {
		case '午':
		case '戌':
		case '寅':{
			if (isGan('丙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':
		case '未':
		case '卯':{
			if (isGan('甲',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':
		case '子':
		case '辰':{
			if (isGan('壬',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':
		case '酉':
		case '巳':{
			if (isGan('庚',bazi)) {
				isLun = true;
			}
			break;
		}
		
		
		
		
		}
		return isLun;
	}
	public static  boolean lunYueDeGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		switch (mz) {
		case '午':
		case '戌':
		case '寅':{
			if (isGan('丙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':
		case '未':
		case '卯':{
			if (isGan('甲',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':
		case '子':
		case '辰':{
			if (isGan('壬',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':
		case '酉':
		case '巳':{
			if (isGan('庚',bazi)) {
				isLun = true;
			}
			break;
		}
		
		
		
		
		}
		return isLun;
	}
	public static  boolean lunYueDeGuiRen(Calendar date){
		
		String bz = get(date);
		return lunYueDeGuiRen(bz);
	}
	
	public static  boolean alunTianDeGuiRen(String bz,Calendar other){
		boolean isLun = false;
	
		char[]  bazi = getGZ(get(other));
		char mz = bz.charAt(5);
		switch (mz) {
		case '寅':{
			if (isGan('丁',bazi)) {
				isLun = true;
			}
			break;
		}
		case '卯':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辰':{
			if (isGan('壬',bazi)) {
				isLun = true;
			}
			break;
		}
		case '巳':{
			if (isGan('辛',bazi)) {
				isLun = true;
			}
			break;
		}
		case '午':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '未':{
			if (isGan('甲',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':{
			if (isGan('癸',bazi)) {
				isLun = true;
			}
			break;
		}
		case '酉':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '戌':{
			if (isGan('丙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':{
			if (isGan('乙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '子':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':{
			if (isGan('庚',bazi)) {
				isLun = true;
			}
			break;
		}
		
		}
		return isLun;
	}
	public static  boolean lunTianDeGuiRen(String bz){
		boolean isLun = false;
	
		char[]  bazi = getGZ(bz);
		char mz = bazi[GZymdh.MZ];
		switch (mz) {
		case '寅':{
			if (isGan('丁',bazi)) {
				isLun = true;
			}
			break;
		}
		case '卯':{
			if (isZi('申',bazi)) {
				isLun = true;
			}
			break;
		}
		case '辰':{
			if (isGan('壬',bazi)) {
				isLun = true;
			}
			break;
		}
		case '巳':{
			if (isGan('辛',bazi)) {
				isLun = true;
			}
			break;
		}
		case '午':{
			if (isZi('亥',bazi)) {
				isLun = true;
			}
			break;
		}
		case '未':{
			if (isGan('甲',bazi)) {
				isLun = true;
			}
			break;
		}
		case '申':{
			if (isGan('癸',bazi)) {
				isLun = true;
			}
			break;
		}
		case '酉':{
			if (isZi('寅',bazi)) {
				isLun = true;
			}
			break;
		}
		case '戌':{
			if (isGan('丙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '亥':{
			if (isGan('乙',bazi)) {
				isLun = true;
			}
			break;
		}
		case '子':{
			if (isZi('巳',bazi)) {
				isLun = true;
			}
			break;
		}
		case '丑':{
			if (isGan('庚',bazi)) {
				isLun = true;
			}
			break;
		}
		
		}
		return isLun;
	}
	public static  boolean lunTianDeGuiRen(Calendar date){
		
		String bz = get(date);
		return lunTianDeGuiRen(bz);
	}
	public static  boolean alunTaiJiGuiRen(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		
			char g = bz.charAt(4);
			switch (g) {
				case '甲':
				case '乙':  {
					if(isZi('子',bazi) || isZi('午',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '丙':
				case '丁':  {
					if(isZi('酉',bazi) || isZi('卯',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				/*case '戊':
				case '己':  {
					if(isZi('亥',bazi) || isZi('酉',bazi)){
						isLun = true;
						break to;
					}
					break;
				}*/
				case '庚':
				case '辛':  {
					if(isZi('寅',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '壬':
				case '癸':  {
					if(isZi('巳',bazi) || isZi('申',bazi)){
						isLun = true;
						
					}
					break;
				}
			}
		
		
		return isLun;
	}
	public static  boolean lunTaiJiGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to : for (int i = 0; i < 4; i += 2) {
			char g = bazi[i];
			switch (g) {
				case '甲':
				case '乙':  {
					if(isZi('子',bazi) || isZi('午',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '丙':
				case '丁':  {
					if(isZi('酉',bazi) || isZi('卯',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				/*case '戊':
				case '己':  {
					if(isZi('亥',bazi) || isZi('酉',bazi)){
						isLun = true;
						break to;
					}
					break;
				}*/
				case '庚':
				case '辛':  {
					if(isZi('寅',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '壬':
				case '癸':  {
					if(isZi('巳',bazi) || isZi('申',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
			}
		}
		
		return isLun;
	}
	
	public static  boolean lunTaiJiGuiRen(Calendar date){
		
		String bz = get(date);
		return lunTaiJiGuiRen(bz);
	}
	
	public static  boolean alunTianYiGuiRen(String bz,Calendar other){
		boolean isLun = false;
		
		char[]  bazi = getGZ(get(other));
		
			char g = bz.charAt(4);
			switch (g) {
				case '甲':
				case '戊':  {
					if(isZi('丑',bazi) || isZi('未',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '乙':
				case '己':  {
					if(isZi('子',bazi) || isZi('申',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '丙':
				case '丁':  {
					if(isZi('亥',bazi) || isZi('酉',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '壬':
				case '癸':  {
					if(isZi('卯',bazi) || isZi('巳',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '庚':
				case '辛':  {
					if(isZi('寅',bazi) || isZi('午',bazi)){
						isLun = true;
						
					}
					break;
				}
			}
		
		
		return isLun;
	}
	public static  boolean lunTianYiGuiRen(String bz){
		boolean isLun = false;
		
		char[]  bazi = getGZ(bz);
		to : for (int i = 0; i < 4; i += 2) {
			char g = bazi[i];
			switch (g) {
				case '甲':
				case '戊':  {
					if(isZi('丑',bazi) || isZi('未',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '乙':
				case '己':  {
					if(isZi('子',bazi) || isZi('申',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '丙':
				case '丁':  {
					if(isZi('亥',bazi) || isZi('酉',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '壬':
				case '癸':  {
					if(isZi('卯',bazi) || isZi('巳',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '庚':
				case '辛':  {
					if(isZi('寅',bazi) || isZi('午',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
			}
		}
		
		return isLun;
	}
	public static  boolean lunTianYiGuiRen(Calendar date){
		
		String bz = get(date);
		return lunTianYiGuiRen(bz);
	}
	
	// 查吉 end
	
	public static void print(){
		for (int y = 1901; y < 2050; y++) {
			for (int m = 0;m < 12 ;m++ ) {
				for (int d = 1 ; d <=31; d++) {
					for (int h = 0; h < 24; h++) {
						Calendar c = new GregorianCalendar(y,m,d,h,0,0);
						String bz = GetBaZi.get(c);
						if (bz.equals("乙丑戊子戊子丙辰")) {//辛酉丁酉戊戌戊午
							System.out.println(y+":"+(m + 1)+":"+d +":" + h);
						}
					}
				}
			}
		}
	}
	
	
	
	
}