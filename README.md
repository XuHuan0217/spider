# spider for douban books
------------------
- Use maven to solve the dependencies and build the project
- 3rd party library
	- jsoup : parse html and catch useful information
	- apache poi : write reuslts to excel
	- junit : test framework
- Core package
	- HTML parser : parse HTML to BOOKENTITY list
	- Spider 
		- different spider use different offset to separate the whole works into parts
		- use two synchronizedList, one for store result and another for handel failure.
		- use AtomicInteger to send max signal(reach to the end page) to other spider threads
		- use FixedThreadPool to limit and control all the spiders
- Entity package
	- Use Factory to create Entity
	- Use genetic types for easier extension
	- Make Factory class Singleton
	- overwrite ,toString,equalsTo,hashcode for BookEntity
	- implements Comparable interface for collection sort
- Util
	- HTTP util :send http or https request
	- Excel util: write data to excel
- Main: basic spider logic

- Junit Test : due to limit time, I just test part of the function

- Time: about 0.1s for each spider thread, and totally use about 13s to finish.
-----------------------
Huan Xu
