ChaosToOrder, prelease version, 2016-03-01, Vlad Yanchenko

DESCRIPTION:
	This program is designed for an entertainment purposes. It involves moving, scattering and converging a balls.

OPERATING: 
	- Moving a scattered balls to their destination balls
		- In an accelerated way (acceleratedConvergence = true)
		- In an uniform speed way (acceleratedConvergence = false)
	- Moving a balls around a selected one
                - Magnifying a scale of a figure of a scattering balls
                - Changing a figure of a scattered and destination balls
	- Selecting / deselecting any present scattered ball by clicking a mouse on it
	- Dragging a ball around

USER INTERACTION:
	- Mouse wheel scrolling:
		- "Upwards" / "Downwards" with NO any scattered ball selected - moves a destination balls in a clockwise / (counter clockwise) direction
		- "Upwards" / "Downwards" with some scattered ball selected - moves scattered balls in a clockwise / (counter clockwise) direction
		- "Upwards" / "Downwards" with a CTRL button being pushed and NO any scattered ball selected - increases / decreases a scale of a figure of a destination balls.
		- "Upwards" / "Downwards" with a CTRL button being pushed with some scattered ball selected - increases / decreases a scale of a figure of a scattered balls.
                - "Upwards" / "Downwards"with a SHIFT button being pushed and NO any scattered ball selected - changes a figure of a destination balls.
		- "Upwards" / "Downwards" with a SHIFT button being pushed with some scattered ball selected - changes a figure of a scattered balls.
                - Pushing a "Start" button, that might have "Init", or "Pause", or "Continue" states
		- "Start" renders an ascending of a scattered balls towards destination balls
		- "Pause" stops the ascending
		- "Continue" allows the balls continue their running towards the destination balls
		- "Init" scatters balls at random

//** Following part describes what is wanted to be done extra in a project */
AMBITION:
	- In "Present features.ods"

//** Following part describes any kind of problems present in a project */
ISSUES:	
		- When balls converged and buttons Init and then Start are clicked, scattered balls do not converge to a destination balls, but become converged right away.

		- (RESOLVED, CHECK IF SOMEDAY TO APPEAR) When ball is dragged during convergence, they do not converge properly.
		- (RESOLVED, CHECK IF SOMEDAY TO APPEAR) When program is run for the first time, scattered balls are converged to a place different to a destination balls.
                - (RESOLVED, CHECK IF SOMEDAY TO APPEAR) When balls are moving away from their destinations (it happens after a convergence is finished and user clicks left mouse button), they somehow get caught by a destination balls.


TO BE DONE:	
		- Nothing, although there are more features present in a "Present features.ods". There are other java technologies that have to be studied.


Releases:

2016-03-01 v.0.99
	- Bugs discovered. 
		1) When balls converged and buttons Init and then Start are clicked, scattered balls do not converge to a destination balls, but become converged right away.
		2) Sometimes NullPointerException occurs on a Logic instance.
	- NullPointerException problem resolved.

2016-02-29
	- Fixed all the current issues.

2015-09-26
	- Fixed a bug of balls incorrect repulsing from a screen walls/sides.

2014-09-05
	- The feature of a balls roaming around after clicking a mouse button, when a convergence is over, is fixed.

2014-08-25
	The project is suspended, its left as a prerelease version. I'm tired of it )

2014-08-21
        - Changing a balls scattering figure is now looped - when reaching a last figure, the counter is switched to a first one and reverse is also valid.

2014-08-10
	- Added an interactivity feature
User is now able to put all the scattered balls to a mouse cursor clicked position, havinga SHIFT button pressed.
	- Fixed a "Start" button inscriptions appearance.

2014-08-04
	- Added an interactivity feature
User is now able to throw a selected ball to a mouse position, having a CTRL button pushed, with some scattered ball selected.

2014-07-31
	- Added an interactivity feature
User is now able to drag all the scattered balls with a mouse, having a CTRL button pushed, with some scattered ball selected.

2014-07-26
        - Fixed selecting a scattered ball 
        - Added a post convergence feature:
Once a convergence is over, a selected ball passes all the converged balls one by one in a clockwise order.
        - Fixed scattering of a destination balls

2014-07-21
	- Removed a bug in a previously made feature
	- Added an interactivity feature
User is now able to pick a ball, and having a CTRL button pushed, click on a Start button, thus making all the scattered balls to fall down to this single ball. 

2014-07-20
	- Added an interactivity feature
User is now able to pick a ball drag it around and leave it where he desires to. Picking a ball is somewhat buggy.
	- Added an interactivity feature
User is now able to rescatter a balls by pushing a "r/R" letter on a keyboard.

2014-07-19
	- Added an interactivity feature
User is now able to choose a scattering mode of a scattered balls, having a SHIFT button pushed and rotating a mouse wheel, with one of a scattered balls selected.
	- Added an interactivity feature
User is now able to choose a scattering mode of a destination balls, having a SHIFT button pushed and rotating a mouse wheel, with no any scattered balls selected.

2014-06-31
	- Added an interactivity feature
User is now able to move farther / closer a randomly scattered balls, relatively a selected one, having a CTRL button pushed and rotating a mouse wheel.
	- Added a full screen feature

2014-06-30
	- Added an interactivity feature
User is now able to change a radius of a destination balls, having a CTRL button pushed and rotating a mouse wheel.


2013-09-08     Project started


Going towards a mouse pointer, with bouncing off the screen borders :
	1. Go to a frm.addMouseListener
	2. Find an "afterConvergence" part
	3. If keyCTRL == true use a computeulateDeltas(mousePoint, ScatteredDots)
	4. Render balls rolling

Going OUTwards a mouse pointer, with bouncing off the screen borders :
	1. Go to a frm.addMouseListener
	2. Find an "afterConvergence" part
	3. If keyCTRL == true use a computeulateDeltas(mousePoint, ScatteredDots)
	4. Invert deltas (*-1)
	4. Render balls rolling

Going OUTwards of a center, with bouncing off the screen borders :
	1. Go to a frm.addMouseListener
	2. Find an "afterConvergence" part
	3. If keyCTRL == true use a computeulateDeltas(screenCenterPoint, ScatteredDots)
	4. Render balls rolling