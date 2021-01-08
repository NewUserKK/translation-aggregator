MODE=$1
PATH_TO_SERVER=$2

if [ "$MODE" == "" ]; then
  MODE="dev"
fi

if [ "$PATH_TO_SERVER" == "" ]; then
  PATH_TO_SERVER="../TODO"
fi

if [ ! -d $"$PATH_TO_SERVER" ]; then
  echo "No directory $PATH_TO_SERVER found"
  exit
fi

PATH_TO_SERVER_STATIC="$PATH_TO_SERVER/static"
mkdir -p $PATH_TO_SERVER_STATIC
if [ $MODE == "dev" ]; then
  npm run build:dev
else
  npm run build
fi || exit
rm -rf ${PATH_TO_SERVER_STATIC:?}/* && cp -rfv ./dist/* $PATH_TO_SERVER_STATIC
