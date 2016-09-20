cmake . -DCMAKE_BUILD_TYPE=Release -DLIBRARY_OUTPUT_PATH="../../../../target/classes/linux64" -G "Unix Makefiles"

build-wrapper-linux-x86-64 --out-dir /opt/atlassian/bitbucketci/agent/build/target/sonar/cpp make

rm -R CMakeFiles
rm CMakeCache.txt
rm cmake_install.cmake
rm Makefile




