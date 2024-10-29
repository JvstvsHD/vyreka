# Module core
This module contains the core functionality of the library. It is responsible for the creation of the main objects of the library,
such as the `LocationMap` and `Cell` objects, which are the central objects of the library.

# Package de.jvstvshd.vyreka.core
This package contains the core functionality of the library besides map and cell representation, mostly centering around
the `Location` class.

# Package de.jvstvshd.vyreka.core.cell
This package contains the cell representation of the library. The `Cell` class is a central object of the library, representing
a single cell in the map, identified by its x, y and z coordinates (`Location` object).

# Package de.jvstvshd.vyreka.core.map
This package contains the map representation of the library. The `VyrekaMap` class is a central object of the library, representing
a grid of cells, identified by their x, y and z coordinates (`Location` object).

# Module paths
This module centers around paths between locations on the map. It also contains means of finding the optimal path between two locations/cells.

# Package de.jvstvshd.vyreka.core.path
This package contains the path representation of the library and a simple implementation of it. It also offers extensions
for more easier use of the functionality of this module on top of the core functionality.

# Package de.jvstvshd.vyreka.core.path.routing
This package provides means to find the most optimal route between to given locations on a map.

# Package de.jvstvshd.vyreka.core.path.routing.dijkstra
This package provides an implementation of the Dijkstra algorithm to find the most optimal route between two locations on a map.