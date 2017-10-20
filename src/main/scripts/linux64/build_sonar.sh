cmake . -DCMAKE_BUILD_TYPE=Release -DLIBRARY_OUTPUT_PATH="../../../../target/classes/linux64" -G "Unix Makefiles"

build-wrapper-linux-x86-64 --out-dir ../../../../target/sonar-cpp make
r1=$?

cp bullet/linux64/lib/libBulletCollision.so ../../../../target/classes/linux64/libBulletCollision.so
cp bullet/linux64/lib/libBulletDynamics.so ../../../../target/classes/linux64/libBulletDynamics.so
cp bullet/linux64/lib/libBulletSoftBody.so ../../../../target/classes/linux64/libBulletSoftBody.so
cp bullet/linux64/lib/libLinearMath.so ../../../../target/classes/linux64/libLinearMath.so

rm -R CMakeFiles
rm CMakeCache.txt
rm cmake_install.cmake
rm Makefile
rm -R bullet

return $r1
