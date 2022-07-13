package com.showcatalog.kafka;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class KafkaConstants {

    // misc
    public static final String SEPARATOR = ".";

    // topic items
    public static final String SHOW_TOPIC = "shows";
    public static final String SCORE_TOPIC = "score";
    public static final String SEATS_TOPIC = "seats";

    // commands
    public static final String COMMAND_ADD = "add";
    public static final String COMMAND_UPDATE = "update";
    public static final String COMMAND_EXCEPTION = "exception";

}
