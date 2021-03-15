package com.threeabs.apt;


import com.threeabs.annotation.TargetTemplate;

@TargetTemplate(targetPath = "com.pay.test", targetName = "WXPayEntryActivity", targetTemplate = WXPayTemplate.class)
public class WXPayTemplate extends MainActivity{

}
