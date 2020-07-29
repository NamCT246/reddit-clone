package com.namct.reddit.subreddit.exceptions;

import com.namct.reddit.exceptions.BaseException;

public class SubRedditException extends BaseException {

    private static final long serialVersionUID = 1L;

    public SubRedditException(String exceptionMessage) {
        super(exceptionMessage);
    }

    // public SubRedditException(String exceptionMessage, String suggestion) {
    //     super(exceptionMessage, suggestion);
    // }

}