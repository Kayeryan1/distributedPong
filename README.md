# dsitributedPong

We created a distributed, 2-4 player pong game that works using socket communication. Each player controls one paddle that traverses one side of the square screen, and the players bounce the single pong ball back and forth. Our program distributes the work of rendering the game to each client, and each client communicates with each other by broadcasting paddle locations, which the opposing clients use to update the corresponding paddle in their respective GUI instances. There is no server in this system, only peer-to-peer client communication. However, during the initial setup, the host player does act as a coordinator between the players so that each player can properly connect and communicate with one another. 
	The heart of our program is the game loop: at every “tick” of the GUI’s rendering, each client performs the following steps to render the game and synchronize with opponents:

##Obtain the latest location of the local player’s paddle
##Broadcast the location of this paddle to each opposing player
##Receive the opposing players’ paddle locations. The client blocks until it has received all such locations. 
##Update the local GUI with received locations
##Detect collisions and update the pong ball's velocity if necessary
##Move the pong ball

	The third step, in which clients block until receiving all locations, behaves as a distributed barrier that ensures the clients remain synchronized in the game, and that no single player surpasses any other player. The code for this game loop is inside the “startGameLoop()” method inside ClientGUI.java.
