package com.example.demo.constant;

public class VerifyRegexConst {

    public static final String PATTERN_USER_SEQ = "^\\d+$";
    public static final String PATTERN_USER_CODE = "^\\w{5,30}$";
    public static final String PATTERN_PCODE = "^[A-F0-9]{256}$";
    public static final String PATTERN_ROLE = "^ROLE_[A-Z0-9]{1,30}$";
    
    

    
    /* for Book  */
    public static final String PATTERN_NAME = "^[a-zA-Z0-9]{1,40}|null$";
    public static final String PATTERN_AUTHOR = "^[a-zA-Z]{1,500}|null$";
    public static final String PATTERN_PUBLICATIONDATE = "^(\\d{4})(\\d{2})(\\d{2})|null$";
    //public static final String PATTERN_PUBLICATIONDATE ="^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
}
