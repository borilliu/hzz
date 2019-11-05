package com.saili.hzz.core.extend.swftools;

/**
 * PDF文档转换接口
 * @author liuby
 *
 */
public interface PDFConverter {
	public void convert2PDF(String inputFile,String pdfFile,String extend);
	public void convert2PDF(String inputFile,String extend);

}
