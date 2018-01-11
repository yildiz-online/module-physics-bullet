cmake_minimum_required (VERSION 3.0.0)

project (yildiz_native_bullet)

set(CMAKE_CXX_FLAGS "-O2 -fPIC -std=gnu++11")

find_package(Bullet REQUIRED)
find_package(Java REQUIRED)
find_package(JNI REQUIRED)

include_directories(${BULLET_INCLUDE_DIR})
include_directories(${JNI_INCLUDE_DIRS})
include_directories(${JNI_INCLUDE_DIRS}/linux)

file(
        GLOB_RECURSE
        source_files
        ${CMAKE_SOURCE_DIR}/../../includes/*.h
        ${CMAKE_SOURCE_DIR}/../../includes/*.hpp
        ${CMAKE_SOURCE_DIR}/../../c++/*.cpp

)

add_library(yildizbullet SHARED ${source_files})

target_link_libraries(yildizbullet ${BULLET_LIBRARIES})
