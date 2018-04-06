#include "../includes/catch.hpp"
#include "../includes/World.hpp"

TEST_CASE( "rayCastPoint", "[world]" ) {
    yz::World world;
    const btVector3 p = btVector3(0,0,0);
    float f [4];
    world.rayCastPoint(p, p, f);
    REQUIRE( f[0] == -1 );
    REQUIRE( f[1] == 0 );
    REQUIRE( f[2] == 0 );
    REQUIRE( f[3] == 0 );
}
