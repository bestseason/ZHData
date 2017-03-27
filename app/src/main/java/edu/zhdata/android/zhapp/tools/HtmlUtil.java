package edu.zhdata.android.zhapp.tools;

/**
 * Created by pink2 on 2017/3/22.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * html处理工具类
 * @author huweijun
 * @date 2016年7月13日 下午7:25:09
 */
public class HtmlUtil {

    /**
     * 替换指定标签的属性和值
     * @param str 需要处理的字符串
     * @param tag 标签名称
     * @param tagAttrib 要替换的标签属性值
     * @param startTag 新标签开始标记
     * @param endTag  新标签结束标记
     * @return
     * @author huweijun
     * @date 2016年7月13日 下午7:15:32
     */
    public static String replaceHtmlTag(String str, String tag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer( "<"+tag+" ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                String attributeStr = matcherForAttrib.group(1);
                matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }
    public static String replaceUploadHtmlTag(String str) {
        String temp="";
        String regex = "<img src=\"http://.*/images";
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(str);
        while (matcher.find()) {
            temp = str.substring(matcher.start(),matcher.end());
            str = str.replaceAll(temp, "<img src=\"/images");
        }
        return str;
    }

}
