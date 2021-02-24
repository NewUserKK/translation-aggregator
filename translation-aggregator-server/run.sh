server() {
  ./gradlew build
}

dock() {
  if [ "$1" == "up" ]; then
    docker-compose up -d --build
  elif [ "$1" == "down" ]; then
    docker-compose down
  fi
}

# ./run.sh build [client|server]
# ./run.sh dock (up|down)
# ./run.sh (prod|dev)

case $1 in
  "build")
    server;;
  "dock")
    dock "$2";;
  "prod")
    server &&
    docker-compose build;;
  *)
    server &&
    dock up;;
esac
