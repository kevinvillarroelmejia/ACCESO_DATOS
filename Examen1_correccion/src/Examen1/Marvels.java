package Examen1;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Marvels {
	@SerializedName("ucm") 
	private List<Marvel> marvels;

	public Marvels() {
		this.marvels = new ArrayList<Marvel>();
	}

	public Marvels(List<Marvel> marvels) {
		this.marvels = marvels;
	}

	public List<Marvel> getMarvels() {
		return marvels;
	}

	public void setMarvels(List<Marvel> marvels) {
		this.marvels = marvels;
	}
}
