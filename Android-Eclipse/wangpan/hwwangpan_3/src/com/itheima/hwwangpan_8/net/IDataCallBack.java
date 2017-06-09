package com.itheima.hwwangpan_8.net;

/**
 * @author  Administrator
 * @time 	2015-7-27 下午2:32:25
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public interface IDataCallBack {
	// 传递数据
	// 请求码-->区分不同的请求-->请求的统一管理
	// 错误码-->区分不同错误信息-->错误的统一管理
	void handleServerData(int reqCode, int errCode, Object data);
}
