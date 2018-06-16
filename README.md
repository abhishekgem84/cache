# cache
A small project to demostrate caching
--------------------------------------------------------------------------------------------------------------------
1.	Cache Service – 
Requirement
Design a generic Caching service so that any Java application should be able to insert and retrieve objects from the cache. 
The caching service should meet the following requirements:

1. Any Java application should be able to access the caching service. The code changes to be done to an existing system to use this caching service should be minimal.
2. Any type of Object can be placed and extracted from the cache. 
3. The application has objects that it wants to store in the cache for different periods ranging from few minutes to hours/days. 
4. The caching service has a limit on the number of objects cached. The client application should not be affected by this limit imposed on the cache. The cache should support pluggable cleanup mechanisms using algorithms like least-recently used (LRU) or least-frequently used (LFU) etc.
5. The caching service should ensure maximum serviceability by cleaning up expired objects appropriately.

Expectation
You should come up with a fairly comprehensive design identifying the interfaces, classes (attributes and methods), and the relationship between these classes. You can use simple Java classes with javadoc comments to explain your design. The objective is not to have a working component but to meet the basic requirements using well-known approaches/patterns. 

*******Use com.pckage.TestCache as test class
