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
		//StringBuilder mess = new StringBuilder("���İ��֣�\n" + get(date) + "\n�����˳�: \n");
		return getGua(get(date),dates);
	}
	public static String agetGua(String  bz,Calendar other){
		StringBuilder mess = new StringBuilder("");
		if (alunTianYiGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������ҹ��ˡ��ܰ�������׻��������������������������ӵ����\n");
		}
		if (alunTaiJiGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������̫�����ˡ��ܰ�����Ϊ����ֱ������רһ����ʼ���գ�������������õĹ��ˡ�\n");
		}
		if(alunTianDeGuiRen(bz,other) || alunYueDeGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�������������µ¹��ˡ��ܰ�������׻����������Խ⡣\n");
		}
		
		
		if(alunFuXingGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������ĸ��ǹ��ˡ��ܰ�����һ����»��ȱ���ร���٣���ʳ���ǡ�\n");
		}
		
		if (alunWenChangGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������Ĳ����ˡ��ܰ������ѧ�Ͻ����ùٽ�»��\n");
		}
		
		
		
		if (alunGuoYinGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������Ĺ�ӡ���ˡ��ܰ������ܰ�����������棬���°��£����¹�������ʧΪ��ʦ���ѡ�\n");
		}
		
		/*if (alunDeXiuGuiRen(bz,other)) {
			mess.append("\n������ˣ����Դ������º���������ں���ʵ������ˬ�ʣ��Ǳ����棬�Ż����ڡ�\n");
		}*/
		
		if(alunYiMa(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������������ǡ��ܰ������������г̷�������⡣\n");
		}
		
		
		
		
		
		if (alunTianYi(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������ҽ�ǡ��ܰ��������ҽѧ���������ѧ������о���\n");
		}
		
		if (alunLuShen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������»���ǡ��ܰ��������ٷ��ƣ��������ࡣ\n");
		}
		
		
		/*if (alunTianShe(bz,other)) {
			mess.append("\n���⣺���׻����������ֻ����оȣ���������ˣ��еûƵ۴���Ļ��ᡣ");
		}*/
		
		if (alunXianChi(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������̳��ǣ��һ��ǣ����ܰ������ʶ���ԣ��ر��ʺ���֪����\n");
		}
		
		if (alunYangRenzhiSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�������������֮ɷ�ǡ��ܰ��������ֽ��ѣ�����ʱҲ��������鷳��\n");
		}
		
		if (alunJieSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������Ľ�ɷ�ǡ��ܰ�����ܻ������ȼ�����ʱҲ�����ʹʧǮ�ơ�\n");
		}
		
		if (alunZaiSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������ɷ�ǡ��ܰ��������ֽ��ѣ���ʱҲ�ḣ�ٻ�������\n");
		}
		
		if (alunGuChenGuaSu(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������Ĺ³������ǡ��ܰ���������į������ʱҲ������¶���\n");
		}
		
		if (alunKongWang(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������׿����ǡ��ܰ���������������н�ȳ�������ʱҲ���������ˮ��ͷ�Է��ȡ�ʱ��ʱ�������������ƴ�֮��\n");
		}
		
		if (alunShiEDaBai(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������ʮ�����ǡ��ܰ������ֶ���ƣ���ʱҲ��Ϊ��������ѧ����������Է���\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (alunYinYangChaCuo(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������������ǡ��ܰ�����������󣬵�Ҳ��������䲻����\n");
		}
		
		/*if (lunSiFei(bz)) {
			mess.append("\n�ķϣ������޳ɣ���ʼ���ա������ಡ�����ܣ����˲С���˾���࣬��������֮�֣���Ϊɮ��֮�ˡ�\n");
		}*/
		
		//mess.append("\n" + wuxing(bz,get(new GregorianCalendar())));
		return mess + "";
	}
	public static String agetGua2a(String  bz,Calendar other){
		StringBuilder mess = new StringBuilder("");
		if (alunTianYiGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������������ҹ����ա��ܰ�������׻��������������������������ӵ����\n");
		}
		if (alunTaiJiGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������̫�������ա��ܰ�����Ϊ����ֱ������רһ����ʼ���գ�������������õĹ��ˡ�\n");
		}
		if(alunTianDeGuiRen(bz,other) || alunYueDeGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������������µ¹����ա��ܰ�������׻����������Խ⡣\n");
		}
		
		
		if(alunFuXingGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ĸ��ǹ����ա��ܰ�����һ����»��ȱ���ร���٣���ʳ���ǡ�\n");
		}
		
		if (alunWenChangGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������Ĳ������ա��ܰ������ѧ�Ͻ����ùٽ�»��\n");
		}
		
		
		
		if (alunGuoYinGuiRen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������Ĺ�ӡ�����ա��ܰ������ܰ�����������棬���°��£����¹�������ʧΪ��ʦ���ѡ�\n");
		}
		  
		/*if (alunDeXiuGuiRen(bz,other)) {
			mess.append("\n������ˣ����Դ������º���������ں���ʵ������ˬ�ʣ��Ǳ����棬�Ż����ڡ�\n");
		}*/
		
		if(alunYiMa(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������������ա��ܰ������������г̷�������⡣\n");
		}
		
		
		
		
		
		if (alunTianYi(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�������������ҽ���ա��ܰ��������ҽѧ���������ѧ������о���\n");
		}
		
		if (alunLuShen(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������»�����ա��ܰ��������ٷ��ƣ��������ࡣ\n");
		}
		
		
		/*if (alunTianShe(bz,other)) {
			mess.append("\n���⣺���׻����������ֻ����оȣ���������ˣ��еûƵ۴���Ļ��ᡣ");
		}*/
		
		if (alunXianChi(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������̳��ǣ��һ��ǣ��ա��ܰ������ʶ���ԣ��ر��ʺ���֪����\n");
		}
		
		if (alunYangRenzhiSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������������֮ɷ���ա��ܰ��������ֽ��ѣ�����ʱҲ��������鷳��\n");
		}
		
		if (alunJieSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������Ľ�ɷ���ա��ܰ�����ܻ������ȼ�����ʱҲ�����ʹʧǮ�ơ�\n");
		}
		
		if (alunZaiSha(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�������������ɷ���ա��ܰ��������ֽ��ѣ���ʱҲ�ḣ�ٻ�������\n");
		}
		
		if (alunGuChenGuaSu(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������Ĺ³��������ա��ܰ���������į������ʱҲ������¶���\n");
		}
		
		if (alunKongWang(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ��������������׿������ա��ܰ���������������н�ȳ�������ʱҲ���������ˮ��ͷ�Է��ȡ�ʱ��ʱ�������������ƴ�֮��\n");
		}
		
		if (alunShiEDaBai(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������ʮ�������ա��ܰ������ֶ���ƣ���ʱҲ��Ϊ��������ѧ����������Է���\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (alunYinYangChaCuo(bz,other)) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������������������ա��ܰ�����������󣬵�Ҳ��������䲻����\n");
		}
		
		/*if (lunSiFei(bz)) {
			mess.append("\n�ķϣ������޳ɣ���ʼ���ա������ಡ�����ܣ����˲С���˾���࣬��������֮�֣���Ϊɮ��֮�ˡ�\n");
		}*/
		
		//mess.append("\n" + wuxing(bz,get(new GregorianCalendar())));
		return mess + "";
	}
	public static String getGua(String  bz,String date){
		StringBuilder mess = new StringBuilder("���İ��֣�\n\n" + bz + "(" + date + ")"  +"\n\n�����˳�: \n");
		if (lunTianYiGuiRen(bz)) {
			mess.append("\n���ҹ��ˣ��й����ǣ��������˰��Σ��֮�����˽�ȣ��Ƿ��׻���֮�ǡ������ܸɣ����»��飬Ϊ�˴󷽣��������ˣ��ĴȺ�ʩ���˼ʹ�ϵ�ã�������ӵ����\n");
		}
		if (lunTaiJiGuiRen(bz)) {
			mess.append("\n̫�����ˣ�������ѧ��ϲ���أ����꾢���ر�ϲ�����࣬�������ϢԤ�⼰����ѧ��ְҵ��Ϊ����ֱ������רһ����ʼ���գ�\n");
		}
		if(lunTianDeGuiRen(bz) || lunYueDeGuiRen(bz)) {
			mess.append("\n����µ¹��ˣ����¹������ǹ����񣬴����ǻ��в�����һ���ٲ������̲��������·��׻��������Խ⡣\n");
		}
		if (lunSanQiGuiRen(bz)) {
			mess.append("\n������ˣ������쳣����׿Խ�������д󣬲�ѧ����");
			if (lunTianYiGuiRen(bz)){
				mess.append("��ѫҵ��Ⱥ \n");
			} else {
				mess.append("��\n");
			}
		}
		
		if(lunFuXingGuiRen(bz)) {
			mess.append("\n���ǹ��ˣ�һ��»»��ȱ���ร���٣��������ã������±����������ǡ�\n");
		}
		
		if (lunWenChangGuiRen(bz)) {
			mess.append("\n�Ĳ����ˣ��������㣬��ֹ���ģ����������ں���Ů���������ݣ���ѧ��֪�����Ͻ��ģ�һ�����ٽ��󣬲������֮���ҽ���\n");
		}
		
		if(lunKuiGangGuiRen(bz)) {
			mess.append("\n����ˣ������쵼���ܣ�������׳���Һ�Ȩ������ʤ��ǿ���粻����ط�����������֮�ࡣ\n");
		}
		
		if (lunGuoYinGuiRen(bz)) {
			mess.append("\n��ӡ���ˣ���ʵ�ɿ���������棬���°��£����¹�����Ϊ�˺��ã������ʴȣ�����������\n");
		}
		
		if (lunDeXiuGuiRen(bz)) {
			mess.append("\n������ˣ����Դ������º���������ں���ʵ������ˬ�ʣ��Ǳ����棬�Ż����ڡ�\n");
		}
		
		if(lunYiMa(bz)) {
			mess.append("\n�������߱鶫���ϱ�֮�У�Ǩ�Ӳ�ֹ����Ů��֮���������Ĳ�����ס��������Ϊ����֮��\n");
		}
		if(lunHuaGai(bz)) {
			mess.append("\n���ǣ���Ϊ�����ǣ���֮�������Ȳ�����ϲ�������鷨���滭�����֣�ϲ���أ�ϲ�����ݷ��ŵ����еĻ��������칦��,�������������˵��Ц����ð��Ϻ���Ϊʦ���������\n");
		}
		
		if(lunJiangXing(bz)) {
			mess.append("\n���ǣ����פ�����С������������ҵ����һ��Ȩ��֮�ǣ�������֯�쵼ָ�ӲŸɣ�������֮����\n");
		}
		
		if (lunJinYu(bz)) {
			mess.append("\n���ߣ��и�֮�ˡ��е���֮������檣����ⰲ̩������ïʢ��Ů�ĵ�֮�฻�󣬲����������������ǳ�������������Ƶ�����������硣\n");
		}
		
		if (lunJinShen(bz)) {
			mess.append("\n������֮������󣬴�����ѧ�вţ����Լ����ң�һ����У����ƻ��ԡ�\n");
		}
		
		if (lunTianYi(bz)) {
			mess.append("\n��ҽ���ʺ��о�ҽѧ��ҽҩѧ������ѧ����ѧ�Լ�����ҽ������\n");
		}
		
		if (lunLuShen(bz)) {
			mess.append("\n»��" + lunLuShenMess(bz) +  "\n");
		}
		
		if (lunGongLu(bz)) {
			mess.append("\n��»���м�������������ٹٹ�\n");
		}
		
		if (lunTianShe(bz)) {
			mess.append("\n���⣺���׻����������ֻ����оȣ���������ˣ��еûƵ۴���Ļ��ᡣ");
		}
		
		if (lunXianChi(bz)) {
			mess.append("\n�̳أ��̳����������ܺá��������棬������ʢ���ܶ����˸��̽�˧�������̳ء��̳������һ��������Լ�������ż��Ů������\n");
		}
		
		if (lunYangRenzhiSha(bz)) {
			mess.append("\n����֮ɷ�������˲�֮�֣�Ҳ���̷�����֮�¡��������пɰ�����ǿ�������ס���ˣ��м�����֮��Ӧ�������£��˼�Ϊ�ˣ�����ط������ɷ��׻�����ƽ�������������������\n");
		}
		
		if (lunJieSha(bz)) {
			mess.append("\n��ɷ����ɷ���ף��������̷�֮�֡���Ϊ�����Ըձ��öʣ��黫��թ���������֡���Ϊ�����ϲ���������ѧ�Ͻ�����ҵ��ǿ���������㣬���¹��ϣ��ɴ�һ����ҵ��\n");
		}
		
		if (lunZaiSha(bz)) {
			mess.append("\n��ɷ����ɷ�¿ˣ�����ȴ�飬�������Ӽ������ٻ����ࡣ\n");
		}
		
		if (lunGuChenGuaSu(bz)) {
			mess.append("\n�³����ޣ�����Ů������˳���������ף�ͽ��֮�֡�����������ϵõ������й������������Ϊ���������������䡣\n");
		}
		
		if (lunKongWang(bz)) {
			mess.append("\n���������������ȿ�󣬶����������������������������֮����\n");
		}
		
		if (lunShiEDaBai(bz)) {
			mess.append("\nʮ���ܣ��ֿ������������Ȼ�����м�������������ศ����Ϊ����\n");
		}
		
		/*if (lunGu(bz)) {
			mess.append("\n");
		}*/
		
		if (lunYinYangChaCuo(bz)) {
			mess.append("\n�������Ů�ӷ�֮�����ùѺϣ��沲��㣬������ˣ����ӷ�֮�������޼ң�Ҳ���޼��ǷǹѺϡ�������˳ʱ�����Ե���Ϣ��־�����ж���֮�¡�\n");
		}
		
		if (lunSiFei(bz)) {
			mess.append("\n�ķϣ������޳ɣ���ʼ���ա������ಡ�����ܣ����˲С���˾���࣬��������֮�֣���Ϊɮ��֮�ˡ�\n");
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
		String m = "�������ˣ�\n";
		if (rq != null) {
			m = "���Ѱ��֣�" + drbazi + "(" + getNongLi(rq) + ")\n" +  "���Ѷ����˳̵�Ӱ�죺\n";
		}
		StringBuilder mess = new StringBuilder(m);
		
		/*List<String>  yunGua = dgbazi(zibazi.charAt(4),drbazi);
		if (yunGua.contains("�ȼ�")) {
			mess.append("\n�ȼ磺\n");
		}
		if (yunGua.contains("�ܲ�")) {
			mess.append("\n�ܲƣ�\n");
		}
		if (yunGua.contains("ʳ��")) {
			mess.append("\nʳ��\n");
		}
		if (yunGua.contains("�˹�")) {
			mess.append("\n�˹٣�\n");
		}
		if (yunGua.contains("ƫ��")) {
			mess.append("\nƫ�ƣ�\n");
		}
		if (yunGua.contains("����")) {
			mess.append("\n���ƣ�\n");
		}
		if (yunGua.contains("ƫ��")) {
			mess.append("\nƫ�٣�\n");
		}
		if (yunGua.contains("����")) {
			mess.append("\n���٣�\n");
		}
		if (yunGua.contains("ƫӡ")) {
			mess.append("\nƫӡ��\n");
		}
		if (yunGua.contains("��ӡ")) {
			mess.append("\n��ӡ��\n");
		}
		if (yunGua.contains("�ٲ�")) {
			mess.append("\n�ٲƣ�\n");
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
		String date =  other.get(Calendar.YEAR) + "��" ;
		if ((other.get(Calendar.MONTH) + 1) > 9)
			date += (other.get(Calendar.MONTH) + 1) + "��";
		else 
			date += "0" + (other.get(Calendar.MONTH) + 1) + "��";
		if (other.get(Calendar.DAY_OF_MONTH) > 9) {
			date += other.get(Calendar.DAY_OF_MONTH) + "��";
		} else {
			date += "0" + other.get(Calendar.DAY_OF_MONTH) + "��";
		}
		return date;
	}
	public static String wuxing2(String zibazi,String drbazi,Calendar rq){
		String m = "�����˳̣�\n";
		if (rq != null) {
			m = "�����˳�(ũ����" + getNongLinh(rq) + " ������" + getYString(rq) + ")��\n";
		}
		StringBuilder mess = new StringBuilder(m);
		
		List<String>  yunGua = dgbazi(zibazi.charAt(4),drbazi);
		if (yunGua.contains("�ȼ�")) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������бȼ��ˡ�\n�ȼ磬�ڰ��������д����ֵ����ѵ���˼�����������һ��Ҫ���ֵ����Ѹ�ù�ϵ���������С��С��Ҳ�ܰ�������ҵ����һ��¥���������ҵ�����澳֮ʱ�����ֵ���������Ҳ��һ�����ѻ��ᣬһ���ú�������ж��ɣ�DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("�ܲ�")) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ƫ���ˡ�\n�ڰ���������ƫ����ָ��н��Ӫ�����룬һ��ָ�������ã������Ʊ��֤ȯ����Ʊ���Ĳ�����Ƶȡ���ˣ����������С�齫�������Ų�Ʊ������һ�£�˵������ƾ����ˡ���������֤ȯ�ģ��������׽��ף�˵���������ջ�Ӵ���������������ƫ������ģ������������������������з磬����̫����Ҳ���á�\n");

		}
		else if (yunGua.contains("ʳ��")) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ʳ���ˡ�\nʳ�����������: \nһ, ʳ���еõ���»�Ļ���, �������. \n��, �о���ڸ�֮��, �Ĵ���ӭ, \n��, �е���, ���ݶ���֮��, ������ҵ. \n��, �л�Ǯ���ѵ���, ��֪�ڼ�.����������ʳ��, �и�», ����ʳ, �ƺ�ʳ��, ������, һ����������.ʳ�˹���, ϲŭ������������, �����л��ز�ס, ����ϲ����̸����, �����Ӫ��ҵ�����������Ŀ������ֹ����ֹ۵�̬�ȶ�����ʧ�ܡ���ˣ����������Ҫ�ƴ�ʳ���пڸ�֮��ʱҪ���ý��ƣ������̰������������ҵ�ϣ���Ҫ���ݶ��ͣ������ھ�ɫ�������ջ��������ǰ�̡�ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("�˹�")) {
			mess.append("\n�ȿ�����������ܰ��ʾ�����������˹��ˡ�\n�˹��ڰ��������д���ƫ����˼�롢�����Ϊ�ȣ��˹������������ģ��������Ϲ�ȷʵ������Υ��������ͥ��ԭ���ԵĴ�����Ϊʱ������غ�����������������ָ��������Ч�ȡ�����������˺��㶥�죬���������ƫ�������ж��������򣬽��������������ʡ�����ܺ����ദ����ҵ���������Ҹ���DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("ƫ��")) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ƫ���ˡ�\n�ڰ���������ƫ����ָ��н��Ӫ�����룬һ��ָ�������ã������Ʊ��֤ȯ����Ʊ���Ĳ�����Ƶȡ���ˣ����������С�齫�������Ų�Ʊ������һ�£�˵������ƾ����ˡ���������֤ȯ�ģ��������׽��ף�˵���������ջ�Ӵ���������������ƫ������ģ������������������������з磬����̫����Ҳ���á�\n");
		}
		else if (yunGua.contains("����")) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������������ˡ�\n�ڰ���������������ָ��н������й̶������ľ�Ӫ�����룬һ��ָ�Ͷ����ã����繤�ʡ����𡢲������������롢����ȡ����������ͼ�Ǯ���õģ�˵�ø�����һ�㣬����û��������ɣ��κ���Ҳ�����һ��������ˣ�������ҪŬ������ม��Ϸ����ã�Ҳ�õ��İ���á�DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("ƫ��")) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ƫ���ˡ�\nƫ��Ҳ����ɱ���ڰ��������д������������������������š������ȣ�Ҳ����С�ˣ���Ϊ������ЩС�˻ỵ��������������������š���������˽��������Ҫǫ��������ܱ�С�˶࣬����Ҫ�ر��������ʡ����֮�����յ�����֮�ѡ�����֮��С��Ҳ�ܳ�Ϊ���ӣ����ܰ�����ɻ��ڴDO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("����")) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������������ˡ�\n�ڰ������������ٴ�����ҵ�����ˡ��Ϲ�����Щ����������ر�Ҫ���Ĺ������׽���˾��˳�ɷ�֮����������ҵ˳������������������ύ���У���Ϊ���ӣ��ɾ����ܱ��ֵ͵���Ҫ����������ά���Ϲ������š������ȣ������Ϲ����棬��ô��У�һ�������Ϲ����ĵĵ��������ģ�����ҵ���ӳ������ź�ϣ��������ʹ��Χ�����ܵ���Ⱦ�����Ϲ�����Χ�Ļ�����Ӯ�úõ�����������Ϊ�Ϲ���ȫ����������ᵽ�Լ�����ҵ�Ľ�һ����չ���벻�����޷���������΢�����������͹ذ��ģ�����һ�����������ػ�ִ���ӡ��ر���ͥ�ġ�DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("ƫӡ")) {
			mess.append("\n�ȿ�����������ܰ��ʾ����������ƫӡ�ˡ�\nƫӡҲ�������ڰ��������д���ѧϰ��֪ʶ�������ȣ����Ǹ���֮����˽��������Ҫ�ر���ʦ���ѣ��������ԣ���˽�����ҵ�����ܸ��󵱽����������Բ����DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("��ӡ")) {
			mess.append("\n�ȿ�����������ܰ��ʾ������������ӡ�ˡ�\n�ڰ�����������ӡ��ָ����֮�����˽��콨����Ҫ�ر��𾴳�����Т����ĸ����ʦ���ѣ�������������ӡ��ʱ�գ����𾴳�����Т����ĸ�������˺�ͨ������ʤ�⣬��ͥ�����Ҹ���DO OK��ȥ���Ͷ��ˡ�\n");
		}
		else if (yunGua.contains("�ٲ�")) {
			mess.append("\n�ȿ�����������ܰ��ʾ���������нٲ��ˡ�\n�ٲƣ�����˼�壬��Ȼ�ǿ���Ǯ�Ƶ���˼��������Ǹ�Ӫ����ҵ��ģ������㼰����ţ���Ϊ������������״�ʹ��ɹ�����һ������Ľ���ʱ�䣬�ܰѱ��˵ĲƺϷ��ؽٻ����������������ʱ��Ҳ���㶯�ֵ�ʱ���ˣ��ڰ��������в�ɫ��飬������ȥ�Ǽǽ�飬�������е����˳����µؽٻؼң�Ҳ�ܰ�ͷ���ϣ����ֶ���Ϊ�أ�������ѻ��ʱ��������Ϊ��ҵ��������У����������һ��������У�������Ʒ���������ǹ�ȥ�໥�ٳֵ�ζ����������ʰ�����ĸо���DO OK��ȥ���Ͷ��ˡ�\n");
		}
		
		return mess + "";
	}
	
	
	
	
	private static List<String> dgbazi(char dg,String bazi){
		List<String> wuxings = new ArrayList<String>();
		char gz = bazi.charAt(4);
		switch (dg) {
		case '��': {
			if ('��' ==  gz) {
				wuxings.add("�ȼ�");
			} 
			else if ('��' ==  gz) {
				wuxings.add("�ܲ�");
			}
			else if ('��' ==  gz) {
				wuxings.add("ʳ��");
			}
			else if ('��' ==  gz) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�ܲ�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�ܲ�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�ܲ�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�ܲ�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (('��' == gz)) {
				wuxings.add("�ȼ�");
			} 
			else if (('��' == gz)) {
				wuxings.add("�˹�");
			}
			else if (('��' == gz)) {
				wuxings.add("ʳ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("����");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫ��");
			}
			else if (('��' == gz)) {
				wuxings.add("��ӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("ƫӡ");
			}
			else if (('��' == gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		}
		
		return wuxings;
		/*List<String> wuxings = new ArrayList<String>();
		char[] gz = getGZ(bazi);
		switch (dg) {
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�ܲ�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�ܲ�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�ܲ�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�ܲ�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�ܲ�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("�ٲ�");
			}
			
			break;
		}
		case '��': {
			if (isGan('��',gz)) {
				wuxings.add("�ȼ�");
			} 
			if (isGan('��',gz)) {
				wuxings.add("�˹�");
			}
			if (isGan('��',gz)) {
				wuxings.add("ʳ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("����");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫ��");
			}
			if (isGan('��',gz)) {
				wuxings.add("��ӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("ƫӡ");
			}
			if (isGan('��',gz)) {
				wuxings.add("�ٲ�");
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
		
		// ���������յ���ɵ�֧1900 1 1  ����1899 ���� 35 12 ����12  1����  10 
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
		return ryear + "��" + rmonth + "��" + rday + "��" + hour + "ʱ";
	}
	public static String getNongLinh(Calendar cdate){
		String bazi = "";
		NongLi nl = new NongLi();
		nl.setCalendar(cdate);
		int ryear = nl.getYear();
		int rmonth = nl.getMonth();
		
		                                       
		int rday = nl.getDay();
		
		int	hour = cdate.get(Calendar.HOUR_OF_DAY);
		String date =  ryear + "��" ;
		if (rmonth > 9)
			date += rmonth + "��";
		else
			date += "0" + rmonth + "��";
		if (rday > 9)
			date += rday + "��";
		else
			date += "0" + rday + "��";
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
		case '��': 
		case 'î':
		case '��': {
			if(dz.equals("����") || dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case 'δ': {
			if(dz.equals("����") || dz.equals("�ﺥ")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����") || dz.equals("��î")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����") || dz.equals("����")) {
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
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("��î");
		mess.add("�ɳ�");
		mess.add("����");
		mess.add("��δ");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("�ﺥ");
		if (mess.contains(dz)) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean lunYinYangChaCuo(String bz){
		boolean isLun = false;
		
		String dz = bz.substring(4, 6);
		ArrayList<String> mess = new ArrayList<String>();
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("��î");
		mess.add("�ɳ�");
		mess.add("����");
		mess.add("��δ");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("�ﺥ");
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
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
		mess.add("����");
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
		if (dgz.equals("�׳�") || dgz.equals("����")|| dgz.equals("����") || dgz.equals("����")|| dgz.equals("����")
				|| dgz.equals("����") || dgz.equals("����") || dgz.equals("�ﺥ") || dgz.equals("����") || dgz.equals("�ҳ�") ) {
			isLun = true;
		}
		return isLun;
	}
	public static boolean alunShiEDaBai(String bz,Calendar other){
		boolean isLun = false;
		bz = get(other);
		String dgz = bz.substring(4, 6);
		if (dgz.equals("�׳�") || dgz.equals("����")|| dgz.equals("����") || dgz.equals("����")|| dgz.equals("����")
				|| dgz.equals("����") || dgz.equals("����") || dgz.equals("�ﺥ") || dgz.equals("����") || dgz.equals("�ҳ�") ) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi) || isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case 'δ': {
				if (isZi('��',bazi) || isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case '��': {
				if (isZi('��',bazi) || isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi) || isZi('δ',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazio) || isZi('��',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case 'δ': {
				if (isZi('��',bazio) || isZi('��',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case '��': {
				if (isZi('��',bazio) || isZi('��',bazio)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazio) || isZi('δ',bazio)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('î',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('î',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
		case '��':	{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	
		case '��': {
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	
		case '��': {
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
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
		case '��':	{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	
		case '��': {
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	
		case '��': {
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':	{
			if (isZi('��',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('î',bazi)) {
					isLun = true;
					
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('��',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case '��':
			case '��': {
				if (isZi('î',bazi)) {
					isLun = true;
					break to;
				}
				break;
			}
			case '��':
			case 'î':
			case 'δ': {
				if (isZi('��',bazi)) {
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
	
	//�鼪start
	public static  boolean lunJinShen(String bz){
		boolean isLun = false;
		
		for (int i = 4; i < 8; i += 2) {
			String dz = bz.substring(i, i + 2);
			if (dz.equals("�ҳ�") || dz.equals("����") || dz.equals("����")) {
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
		case '��': 
		case 'î':
		case '��': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case 'δ': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����")) {
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
		case '��': 
		case 'î':
		case '��': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case 'δ': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����")) {
				isLun = true;
			}
			break to;
		}
		case '��': 
		case '��':
		case '��': {
			if(dz.equals("����")) {
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
		if (dz.equals("�ﺥ") && hz.equals("���")) {
			isLun = true;
		} else if (dz.equals("���") && hz.equals("�ﺥ")) {
			isLun = true;
		} else if (dz.equals("����") && hz.equals("��δ")) {
			isLun = true;
		} else if (dz.equals("��δ") && hz.equals("����")) {
			isLun = true;
		} else if (dz.equals("�쳽") && hz.equals("����")) {
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
		case '��':{
			if (isZi('��',bazi)) {
				mess = "����ν֮����»����";
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				mess = "ϲ����»��������";
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				mess = "�й�λ�أ��㼪";
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				mess = "��·���������ס�";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "���곤��֮»����";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "������»���㼪";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "��";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "�����ǹ��ˣ���Ȩ��";
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
		case '��':{
			if (isZi('��',bazi)) {
				mess = "����ν֮����»����";
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				mess = "ϲ����»��������";
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				mess = "�й�λ�أ��㼪";
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				mess = "��·���������ס�";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "���곤��֮»����";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "������»���㼪";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "��";
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				mess = "�����ǹ��ˣ���Ȩ��";
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
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
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
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
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
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'î':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'δ':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('δ',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
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
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'î':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('î',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'δ':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('δ',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
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
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':
		case '��':{
			if(isZi('δ',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
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
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':
		case '��':{
			if(isZi('δ',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
				isLun = true;
			}
			break ;
		}
		case '��':{
			if(isZi('��',bazi)) {
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
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '��': 
					case 'î':
					case 'δ': {
						if(isZi('î',bazi)) {
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
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
						
					}
					case '��': 
					case 'î':
					case 'δ': {
						if(isZi('δ',bazi)) {
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
			if (sq.equals("�����") || sq.equals("�ұ���") || sq.equals("�ɹ���")) {
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
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							
						}
						break;
					}
					case '��': 
					case 'î':
					case 'δ': {
						if(isZi('��',bazi)) {
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
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '��': 
					case '��':
					case '��': {
						if(isZi('��',bazi)) {
							isLun = true;
							break to;
						}
						break;
					}
					case '��': 
					case 'î':
					case 'δ': {
						if(isZi('��',bazi)) {
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
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) && isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case 'î':
		case 'δ': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
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
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) && isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case '��':
		case '��': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
				isLun = true;
				break ;
			}
			break;
		}
		case '��': 
		case 'î':
		case 'δ': {
			if(isGan('��',bazi) && isGan('��',bazi)){
				if (isGan('��',bazi) || isGan('��',bazi) )
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
				
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
					
					}
					break;
				}
				case '��':  {
					if(isZi('δ',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
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
				
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('δ',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
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
		if (dz.equals("�ɳ�") || dz.equals("����") || dz.equals("����") || dz.equals("����")) {
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('î',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('î',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('δ',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '��':
				case '��': {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('δ',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':  {
					if(isZi('��',bazi)){
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
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case 'δ':
		case 'î':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
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
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case 'δ':
		case 'î':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':
		case '��':
		case '��':{
			if (isGan('��',bazi)) {
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
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'î':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'δ':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
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
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'î':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case 'δ':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isZi('��',bazi)) {
				isLun = true;
			}
			break;
		}
		case '��':{
			if (isGan('��',bazi)) {
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						
					}
					break;
				}
				
				/*case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}*/
				case '��':
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('î',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				
				/*case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}*/
				case '��':
				case '��':  {
					if(isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('δ',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('î',bazi) || isZi('��',bazi)){
						isLun = true;
						
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
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
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('δ',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('î',bazi) || isZi('��',bazi)){
						isLun = true;
						break to;
					}
					break;
				}
				case '��':
				case '��':  {
					if(isZi('��',bazi) || isZi('��',bazi)){
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
	
	// �鼪 end
	
	public static void print(){
		for (int y = 1901; y < 2050; y++) {
			for (int m = 0;m < 12 ;m++ ) {
				for (int d = 1 ; d <=31; d++) {
					for (int h = 0; h < 24; h++) {
						Calendar c = new GregorianCalendar(y,m,d,h,0,0);
						String bz = GetBaZi.get(c);
						if (bz.equals("�ҳ��������ӱ���")) {//���϶�����������
							System.out.println(y+":"+(m + 1)+":"+d +":" + h);
						}
					}
				}
			}
		}
	}
	
	
	
	
}