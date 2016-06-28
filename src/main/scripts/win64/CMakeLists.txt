cmake_minimum_required (VERSION 2.8.6)
project (yildiz_native_bullet)

set(CMAKE_CXX_FLAGS "-O2")

set(BULLET "${CMAKE_SOURCE_DIR}/../bullet/release/win64")
set(BULLET_INCLUDE "${BULLET}/include")
set(BULLET_LIB "${BULLET}/lib")

include_directories(/home/moussa/dev/tools/java-win64/include)
include_directories(/home/moussa/dev/tools/java-win64/include/win32)
include_directories(${BULLET_INCLUDE})
    
set(CMAKE_SHARED_LINKER_FLAGS "-Xlinker --add-stdcall-alias")

#Set all source file in variable source_files.
file(
        GLOB_RECURSE
        source_files
        ${CMAKE_SOURCE_DIR}/../../c++/*.cpp
        ${CMAKE_SOURCE_DIR}/../../includes/*.h
)

#set dynamic lib as output, name is bullet and sources dem source_files variable are used.
add_library(yildizbullet SHARED ${source_files})
add_definitions(-O2)

target_link_libraries(yildizbullet ${BULLET_LIB}/libBulletWorldImporter.a)
target_link_libraries(yildizbullet ${BULLET_LIB}/libBulletFileLoader.a)
target_link_libraries(yildizbullet ${BULLET_LIB}/libBulletDynamics.a)
target_link_libraries(yildizbullet ${BULLET_LIB}/libBulletCollision.a)
target_link_libraries(yildizbullet ${BULLET_LIB}/libLinearMath.a)
