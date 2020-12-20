package com.ohdocha.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DochaUtil {

    @Autowired
    private MessageSource messageSource;

    /*
     * message properties에서 code에 해당하는 메세지를 가져옵니다.
     * */
	public String getMessage(String code) {
		String msg = "";
		msg = messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
		return msg;
	}


	/**
	 * <p>Checks if a String is whitespace, empty ("") or null.</p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = true
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str  the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isEmpty(String str) {
	    int strLen;
	    if (str == null || (strLen = str.length()) == 0) {
	        return true;
	    }
	    for (int i = 0; i < strLen; i++) {
	        if ((Character.isWhitespace(str.charAt(i)) == false)) {
	            return false;
	        }
	    }
	    return true;
	}
}
