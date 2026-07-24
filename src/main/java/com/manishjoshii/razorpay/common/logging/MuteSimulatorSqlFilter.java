package com.manishjoshii.razorpay.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class MuteSimulatorSqlFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName() != null && event.getLoggerName().startsWith("org.hibernate.SQL")) {
            String threadName = event.getThreadName();
            if (threadName != null && (threadName.contains("scheduling") || threadName.contains("task-"))) {
                return FilterReply.DENY;
            }
        }
        return FilterReply.NEUTRAL;
    }
}
