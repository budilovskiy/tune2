package com.felixfeatures.player;

import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;

public class VolumeControl {

	private Port lineOut;
	private FloatControl volControl;
	private Mixer mixer;

	public VolumeControl() {
		lineOut = null;
		volControl = null;

		// It gets everyone of the System's Mixers
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		try {
			// it Looks for those Mixers that support the OutPut SPEAKER
			for (int i = 0; i < mixerInfo.length; i++) {
				mixer = AudioSystem.getMixer(mixerInfo[i]);

				// If the SPEAKER is supported, then it gets a line
				if (mixer.isLineSupported(Port.Info.SPEAKER)) {
					lineOut = (Port) mixer.getLine(Port.Info.SPEAKER);

					lineOut.open();

					// Once we have the line, we request the Volumen control as
					// a FloatControl
					volControl = (FloatControl) lineOut
							.getControl(FloatControl.Type.VOLUME);
					// Everything is done
				}
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}

	public float getValue() {
		return volControl.getValue();
	}

	public void setValue(float value) {
		volControl.setValue(value);
	}

	public boolean isControlValid() {
		return (volControl == null) ? false : true;
	}

	public void close() {
		lineOut.close();
	}
}
