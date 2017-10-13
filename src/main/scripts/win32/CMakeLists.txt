cmake_minimum_required (VERSION 2.8.6)
project (yildiz_native_bullet)

set(CMAKE_CXX_FLAGS "-O2 -std=gnu++11")

include_directories("bullet/win32/include/bullet")
include_directories("java/include")
include_directories("java/include/win32")

get_property(dirs DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR} PROPERTY INCLUDE_DIRECTORIES)
foreach(dir ${dirs})
  message(STATUS "dir='${dir}'")
endforeach()
    
set(CMAKE_SHARED_LINKER_FLAGS "-Xlinker --add-stdcall-alias")

#Set all source file in variable source_files.
file(
        GLOB_RECURSE
        source_files
        ${CMAKE_SOURCE_DIR}/../../includes/*.h
        ${CMAKE_SOURCE_DIR}/../../includes/*.hpp
        ${CMAKE_SOURCE_DIR}/../../c++/*.cpp
)

add_library(yildizbullet SHARED ${source_files})
add_definitions(-O2)

target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/win32/lib/libBulletWorldImporter.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/win32/lib/libBulletFileLoader.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/win32/lib/libBulletDynamics.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/win32/lib/libBulletCollision.dll.a)
target_link_libraries(yildizbullet ${CMAKE_SOURCE_DIR}/bullet/win32/lib/libLinearMath.dll.a)
