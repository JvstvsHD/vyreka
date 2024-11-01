# Vyreka
Vyreka is a simple library for representing maps and paths in Kotlin. It offers a simple API for creating and manipulating maps and paths, 
and provides of useful algorithms for working with them.

## Installation
To install Vyreka, add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") //only needed for snapshots
}

dependencies {
    implementation("de.jvstvshd.vyreka:vyreka-core:1.0.0") //Core library that contains the basic API about maps and their cells
    implementation("de.jvstvshd.vyreka:vyreka-paths:1.0.0") //Library that contains the path concept and algorithms for working with paths
}
```

## Usage
### Maps
A map is a grid of cells. Each cell has a position. The position is a pair of integers that represent the x, y and z coordinates of the cell.
To create a map, you can use the `VyrekaMap` class. The following code creates a map with a size of 10x10x10 using the `SimpleGridMap`,
which is a default implementation for grid-based maps.:

```kotlin
val map = SimpleGridMap(10, 10, 10)
```
This map does not yet contain anything. To add cells to the map, you can use the `setCell` method:

### Cells
```kotlin
val location = Location(0, 0, 0) 
val cell = SimpleGridCell(location, map)
map.setCell(cell)
map.getCellAt(location) //returns the cell at the location (0|0|0), the cell that got created before
map.removeCellAt(location) //removes the cell at the location (0|0|0) and returns the instance of the cell, if it exists
```
This will add the cell at the location (0|0|0) to the map.

Cells are able to hold attributes (see AttributeHolder interface):
```kotlin
val key = Key.fromString("key")
cell.setAttribute(key, "value")
val result = cell.getAttribute(key) //returns "value" but as type any
//You can also use the getAttribute method with a type parameter to get the value as a specific type:
val result = cell.getAttributeType<String>(key) //returns "value", but as type String
val result = cell.getAttributeType<Int>(key) //throws an exception because the value is not an Int
val hasAttribute = cell.hasAttribute(key) //returns true
cell.removeAttribute(key) //removes the attribute
```
Furthermore, you are able to use getAttributeOrNull to get the attribute as a nullable value or getAttributeOrDefault to 
get the attribute value or a default value if the attribute does not exist. These methods also exist for the type-specific methods.

It is also possible to check accessibility of cells. This is done via the attribute key `Cell.PermeabilityKey`, which allows
for values `true` and `false`. If the attribute does not exist, the cell is considered to be permeable. If the attribute exists
and is set to `false`, the cell is considered to be impermeable. The following code demonstrates this:
```kotlin
val location = Location(0, 0, 0)
val cell = SimpleGridCell(location, map)
cell.setAttribute(Cell.PermeabilityKey, false)
val secondLocation = Location(1, 0, 0)
val secondCell = SimpleGridCell(secondLocation, map)
secondCell.canBeAccessedFrom(cell) //returns false, since `cell` is impermeable, but `secondCell` is permeable
```
### Paths
A path is a sequence of cells. To create a path, you can use the `Path` class or the default implementation `SimplePath`.
The following code creates a path with the cells at the locations (0|0|0), (1|0|0) and (2|0|0):

```kotlin
val path = startCell.path() //creates a path with only the start cell
path + secondCell //adds the second cell to the path
path + thirdCell //adds the third cell to the path
println(path.getLength()) //prints 3
```
You can also create sub-paths and forked paths:
```kotlin
val subPath = path.subPath(1, 2) //creates a sub-path from the second cell to the third cell
val forkedPath = path.forkTo(newCell, 1) //creates a path that contains the first & second cell as well as the new cell
```

### Routing/Pathfinding
Vyreka provides a simple API for routing. The `RoutingAlgorithm` interface defines the methods for routing. Implemented in 
this library is yet only the `DijkstraRoutingAlgorithm`, but more algorithms will be added in the future. You will need to provide a 
`CellTravelCostSupplier` that defines the cost or you can resort to either the zero-cost implementation `CellTravelCostSupplier.None`
or `CellTravelCostSupplier.Distance` that defines the cost through the distance of the cells (incl. diagonal). The following code
demonstrates how to use the `DijkstraRoutingAlgorithm`:
```kotlin
val startCell = map.getCellAt(Location(0, 0, 0))
val endCell = map.getCellAt(Location(9, 9, 9))
val result = startCell.findPathTo(endCell, yourCellTravelCostSupplier) //returns a result that contains a path from the start cell to the end cell and the duration taken
```