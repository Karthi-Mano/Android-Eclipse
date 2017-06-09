package com.itheima.hwwangpan_8.bean;

import com.vdisk.net.VDiskAPI.Entry;

/**
 * @author  Administrator
 * @time 	2015-7-28 上午10:39:29
 * @des	因为Entry是lib工程中的一个实体bean.所以没法直接复写它的hashcode和equals方法
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class EntryWrapper {
	public Entry	entry;
	public boolean	isCheck;

	@Override
	public int hashCode() {
		if (this != null && this.entry != null) {
			return this.entry.path.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EntryWrapper) {
			EntryWrapper entryWrapper = (EntryWrapper) obj;
			if (entryWrapper != null && entryWrapper.entry != null && this != null && this.entry != null) {
				if (entryWrapper.entry.path.equals(this.entry.path)) {
					return true;
				}
			}
		}
		return false;
	}

}
