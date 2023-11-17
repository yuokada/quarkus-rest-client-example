SRC_DIR := src/main/java
JAVA_FILES := $(shell find $(SRC_DIR) -name '*.java')

get_google_java_format:
	wget -O google-java-format-all-deps.jar https://github.com/google/google-java-format/releases/download/v1.18.1/google-java-format-1.18.1-all-deps.jar

reformat_java_files:
	java -jar google-java-format-all-deps.jar --replace -a ${JAVA_FILES}
