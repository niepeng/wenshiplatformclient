package com.baibutao.app.waibao.yun.android.biz.consumer;

import android.graphics.Bitmap;

import com.baibutao.app.waibao.yun.android.biz.LoadImgDO;
import com.baibutao.app.waibao.yun.android.common.ImageUtil;
import com.baibutao.app.waibao.yun.android.util.MyQueue;

/**
 * @author lsb
 *
 * @date 2012-5-29 ����11:04:48
 */
public class LoadImageConsumer implements Runnable {

	private MyQueue<LoadImgDO> myQueue;

	public LoadImageConsumer(MyQueue<LoadImgDO> queue) {
		this.myQueue = queue;
	}

	@Override
	public void run() {
		while (true) {
			final LoadImgDO loadImgDO = myQueue.take();
			final Bitmap bitmap = tryLoadImage(loadImgDO);
			if (bitmap == null) {
				continue;
			}
			loadImgDO.getHandler().post(new Runnable() {
				@Override
				public void run() {
					if(loadImgDO.getNewWidth() > 0) {
						// ����ͼƬ������С
						loadImgDO.getImageView().getLayoutParams().width = loadImgDO.getNewWidth();
						loadImgDO.getImageView().getLayoutParams().height = (int)(loadImgDO.getNewWidth() * bitmap.getHeight() / Double.valueOf(bitmap.getWidth()).doubleValue()); 
					}
					loadImgDO.getImageView().setImageBitmap(bitmap);
				}
			});
		}
	}

	private Bitmap tryLoadImage(LoadImgDO loadImgDO) {
		int tryTimes = 50;
		for (int i = 0; i < tryTimes; ++i) {
			try {
				// ����ͼƬ��Ҫ���崦��
				return ImageUtil.getBitmap(loadImgDO.getFileName(), loadImgDO.getPicUrl());
			} catch (Exception e) {
				// ���ʧ��������ȡ����
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}
			}
		}
		return null;
	}

}

