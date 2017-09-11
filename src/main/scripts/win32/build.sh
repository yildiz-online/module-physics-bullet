cmake . -DCMAKE_BUILD_TYPE=Release -DLIBRARY_OUTPUT_PATH="../../../../target/classes/win32" -DCMAKE_TOOLCHAIN_FILE=mingw-toolchain.cmake

make
r1=$?

cp bullet/bin/libBulletCollision.dll ../../../../target/classes/win32/libBulletCollision.dll
cp bullet/bin/libBulletDynamics.dll ../../../../target/classes/win32/libBulletDynamics.dll
cp bullet/bin/libBulletSoftBody.dll ../../../../target/classes/win32/libBulletSoftBody.dll
cp bullet/bin/libLinearMath.dll ../../../../target/classes/win32/libLinearMath.dll
cp libstdc++-6.dll ../../../../target/classes/win32/libstdc++-6.dll
cp libgcc_s_sjlj-1.dll ../../../../target/classes/win32/libgcc_s_sjlj-1.dll

rm -R CMakeFiles
rm CMakeCache.txt
rm cmake_install.cmake
rm Makefile

return $r1




