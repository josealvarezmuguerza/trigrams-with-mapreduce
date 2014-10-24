trigrams-with-mapreduce
=======================


This coding exercise aims to solve the Trigrams problem described in this page: http://codekata.com/kata/kata14-tom-swift-under-the-milkwood/ .



###In short the code does: 
**generate trigrams**: a map reduce implementation

**load trigram**: A Data Access Object which loads the trigrams in memory

**generate New Book:** It generates the new book based on the above found trigrams. It starts from a random seed

**write File:** Flush the generated book into a new file called "new_file.txt"



###Usage:

````
java -jar trigrams-with-mapreduce-0.0.1-SNAPSHOT-jar-with-dependencies.jar -i INPUT_FOLDER -o OUTPUT_FOLDER
````


####Dependencies Tree: 

````
------------------------------------------------------------------------
Building trigrams-with-mapreduce-0.0.1-SNAPSHOT
------------------------------------------------------------------------

--- maven-dependency-plugin:2.1:tree (default-cli) @ trigrams-with-mapreduce ---
com.trigram:trigrams-with-mapreduce:jar:0.0.1-SNAPSHOT
+- junit:junit:jar:4.11:test (scope not updated to compile)
|  \- org.hamcrest:hamcrest-core:jar:1.3:test
+- org.easymock:easymock:jar:3.2:test
|  +- cglib:cglib-nodep:jar:2.2.2:test
|  \- org.objenesis:objenesis:jar:1.3:test
+- log4j:log4j:jar:1.2.16:compile
\- org.apache.hadoop:hadoop-core:jar:1.2.1:compile
   +- commons-cli:commons-cli:jar:1.2:compile
   +- commons-io:commons-io:jar:2.1:compile
   +- commons-httpclient:commons-httpclient:jar:3.0.1:compile
   |  \- commons-logging:commons-logging:jar:1.0.3:compile
   +- commons-codec:commons-codec:jar:1.4:compile
   +- commons-configuration:commons-configuration:jar:1.6:compile
   |  +- commons-collections:commons-collections:jar:3.2.1:compile
   |  +- commons-lang:commons-lang:jar:2.4:compile
   |  \- commons-digester:commons-digester:jar:1.8:compile
   |     \- commons-beanutils:commons-beanutils:jar:1.7.0:compile
   +- commons-el:commons-el:jar:1.0:compile
   \- org.codehaus.jackson:jackson-mapper-asl:jar:1.8.8:compile
      \- org.codehaus.jackson:jackson-core-asl:jar:1.8.8:compile
````

##License
Apache License, Version 2.0 http://www.apache.org/licenses/LICENSE-2.0
