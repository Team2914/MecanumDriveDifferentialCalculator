# Vehicle Simulation

## Overview

This code simulates the movement of a car-like vehicle with two axles and four wheels. The `findCompensation` method calculates the velocity scalers for the back left and right wheels of the vehicle, given a heading in the form of two `double` values `headingI` and `headingJ`. The simulation is drawn on a canvas, with lines representing the axles and wheels of the vehicle, as well as a center of mass and a target point for the vehicle to move towards.

## Requirements

- The `Draw` class is required for drawing the simulation on the canvas.
- The `Config` class contains various parameters for the simulation, such as the dimensions of the wheels and the distance between the axles.

## Setup

Ensure that the necessary dependencies are imported or installed.

## Usage

To run the simulation, call the `findCompensation` method with the desired heading values as arguments. The resulting scalers for the back left and right wheels will be printed to the console.


## License

This code is released under the GPL 3.0 terms.
