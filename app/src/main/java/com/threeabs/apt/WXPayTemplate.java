package com.threeabs.apt;


import com.threeabs.annotation.TargetTemplate;

@TargetTemplate(targetPath = "com.pay", targetName = "WXPayEntryActivity", targetTemplate = WXPayTemplate.class)
public class WXPayTemplate extends MainActivity{

}
