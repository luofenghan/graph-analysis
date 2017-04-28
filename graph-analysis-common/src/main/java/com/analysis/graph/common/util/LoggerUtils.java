package com.analysis.graph.common.util;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cwc on 2017/4/6 0006.
 */
public class LoggerUtils {
    private static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];

    private static String generateLog(String description, Object... extraInfos) {
        String log = "[" + description + "]" + " - ";
        for (Object extraInfo : extraInfos) {
            log += extraInfo + " ";
        }
        return log;
    }

    public static Builder builder(Logger logger, String info) {
        return new Builder(logger, info);
    }

    public static Builder builder(Logger logger) {
        return new Builder(logger);
    }

    public static class Builder {
        private final Logger logger;
        private String formatInfo;
        private List<Object> parameters;
        private List<Object> extras;

        private Builder(Logger logger, String formatInfo) {
            this.logger = logger;
            this.formatInfo = formatInfo;
        }

        public Builder(Logger logger) {
            this.logger = logger;
        }

        public Builder format(String format) {
            this.formatInfo = format;
            return this;
        }

        public Builder addParams(Object... params) {
            if (this.parameters == null) {
                parameters = new ArrayList<>(params.length);
            }
            parameters.addAll(Arrays.asList(params));
            return this;
        }

        public Builder addParam(Object parameter) {
            if (this.parameters == null) {
                parameters = new ArrayList<>(3);
            }
            parameters.add(parameter);
            return this;
        }

        public Builder addExtras(Object... extraInfos) {
            if (extras == null) {
                extras = new ArrayList<>(extraInfos.length);
            }
            extras.add(Arrays.asList(extraInfos));
            return this;
        }

        public Builder addExtra(Object extraInfo) {
            if (this.extras == null) {
                this.extras = new ArrayList<>(2);
            }
            extras.add(extraInfo);
            return this;
        }

        public void error() {
            logger.error(generateLog(formatInfo, toArray(extras)), toArray(parameters));
            recycle();
        }

        public void debug() {
            logger.debug(generateLog(formatInfo, toArray(extras)), toArray(parameters));
            recycle();

        }

        public void warn() {
            logger.warn(generateLog(formatInfo, toArray(extras)), toArray(parameters));
            recycle();
        }

        public void info() {
            logger.info(generateLog(formatInfo, toArray(extras)), toArray(parameters));
            recycle();
        }

        private Object[] toArray(List<Object> list) {
            return list != null ? list.toArray() : DEFAULT_EMPTY_ARRAY;
        }

        private void recycle() {
            formatInfo = null;
            if (parameters != null) {
                parameters.clear();
                parameters = null;
            }
            if (extras != null) {
                extras.clear();
                extras = null;
            }
        }


    }
}
