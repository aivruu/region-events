#!/bin/bash

# Extracts the value from the specified property (as $1) from the given file (as $2).
#
# The returned property is cut taken '=' as delimiter, then result is extracted and returned.
# e.g. version=2.0.0 -> 2.0.0
extractProperty () {
  grep "$1" "$2" | cut -d'=' -f2
}

# Ensures the specified directory or file exists, otherwise exists the script with error.
ensureExistingDirOrFile () {
  if [ ! -d "$1" ]; then
    echo "fn ensureExistingDir(): Specified directory $1 doesn't exist.";
    exit 1
  fi
}

SERVER_PLUGINS_DIR="$HOME/Escritorio/Testing/plugins"
ensureExistingDirOrFile "$SERVER_PLUGINS_DIR"

#if [[ ! -d "$SERVER_DIR" || ! -d "$SERVER_PLUGINS_DIR" ]]; then
#  echo
#  echo "ERROR: Server or plugins directories does not exist, ensure the specified-route in the
#        script is correct."
#  echo
#  exit 1
#fi

# project-related variables.
PROJECT_NAME="region-events"
PROJECT_DIR="$HOME/projects/java/$PROJECT_NAME"
PROJECT_VERSION=$(extractProperty "version" "$PROJECT_DIR/gradle.properties")
PROJECT_BUILT_FILE="$PROJECT_NAME-plugin-$PROJECT_VERSION.jar"
PROJECT_BUILT_FILE_ROUTE="$PROJECT_DIR/jars/$PROJECT_BUILT_FILE"

# plugin-file related variables.
PLUGIN_DATA_FOLDER_DIR="$SERVER_PLUGINS_DIR/REvt-RegionEvents"
IN_PROD_FILE_DIR="$SERVER_PLUGINS_DIR/$PROJECT_BUILT_FILE"

echo "Deleting related-plugin directories and files prior to move new jar-file."

# Delete the old data-folder the plugin created?
# let this thing as false only for initial-testing purposes, you don't want to lose testing-data accidentally :p.
DELETE_DATA_FOLDER=true
if [[ $DELETE_DATA_FOLDER && -d "$PLUGIN_DATA_FOLDER_DIR" ]]; then
  rm -r "$PLUGIN_DATA_FOLDER_DIR"
  echo "Deleted old data-folder."
fi

if [ -d "$IN_PROD_FILE_DIR" ]; then
  rm "$IN_PROD_FILE_DIR"
  echo "Deleted previous plugin-jar."
fi

ensureExistingDirOrFile "$PROJECT_BUILT_FILE_ROUTE"

echo "Moving compiled-jar into plugins directory."

mv "$PROJECT_BUILT_FILE_ROUTE" "$SERVER_PLUGINS_DIR"
echo
echo "The file $PROJECT_BUILT_FILE has been moved into the $SERVER_PLUGINS_DIR directory successfully!"
echo "Script execution has finished correctly."
echo
exit 0