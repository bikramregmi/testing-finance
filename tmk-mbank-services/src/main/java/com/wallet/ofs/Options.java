package com.wallet.ofs;

public class Options {
	
private String versionName="CCASHATM";

private String function="I";

private Option_Operation operation = Option_Operation.PROCESS;

public Options(){
	this.versionName="CCASHATM";

	this.function="I";

	this.operation = Option_Operation.PROCESS;
}

public String getVersionName() {
	return versionName;
}

public void setVersionName(String versionName) {
	this.versionName = versionName;
}

public String getFunction() {
	return function;
}

public void setFunction(String function) {
	this.function = function;
}

public Option_Operation getOperation() {
	return operation;
}

public void setOperation(Option_Operation operation) {
	this.operation = operation;
}


}
