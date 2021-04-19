package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.GraphicalView;

public class GraphicalViewListener implements MouseListener {

	private GraphicalView gv;

	public GraphicalViewListener(GraphicalView gv) {
		if (gv != null)
			this.gv = gv;
		else
			throw new IllegalArgumentException(
					"GraphicalViewListener : GraphicalViewListener(GraphicalView) : parameter \"gv\" null.");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.gv.getPlayButton()) {
			this.gv.hideHome();
			System.exit(0);
		} else if (e.getSource() == this.gv.getSettingsButton()) {
			this.gv.hideHome();
			this.gv.printSettings();
		} else if (e.getSource() == this.gv.getPlayButtonSettings() || e.getSource() == this.gv.getLeaveButton()) {
			this.gv.hideSettings();
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
