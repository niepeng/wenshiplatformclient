/**
 * 
 */
package com.baibutao.app.waibao.yun.android.biz.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.baibutao.app.waibao.yun.android.biz.dataobject.constant.ItemConstant;

/**
 * @author niepeng
 *
 * @date 2012-10-11 ����12:31:07
 */
public class ItemDO implements Serializable, ItemConstant {

	private static final long serialVersionUID = -4519361576084203436L;
	
	private long id;
	
	// ����
	private String name;
	
	// ��ͼ
	private String image;
	
	// �Ƽ�����
	private String reason;
	
	// ����Ȥ����
	private int interest;
	
	// ��������
	private int join;
	
	private int longitude;
	
	private int latitude;
	
	// ԭ��
	private int oldPrice;
	
	// �ּ�
	private int newPrice;
	
	// ��ַ
	private String address;
	
	private Date startTime;
	
	private Date endTime;
	
	// �������
	private String cooperation;
	
	// ���Ƭs
	private String photos;
	
	// �����
	private String content;
	
	private int type;
	
	// �Ƿ��Ѿ��ղ�
	private boolean isCollectioned;
	
	private long collectionId;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCooperation() {
		return cooperation;
	}

	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public int getJoin() {
		return join;
	}

	public void setJoin(int join) {
		this.join = join;
	}

	public int getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(int oldPrice) {
		this.oldPrice = oldPrice;
	}

	public int getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(int newPrice) {
		this.newPrice = newPrice;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCollectioned() {
		return isCollectioned;
	}

	public void setCollectioned(boolean isCollectioned) {
		this.isCollectioned = isCollectioned;
	}

	public long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}
	
}
