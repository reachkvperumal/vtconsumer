# vtconsumer

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Properties>
        <Property name="LOG_EXCEPTION_CONFIG">%xwEx{80}</Property>
        <Property name="LOG_LEVEL_PATTERN">%5p</Property>
        <Property name="LOG_DATEFORMAT_PATTERN">yyyy-MM-dd HH:mm:ss.SSS</Property>
        <Property name="CONSOLE_LOG_PATTERN">%clr{%d{${sys:LOG_DATEFORMAT_PATTERN}}}{faint} %clr{${sys:LOG_LEVEL_PATTERN}} %clr{%pid}{magenta} %clr{-}{faint} %clr{${sys:LOGGED_APPLICATION_NAME:-}[%2.36t]}{faint} %clr{${sys:LOG_CORRELATION_PATTERN:-}}{faint}%clr{%-4.40c{1.}}{cyan} %clr{:}{faint} %m%n${sys:LOG_EXCEPTION_CONFIG}</Property>
        <Property name="FILE_LOG_PATTERN">%d{${sys:LOG_DATEFORMAT_PATTERN}} ${sys:LOG_LEVEL_PATTERN} %pid - ${sys:LOGGED_APPLICATION_NAME:-}[%t] ${sys:LOG_CORRELATION_PATTERN:-}%-36.36c{1.} : %m%n${sys:LOG_EXCEPTION_CONFIG}</Property>
    </Properties>
    <Appenders>
        <Console name="ConsoleAppender" target="SYSTEM_OUT" follow="true">
            <PatternLayout pattern="${sys:CONSOLE_LOG_PATTERN}" charset="${sys:CONSOLE_LOG_CHARSET}"/>
            <filters>
                <ThresholdFilter level="${sys:CONSOLE_LOG_THRESHOLD:-TRACE}"/>
            </filters>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info" additivity="false">
            <AppenderRef ref="ConsoleAppender"/>
        </Root>
    </Loggers>
</Configuration>
```