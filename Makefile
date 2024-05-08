SRC_DIR := server/src/main/java
JAVA_FILES := $(shell find $(SRC_DIR) -name '*.java')
GJF_VERSION := 1.22.0

get_google_java_format:
	wget -O google-java-format-all-deps.jar https://github.com/google/google-java-format/releases/download/v$(GJF_VERSION)/google-java-format-$(GJF_VERSION)-all-deps.jar

fix_import_only:
	find . -name  \*.java |xargs -L 1 google-java-format --replace --fix-imports-only

reformat_java_files:
	ls -1 ${JAVA_FILES} | xargs -L 1 java -jar google-java-format-all-deps.jar --replace --offset 4 --length 4
