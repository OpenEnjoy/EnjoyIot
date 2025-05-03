
# 停止容器
docker stop iot-server
# 删除容器
docker rm iot-server
# 重新删除镜像(如果server.jar包没更新,没必要删除镜像,否则会导致下一个步骤需要重新构建)
docker rmi iot-server
# 启动容器 ()
docker compose --env-file docker.env up -d

