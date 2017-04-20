cmake_minimum_required (VERSION 2.8.6)
project (yildiz_native_bullet)

set(CMAKE_CXX_FLAGS "-O2 -std=gnu++11")

include_directories(bullet/include)
include_directories("java/include")
include_directories("java/include/win32")
    
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

target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/lib/libBulletWorldImporter.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/lib/libBulletFileLoader.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/lib/libBulletDynamics.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/lib/libBulletCollision.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/lib/libLinearMath.dll.a)
