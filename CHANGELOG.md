# spring-boot-assembly CHANGELOG

### 1.2.RELEASE  2018-12-23
##### 优化mvn-package.bat命令

### 1.1.RELEASE  2018-12-22
1.修复maven不同环境打包时,application.yml中profiles.active为对应环境
> application.yml
```
profiles:
    active: @profileActive@
```
2.优化启动脚本

3.各个环境的端口号统一设置成了8080

4.修改assembly.xml文件,同时打包成tar.gz和zip
> assembly.xml

    <formats>
        <format>tar.gz</format>
        <format>zip</format>
    </formats>

5.新增mvn-package.bat和mvn-package.sh快速打包脚本
> 默认指定local环境,可指定不同环境打包

> window
```bash
mvn-package.bat dev
```

> linux/mac
```bash
sh mvn-package.sh dev
```

6.assembly.xml和pom.xml文件的打包时包含*.jks文件,如有其它文件可自行设置
> assembly.xml

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
            <include>*.jks</include>
        </includes>
    </fileSet>


> pom.xml

    <resources>
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
                <include>*.jks</include>
            </includes>
        </resource>
    </resources>

### 1.0.RELEASE  2018-12-16
1. 完成spring boot项目使用maven-assembly-plugin等插件整合
2. 将项目打包成tar.zip
3. 抽取项目配置文件到config目录
4. 抽取项目第三方依赖到lib目录
5. 启动停止重启服务命令在bin目录
6. 项目启动jar包在boot目录
7. 项目启动及运行日志文件保存在logs目录