
DEMO
===========================

###########环境依赖
Nginx/Jre8/Mysql/Redis

###########开发环境
Node/Npm/Maven

###########开发工具
VsCode/Eclipse

###########部署步骤
0.添加系统环境变量
	暂无
1.启停nginx
	dev:
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -p /Users/weimy/Documents/workspace2/front/nginx_work -t -c conf/nginx.conf
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -p /Users/weimy/Documents/workspace2/front/nginx_work -c conf/nginx.conf
		ps -ef|grep nginx
		netstat -nat | grep LISTEN
		lsof -i:8080
		pkill -9 nginx
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s reload            # 重新载入配置文件
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s reopen            # 重启 Nginx
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s stop              # 停止 Nginx
		
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s reload -p /Users/weimy/Documents/workspace2/front/nginx_work -c conf/nginx.conf
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s reopen -p /Users/weimy/Documents/workspace2/front/nginx_work -c conf/nginx.conf
		/usr/local/Cellar/openresty/1.13.6.2/nginx/sbin/nginx -s stop -p /Users/weimy/Documents/workspace2/front/nginx_work -c conf/nginx.conf
		
		kill -HUP master进程号
	prod:
		/usr/local/nginx/sbin/nginx -p /home/sodev/nginx -t -c conf/nginx.conf
		/usr/local/nginx/sbin/nginx -p /home/sodev/nginx -c conf/nginx.conf
		/usr/local/nginx/sbin/nginx -s reload -p /home/sodev/nginx -c conf/nginx.conf
		/usr/local/nginx/sbin/nginx -s stop -p /home/sodev/nginx -c conf/nginx.conf
		netstat -ntlp
		ps -ef | grep nginx
2.启动mysql
3.启停redis
	dev:
		/Users/weimy/Documents/dev/redis-5.0.4/utils/redis_init_script start
		ps -ef|grep redis
		netstat -nat | grep LISTEN
		lsof -i:6679
		/Users/weimy/Documents/dev/redis-5.0.4/src/redis-cli -h 127.0.0.1 -p 6679 -a alaley
		/Users/weimy/Documents/dev/redis-5.0.4/utils/redis_init_script stop
		kill -9 pid
	prod:
		/home/sodev/redis/bin/redis_init_script start
		/home/sodev/redis/bin/redis_init_script stop
		/usr/local/redis-5.0.5/src/redis-cli  -h 127.0.0.1 -p 6679 -a alaley
4.前端
	搭建环境，见front.txt
	调试
		npm run dev
		或
		npm start
	打包
		npm run build:prod
5.后端
  
  需要的jar
  mvn dependency:copy-dependencies -DoutputDirectory=dependency_lib
	部署jar
		cd ~/Documents/workspace2/sb-demo-1
	生成jar/war:
		pom.xml中切换jar/war:
			<!--packaging>war</packaging-->
			与spring-boot-starter-tomcat有关的exclusions和dependency  （这行不做也行）
		mvn clean package
			如果想不执行测试，则执行下面一行
			mvn clean package -Dmaven.test.skip=true
	jar的部署
		cd ~/tobedel
		/*
		java -jar ~/Documents/workspace2/sb-demo-1/target/sbDemo1.jar
		nohup java -Xms513m -Xmx1024m -jar ${项目位置}/sbDemo1.jar --spring.config.location=${配置文件位置}/application-production.yaml &
		
		监控程序
		nohup java -jar /Users/weimy/Documents/workspace2/sb-demo-admin/target/astrology.jar > astrology.log 2>&1 &
		http://localhost:32101/astrology
		应用程序
		nohup java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Ddistribute.dataCenterId=2 -Ddistribute.workerId=1 -Dserver.port=8101 -jar /Users/weimy/Documents/workspace2/sb-demo-1/target/sbDemo1.jar > sbDemo1_8101.log 2>&1 &
		nohup java -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xms1024m -Xmx1024m -Xmn256m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Ddistribute.dataCenterId=2 -Ddistribute.workerId=2 -Dserver.port=8102 -jar /Users/weimy/Documents/workspace2/sb-demo-1/target/sbDemo1.jar > sbDemo1_8102.log 2>&1 &
		*/
		
		sh javaapp_init_script.sh stop
		sh javaapp_init_script.sh start
	war的部署	
		cd ~/Library/apache-tomcat-9.0.17/webapps
		cp ~/Documents/workspace2/sb-demo-1/target/sbDemo1.war .
		~/Library/apache-tomcat-9.0.17/bin/startup.sh
		http://localhost:8080/sbDemo1/teachers/all
		~/Library/apache-tomcat-9.0.17/bin/shutdown.sh
	API文档
		http://localhost:8180/sbDemo1/doc.html
		http://localhost:8180/sbDemo1/swagger-ui.html

###########后端目录结构描述
├── Readme.md                   // help
├── app                         // 应用
├── config                      // 配置
│   ├── default.json
│   ├── dev.json                // 开发环境
│   ├── experiment.json         // 实验
│   ├── index.js                // 配置控制
│   ├── local.json              // 本地
│   ├── production.json         // 生产环境
│   └── test.json               // 测试环境
├── data
├── doc                         // 文档
├── environment
├── gulpfile.js
├── locales
├── logger-service.js           // 启动日志配置
├── node_modules
├── package.json
├── app-service.js              // 启动应用配置
├── static                      // web静态资源加载
│   └── initjson
│       └── config.js         // 提供给前端的配置
├── test
├── test-service.js
└── tools



###########V1.0.0 版本内容更新
1. 新功能     aaaaaaaaa
2. 新功能     bbbbbbbbb
3. 新功能     ccccccccc
4. 新功能     ddddddddd