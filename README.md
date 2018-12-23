# spring-boot-assembly

> 1. 在spring boot项目中使用maven profiles和maven assembly插件根据不同环境打包成tar.gz或者zip
> 2. 将spring boot项目中的配置文件提取到外部config目录中
> 3. 将spring boot项目中的启动jar包移动到boot目录中
> 4. 将spring boot项目中的第三方依赖jar包移动到外部lib目录中
> 5. bin目录中是启动，停止，重启服务命令
> 6. 打包后的目录结构类似于tomcat/maven目录结构

## 代码托管

> **[Github](https://github.com/geekidea/spring-boot-assembly)** | **[Gitee](https://gitee.com/geekideaio/spring-boot-assembly)**


### 主要插件
1. maven-assembly-plugin
2. maven-jar-plugin
3. spring-boot-maven-plugin
4. maven-dependency-plugin
5. maven-resources-plugin

### CHANGELOG
[CHANGELOG](https://github.com/geekidea/spring-boot-assembly/blob/master/CHANGELOG.md)

#### 1.maven-assembly-plugin 配置assembly.xml文件路径
    <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
            <descriptors>
                <descriptor>src/main/assembly/assembly.xml</descriptor>
            </descriptors>
        </configuration>
        <executions>
            <execution>
                <id>make-assembly</id>
                <phase>package</phase>
                <goals>
                    <goal>single</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    
#### 2.assembly.xml打包配置文件
    <?xml version="1.0" encoding="UTF-8"?>
    <assembly>
        <!-- 可自定义，这里指定的是项目环境 -->
        <!-- spring-boot-assembly-local-1.0.RELEASE.tar.gz  -->
        <id>${profileActive}-${project.version}</id>
    
        <!-- 打包的类型，如果有N个，将会打N个类型的包 -->
        <formats>
            <format>tar.gz</format>
            <!--<format>zip</format>-->
        </formats>
    
        <includeBaseDirectory>true</includeBaseDirectory>
    
        <fileSets>
            <!--
                0755->即用户具有读/写/执行权限，组用户和其它用户具有读写权限；
                0644->即用户具有读写权限，组用户和其它用户具有只读权限；
            -->
    
            <!-- 将src/bin目录下的所有文件输出到打包后的bin目录中 -->
            <fileSet>
                <directory>${basedir}/src/bin</directory>
                <outputDirectory>bin</outputDirectory>
                <fileMode>0755</fileMode>
                <includes>
                    <include>**.sh</include>
                    <include>**.bat</include>
                </includes>
            </fileSet>
    
            <!-- 指定输出target/classes中的配置文件到config目录中 -->
            <fileSet>
                <directory>${basedir}/target/classes</directory>
                <outputDirectory>config</outputDirectory>
                <fileMode>0644</fileMode>
                <includes>
                    <include>application.yml</include>
                    <include>application-${profileActive}.yml</include>
                    <include>mapper/**/*.xml</include>
                    <include>static/**</include>
                    <include>templates/**</include>
                    <include>*.xml</include>
                    <include>*.properties</include>
                </includes>
            </fileSet>
    
            <!-- 将第三方依赖打包到lib目录中 -->
            <fileSet>
                <directory>${basedir}/target/lib</directory>
                <outputDirectory>lib</outputDirectory>
                <fileMode>0755</fileMode>
            </fileSet>
    
            <!-- 将项目启动jar打包到boot目录中 -->
            <fileSet>
                <directory>${basedir}/target</directory>
                <outputDirectory>boot</outputDirectory>
                <fileMode>0755</fileMode>
                <includes>
                    <include>${project.build.finalName}.jar</include>
                </includes>
            </fileSet>
    
            <!-- 包含根目录下的文件 -->
            <fileSet>
                <directory>${basedir}</directory>
                <includes>
                    <include>NOTICE</include>
                    <include>LICENSE</include>
                    <include>*.md</include>
                </includes>
            </fileSet>
        </fileSets>
    
    </assembly>

#### 3.spring-boot-maven-plugin 排除启动jar包中依赖的jar包
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <layout>ZIP</layout>
            <includes>
                <!-- 项目启动jar包中排除依赖包 -->
                <include>
                    <groupId>non-exists</groupId>
                    <artifactId>non-exists</artifactId>
                </include>
            </includes>
        </configuration>
    </plugin>

#### 4.maven-jar-plugin 自定义maven jar打包内容
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.1.0</version>
        <configuration>
            <archive>
                <manifest>
                    <!-- 项目启动类 -->
                    <mainClass>Application</mainClass>
                    <!-- 依赖的jar的目录前缀 -->
                    <classpathPrefix>../lib</classpathPrefix>
                    <addClasspath>true</addClasspath>
                </manifest>
            </archive>
            <includes>
                <!-- 只打包指定目录的文件 -->
                <include>io/geekidea/springboot/**</include>
            </includes>
        </configuration>
    </plugin>    
    
#### 5.maven-dependency-plugin 复制项目的依赖包到指定目录
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-dependency-plugin</artifactId>
        <version>3.1.0</version>
        <executions>
            <execution>
                <phase>prepare-package</phase>
                <goals>
                    <goal>copy-dependencies</goal>
                </goals>
                <configuration>
                    <outputDirectory>target/lib</outputDirectory>
                    <overWriteReleases>false</overWriteReleases>
                    <overWriteSnapshots>false</overWriteSnapshots>
                    <overWriteIfNewer>true</overWriteIfNewer>
                    <includeScope>compile</includeScope>
                </configuration>
            </execution>
        </executions>
    </plugin>    
    
#### 6.maven-resources-plugin
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-resources-plugin</artifactId>
        <version>3.1.0</version>
    </plugin>
   
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
            <include>application.yml</include>
            <include>application-${profileActive}.yml</include>
            <include>mapper/**/*.xml</include>
            <include>static/**</include>
            <include>templates/**</include>
            <include>*.xml</include>
            <include>*.properties</include>
        </includes>
    </resource>
    
#### 7.maven profiles配置
    <!--MAVEN打包选择运行环境-->
    <!-- 1:local(默认) 本地 2:dev:开发环境 3:test 4:uat 用户验收测试 5.pro:生产环境-->
    <profiles>
        <profile>
            <id>local</id>
            <properties>
                <profileActive>local</profileActive>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>dev</id>
            <properties>
                <profileActive>dev</profileActive>
            </properties>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <profileActive>test</profileActive>
            </properties>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>uat</id>
            <properties>
                <profileActive>uat</profileActive>
            </properties>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <profileActive>prod</profileActive>
            </properties>
            <activation>
                <activeByDefault>false</activeByDefault>
            </activation>
        </profile>
    </profiles>
    
#### 8.阿里云仓库配置
    <repositories>
        <!--阿里云仓库-->
        <repository>
            <id>aliyun</id>
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        </repository>
    </repositories>
    
___    
##### 项目源码结构
    ├─bin
    │      restart.sh
    │      shutdown.sh
    │      startup.bat
    │      startup.sh
    │
    ├─logs
    │      springboot-assembly.log
    │
    ├─main
    │  ├─assembly
    │  │      assembly.xml
    │  │
    │  ├─java
    │  │  └─io
    │  │      └─geekidea
    │  │          └─springboot
    │  │              └─assembly
    │  │                      Application.java
    │  │                      HelloController.java
    │  │                      HelloService.java
    │  │                      HelloServiceImpl.java
    │  │
    │  └─resources
    │      │  application-dev.yml
    │      │  application-local.yml
    │      │  application-prod.yml
    │      │  application-test.yml
    │      │  application-uat.yml
    │      │  application.yml
    │      │
    │      ├─mapper
    │      │  │  test.xml
    │      │  │
    │      │  └─hello
    │      │          hello.xml
    │      │
    │      ├─static
    │      │      index.html
    │      │
    │      └─templates
    │              test.txt
    │
    └─test

##### 项目打包
```bash
mvn clean package
```

##### 使用maven assembly插件打包local环境后的压缩包,target目录下
    spring-boot-assembly-local-1.0.RELEASE.tar.gz
    
##### linux解压tar.gz
```bash
tar -zxvf spring-boot-assembly-local-1.0.RELEASE.tar.gz
```

##### 解压后的目录结构
    └─spring-boot-assembly
        │  LICENSE
        │  NOTICE
        │  README.md
        │
        ├─bin
        │      restart.sh
        │      shutdown.sh
        │      startup.bat
        │      startup.sh
        │
        ├─boot
        │      spring-boot-assembly.jar
        │
        ├─config
        │  │  application-local.yml
        │  │  application.yml
        │  │
        │  ├─mapper
        │  │  │  test.xml
        │  │  │
        │  │  └─hello
        │  │          hello.xml
        │  │
        │  ├─static
        │  │      index.html
        │  │
        │  └─templates
        │          test.txt
        │
        └─lib
                classmate-1.4.0.jar
                fastjson-1.2.54.jar
                hibernate-validator-6.0.13.Final.jar
                jackson-annotations-2.9.0.jar
                jackson-core-2.9.7.jar
                jackson-databind-2.9.7.jar
                jackson-datatype-jdk8-2.9.7.jar
                jackson-datatype-jsr310-2.9.7.jar
                jackson-module-parameter-names-2.9.7.jar
                javax.annotation-api-1.3.2.jar
                jboss-logging-3.3.2.Final.jar
                jul-to-slf4j-1.7.25.jar
                log4j-api-2.11.1.jar
                log4j-to-slf4j-2.11.1.jar
                logback-classic-1.2.3.jar
                logback-core-1.2.3.jar
                slf4j-api-1.7.25.jar
                snakeyaml-1.23.jar
                spring-aop-5.1.2.RELEASE.jar
                spring-beans-5.1.2.RELEASE.jar
                spring-boot-2.1.0.RELEASE.jar
                spring-boot-autoconfigure-2.1.0.RELEASE.jar
                spring-boot-starter-2.1.0.RELEASE.jar
                spring-boot-starter-json-2.1.0.RELEASE.jar
                spring-boot-starter-logging-2.1.0.RELEASE.jar
                spring-boot-starter-tomcat-2.1.0.RELEASE.jar
                spring-boot-starter-web-2.1.0.RELEASE.jar
                spring-context-5.1.2.RELEASE.jar
                spring-core-5.1.2.RELEASE.jar
                spring-expression-5.1.2.RELEASE.jar
                spring-jcl-5.1.2.RELEASE.jar
                spring-web-5.1.2.RELEASE.jar
                spring-webmvc-5.1.2.RELEASE.jar
                tomcat-embed-core-9.0.12.jar
                tomcat-embed-el-9.0.12.jar
                tomcat-embed-websocket-9.0.12.jar
                validation-api-2.0.1.Final.jar

___

##### window启动,会打开浏览器，访问项目测试路径
```bash
bin/startup.bat
```
> - 访问地址: [http://localhost:8080/example/hello?name=123](http://localhost:8080/example/hello?name=123)
> - 响应结果: 
```json
{"msg":"service hello:123","code":200}
```

##### linux启动，停止，重启
```bash
sh bin/startup.sh   启动项目
sh bin/shutdown.sh  停止服务
sh bin/restart.sh   重启服务
```    

##### startup.sh 脚本中的主要内容
- 配置项目名称及项目启动jar名称，默认项目名称与启动jar名称一致

```bash
APPLICATION="spring-boot-assembly"
APPLICATION_JAR="${APPLICATION}.jar"
```

- JAVA_OPTION配置
> - JVM Configuration
> - -Xmx256m:设置JVM最大可用内存为256m,根据项目实际情况而定，建议最小和最大设置成一样。
> - -Xms256m:设置JVM初始内存。此值可以设置与-Xmx相同,以避免每次垃圾回收完成后JVM重新分配内存
> - -Xmn512m:设置年轻代大小为512m。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m,所以增大年轻代,将会减小年老代大小。此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8
> - -XX:MetaspaceSize=64m:存储class的内存大小,该值越大触发Metaspace GC的时机就越晚
> - -XX:MaxMetaspaceSize=320m:限制Metaspace增长的上限，防止因为某些情况导致Metaspace无限的使用本地内存，影响到其他程序
> - -XX:-OmitStackTraceInFastThrow:解决重复异常不打印堆栈信息问题

```bash
JAVA_OPT="-server -Xms256m -Xmx256m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m"
JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"
```
#### 执行启动命令：后台启动项目,并将日志输出到项目根目录下的logs文件夹下
```bash
nohup java ${JAVA_OPT} -jar ${BASE_PATH}/boot/${APPLICATION_JAR} --spring.config.location=${CONFIG_DIR} > ${LOG_PATH} 2>&1 &
```

## 最终执行jar包的命令
```bash
nohup java -server -Xms256m -Xmx256m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow -jar /opt/spring-boot-assembly/boot/spring-boot-assembly.jar --spring.config.location=/opt/spring-boot-assembly/config/ > /opt/spring-boot-assembly/logs/spring-boot-assembly.log 2>&1 &
```
> - nohup：在后台运行jar包，然后将运行日志输出到指定位置
> - -server：指定JVM参数
> - -jar /opt/spring-boot-assembly/boot/spring-boot-assembly.jar：指定启动的jar包
> - 启动命令中指定的启动jar包路径，配置文件路径，日志路径都是绝对路径
> - 可在任何位置执行start.sh,shutdown.sh,restart.sh脚本
> - --spring.config.location：指定配置文件目录或者文件名称，如果是目录，以/结束 
> - &gt; /opt/spring-boot-assembly/logs/spring-boot-assembly.log：指定日志输出路径
> - 2>&1 & ：将正常的运行日志和错误日志合并输入到指定日志，并在后台运行

#### shutdown.sh停服脚本，实现方式：找到当前项目的PID，然后kill -9
```bash
PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
kill -9 ${PID}
```

#### 日志记录
##### 项目启动日志存储路径，一个项目只有一个启动日志文件

```bash
logs/spring-boot-assembly_startup.log
```

    ================================================ 2018-12-12 12:36:56 ================================================
    application name: spring-boot-assembly
    application jar name: spring-boot-assembly.jar
    application bin path: /opt/spring-boot-assembly/bin
    application root path: /opt/spring-boot-assembly
    application log path: /opt/spring-boot-assembly/logs/spring-boot-assembly.log
    application JAVA_OPT : -server -Xms256m -Xmx256m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow
    application background startup command: nohup java -server -Xms256m -Xmx256m -Xmn512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow -jar /opt/spring-boot-assembly/boot/spring-boot-assembly.jar --spring.config.location=/opt/spring-boot-assembly/config/ > /opt/spring-boot-assembly/logs/spring-boot-assembly.log 2>&1 &
    application pid: 11596

##### 项目运行日志存储路径，最近一次启动项目的运行日志

```bash
logs/spring-boot-assembly.log
```

      .   ____          _            __ _ _
     /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
    ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
     \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
      '  |____| .__|_| |_|_| |_\__, | / / / /
     =========|_|==============|___/=/_/_/_/
     :: Spring Boot ::        (v2.1.0.RELEASE)
    
    2018-12-12 23:28:58.420  INFO 11596 --- [           main] o.s.boot.SpringApplication               : Starting application on VM_0_17_centos with PID 11596 (started by root in /opt/spring-boot-assembly)
    2018-12-12 23:28:58.442  INFO 11596 --- [           main] o.s.boot.SpringApplication               : The following profiles are active: local
    2018-12-12 23:29:01.355  INFO 11596 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
    2018-12-12 23:29:01.437  INFO 11596 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
    2018-12-12 23:29:01.437  INFO 11596 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/9.0.12
    2018-12-12 23:29:01.461  INFO 11596 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib]
    2018-12-12 23:29:01.646  INFO 11596 --- [           main] o.a.c.c.C.[.[localhost].[/example]       : Initializing Spring embedded WebApplicationContext
    2018-12-12 23:29:01.647  INFO 11596 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 3028 ms
    2018-12-12 23:29:01.708  INFO 11596 --- [           main] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
    2018-12-12 23:29:01.712  INFO 11596 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
    2018-12-12 23:29:01.712  INFO 11596 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
    2018-12-12 23:29:01.712  INFO 11596 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'formContentFilter' to: [/*]
    2018-12-12 23:29:01.713  INFO 11596 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
    2018-12-12 23:29:02.250  INFO 11596 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
    2018-12-12 23:29:03.179  INFO 11596 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path '/example'
    2018-12-12 23:29:03.182  INFO 11596 --- [           main] o.s.boot.SpringApplication               : Started application in 5.844 seconds (JVM running for 6.547)
    spring.profiles.active = local
    contextPath = /example
    server.port = 8080
    hello = Hello Local
    http://localhost:8080/example/hello?name=123


##### 项目历史运行日志存储路径，每启动一次项目，会将之前的运行日志移动到back目录
    `-- logs
        |-- back
        |   |-- spring-boot-assembly_back_2018-12-12-23-30-10.log
        |   `-- spring-boot-assembly_back_2018-12-12-23-36-56.log
        |-- spring-boot-assembly.log
        `-- spring-boot-assembly_startup.log
    
#### maven项目打包
##### 1. 使用IDEA工具打包,选择对应的profiles,然后clean package
![mvn-clean-package-local](https://raw.githubusercontent.com/geekidea/spring-boot-assembly/master/img/mvn-clean-package-local.png)
![mvn-clean-package-dev](https://raw.githubusercontent.com/geekidea/spring-boot-assembly/master/img/mvn-clean-package-dev.png)

##### 2. 使用maven命令打包
```bash
mvn clean package -Pdev
```
##### 3. 使用mvn-package脚本打包
> window
```bash
mvn-package.bat dev
```

> linux/mac
```bash
sh mvn-package.sh dev
```

#### FAQ
##### Q: 项目打成tar包后，不能正常读取resource目录下的某些配置文件

##### A:如果resource目录中还用到其它配置文件，需要在pom.xml和assembly.xml文件的resource中进行配置
1. pom.xml中的resource默认配置
> 把需要打包的文件添加到include中

    <!-- 资源文件配置 -->
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
            <include>application.yml</include>
            <include>application-${profileActive}.yml</include>
            <include>mapper/**/*.xml</include>
            <include>static/**</include>
            <include>templates/**</include>
            <include>*.xml</include>
            <include>*.properties</include>
            <!-- xxx.keystore -->
            <include>xxx.keystore</include>
        </includes>
    </resource>

2. assembly.xml中的resource默认配置
> 把需要打包的文件添加到include中

    <!-- 指定输出target/classes中的配置文件到config目录中 -->
    <fileSet>
        <directory>${basedir}/target/classes</directory>
        <outputDirectory>config</outputDirectory>
        <fileMode>0644</fileMode>
        <includes>
            <include>application.yml</include>
            <include>application-${profileActive}.yml</include>
            <include>mapper/**/*.xml</include>
            <include>static/**</include>
            <include>templates/**</include>
            <include>*.xml</include>
            <include>*.properties</include>
            <!-- xxx.keystore -->
            <include>xxx.keystore</include>
        </includes>
    </fileSet>
    
##### 参考：
- https://docs.spring.io/spring-boot/docs/current/maven-plugin/
- http://maven.apache.org/components/plugins/maven-assembly-plugin/usage.html
- http://maven.apache.org/plugins/maven-jar-plugin/
- http://maven.apache.org/components/plugins/maven-dependency-plugin/
- https://maven.apache.org/plugins/maven-resources-plugin/
- http://maven.apache.org/guides/introduction/introduction-to-profiles.html