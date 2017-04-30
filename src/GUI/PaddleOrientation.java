package gui;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCode;

enum PaddleOrientation {
	Vertical(KeyCode.UP, KeyCode.DOWN), Horizontal(KeyCode.LEFT, KeyCode.RIGHT);
	
	private final Set<KeyCode> directions = new HashSet<>();;
	
	private PaddleOrientation(KeyCode dir, KeyCode dir2) {
		this.directions.add(dir);
		this.directions.add(dir2);
	}
	
	public boolean contains(KeyCode dir) {
		return directions.contains(dir);
	}
}