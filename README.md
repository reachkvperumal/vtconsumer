# VirtualThreads

## Left hand side in the following image is Platform thread & Right hand side is Virtual Thread

![image](https://github.com/reachkvperumal/VirtualThreads/assets/18358866/932bce8a-614b-4518-9fad-22ea39157d64)


## In linux --> https://dustycodes.wordpress.com/2012/02/09/increasing-number-of-threads-per-process/

# The maximum number of threads that can be spawned in a Spring Boot 3 application on a Linux OS is influenced by the operating system's limit on the number of threads per process.
In Linux, the maximum number of threads per process is governed by various factors such as the amount of virtual memory, stack size, and system parameters.
The limit on the number of threads per process in Linux is not a fixed value and can be influenced by the operating system and memory constraints
https://www.baeldung.com/linux/max-threads-per-process
. Therefore, the specific number of threads that can be spawned in a Spring Boot 3 application on a Linux OS is subject to the limitations imposed by the operating system.

There is no direct limit on number of threads a process can have. Rather, this is calculated using following formula:

number of threads = total virtual memory / (stack size*1024*1024)

Thus, the number of threads per process can by increasing total virtual memory or by decreasing stack size.
Decreasing stack size can lead to code failure due to stack overflow while max virtual memory is equal to the swap memory.

Check you machine:

Total Virtual Memory: ulimit -v (default is unlimited, thus you need to increase swap memory to increase this)

Total Stack Size: ulimit -s (default is 8Mb)

Command to increase these values:

ulimit -s newvalue

ulimit -v newvalue

replace new value with the value you want to put as limit.

How to increase swap memory?
Use the following links for reference.
```
server.tomcat.threads.max=10
spring.threads.virtual.enabled=true

http://www.ehow.com/how_5001512_increase-virtual-memory-linux.html
https://man7.org/linux/man-pages/man2/getrlimit.2.html

https://www.geeksforgeeks.org/difference-between-process-and-thread/
https://www.geeksforgeeks.org/thread-in-operating-system/
```
## These virtual threads are mounted on carrier threads.

- When the virtual thread attempts to use blocking I/O, the JVM transforms this call into a non-blocking one.
- Unmounts the virtual thread, and mounts another virtual thread on the carrier thread.
- When the I/O completes, the waiting virtual thread becomes eligible again and will be re-mounted on a carrier thread to continue its execution.
- For the user, all this dance is invisible. Your synchronous code is executed asynchronously.
- Note that the virtual thread may not be re-mounted on the same carrier thread.

## Virtual threads are useful for I/O-bound workloads only
- We now know we can create more virtual threads than platform threads.
- One could be tempted to use virtual threads to perform long computations (CPU-bound workload).
- It is useless and counterproductive.
- CPU-bound doesn’t consist of quickly swapping threads while they need to wait for the completion of an I/O.
- But in leaving them attached to a CPU core to compute something.
- In this scenario, it is worse than useless to have thousands of threads if we have tens of CPU cores.
- Virtual threads won’t enhance the performance of CPU-bound workloads.
- Even worse, when running a CPU-bound workload on a virtual thread, the virtual thread monopolizes the carrier thread on which it is mounted.
- It will either reduce the chance for the other virtual thread to run or will start creating new carrier threads, leading to high memory usage.

Difference between OS Threads & Virtual Threads while iterating for 10,000 times with sleep time of 1 second.

# Starting OS THREADS...
## Total Elapsed Time for OS THREADS in seconds: 101

# Starting VIRTUAL THREADS...
## Total Elapsed Time for VIRTUAL THREADS in Seconds: 1

## StructuredTaskScope
Following is the output from StructuredTaskScope execution.

YahooReports[
getSummary={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"},
getFinancials= "meta": {
"symbol": "JPM",
"start": 493590046,
"end": 1699763610,
"timeUnit": "annual"
},
"loading": false,
"errorList": []
}
getOptions={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"},
topHoldings={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"},
getEarnings={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"}]

## ScopedValue - Implemented
