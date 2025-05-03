# Docker Build & Up

目标: 快速部署体验系统，帮助了解系统之间的依赖关系。
依赖：docker compose v2，删除`name: iot-system`，降低`version`版本为`3.3`以下，支持`docker-compose`。

## 功能文件列表

```text
.
├── Docker-HOWTO.md                 
├── docker-compose.yml              
├── docker.env                      <-- 提供docker-compose环境变量配置
├── iot-server                      
│   └── Dockerfile 
│   └── server.jar                  <-- 需要手动拷贝后端jar包到此路径
└── iot-web
    ├── .dockerignore
    ├── Dockerfile
    └── nginx.conf                  <-- 提供基础配置，gzip压缩、api转发
```

## 构建 jar 包
手动打包的jar包,忽略此步骤
```shell
# 创建maven缓存volume
docker volume create --name iot-maven-repo

docker run -it --rm --name iot-maven \
    -v iot-maven-repo:/root/.m2 \
    -v $PWD:/usr/src/mymaven \
    -w /usr/src/mymaven \
    maven mvn clean install package '-Dmaven.test.skip=true'
```
## 拷贝jar包
拷贝主程序jar包到docker/iot-server目录下,并命名为server.jar

## 构建启动服务

1.修改docker-compose.yml,忽略不需要启动的服务,或者修改配置
```aiignore
如果服务器已经有mysql,不需要docker里的mysql,就移除mysql相关配置,或者修改mysql的端口

```
2.修改docker.env,修改配置
如redis改成宿主机的redis:
```
REDIS_HOST=host.docker.internal
```
3.启动服务
```shell
docker-compose --env-file docker.env up -d
```

首次运行会自动构建容器。可以通过`docker compose build [service]`来手动构建所有或某个docker镜像

`--env-file docker.env`为可选参数，只是展示了通过`.env`文件配置容器启动的环境变量，`docker-compose.yml`本身已经提供足够的默认参数来正常运行系统。

## 服务器的宿主机端口映射

- admin ui: http://localhost:8080
- api server: http://localhost:48080
- mysql: root/123456, port: 3306
- redis: port: 6379


## 查看后台日志
```aiignore
docker logs -f iot-server
或
docker compose logs -f server
```

## 其他
1. 单独启动server

docker compose --env-file docker.env up  server