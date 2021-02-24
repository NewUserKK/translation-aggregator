MODE=$1
PATH_TO_SERVER=$2

if [ "$MODE" == "" ]; then
  MODE="prod"
fi

if [ "$PATH_TO_SERVER" == "" ]; then
  PATH_TO_SERVER="../translation-aggregator-server"
fi

if [ ! -d $"$PATH_TO_SERVER" ]; then
  echo "No directory $PATH_TO_SERVER found"
  exit
fi

rm -rf ./dist/*

PATH_TO_SERVER_STATIC="$PATH_TO_SERVER/src/main/resources/static"
mkdir -p $PATH_TO_SERVER_STATIC
if [ $MODE == "dev" ]; then
  npm run build:dev
else
  npm run build
fi || exit
rm -rf ${PATH_TO_SERVER_STATIC:?}/* && cp -rfv ./dist/* $PATH_TO_SERVER_STATIC
