package Architecture.Registers;

/**
 * 
 * @author Omar Manir
 *
 */
public class StatusRegister {

	private boolean t; // trace
	private boolean s; // supervisor
	private boolean i0; // interrupt 0
	private boolean i1; // interrupt 1
	private boolean i2; // interrupt 2
	private boolean x; // extend
	private boolean n; // negative
	private boolean z; // zero
	private boolean v; // overflow
	private boolean c; // carry
	
	public void setTrace(boolean value) {
		t = value;
	}
	
	public boolean getTrace() {
		return t;
	}
	
	public void setsupervisor(boolean value) {
		value = s;
	}
	
	public boolean getSupervisor() {
		return s;
	}	
	
	//TODO required?
	public void setInterrupt0(boolean value) {
		i0 = value;
	}
	
	public boolean getInterrupt0() {
		return i0;
	}
	
	//TODO required?
	public void setInterrupt1(boolean value) {
		i1 = value;
	}

	public boolean getInterrupt1() {
		return i1;
	}

	//TODO required?
	public void setInterrupt2(boolean value) {
		i2 = value;
	}

	public boolean getInterrupt2() {
		return i2;
	}
	
	public void setExtend(boolean value) {
		x = value;
	}
	
	public boolean getExtend() {
		return x;
	}
	
	public void setNegative(boolean value) {
		n = value;
	}
	
	public boolean getNegative() {
		return n;
	}
	
	public void setZero(boolean value) {
		z = value;
	}
	
	public boolean getZero() {
		return z;
	}
	
	public void setOverflow(boolean value) {
		v = value;
	}
	
	public boolean getOverflow() {
		return v;
	}
	
	public void setCarry(boolean value) {
		c = value;
	}
	
	public boolean getCarry() {
		return c;
	}

}
